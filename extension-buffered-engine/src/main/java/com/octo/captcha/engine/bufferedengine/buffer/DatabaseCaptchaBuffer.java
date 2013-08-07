/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

/*
 * jcaptcha, the open source java framework for captcha definition and integration
 * copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

/*
 * jcaptcha, the open source java framework for captcha definition and integration
 * copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */
package com.octo.captcha.engine.bufferedengine.buffer;

import com.octo.captcha.Captcha;
import org.apache.commons.collections.buffer.UnboundedFifoBuffer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * A database Captcha Buffer.
 * <p/>
 * The database should have the following structure : default Column Name , type </p> <ul> <li> timemillis , long </li>
 * <li> hashCode , long </li> <li> locale , string </li> <li> captcha , object </li> </ul>
 *
 * @author <a href="mailto:marc.antoine.garrigue@gmail.com">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public class DatabaseCaptchaBuffer implements CaptchaBuffer {

    private static final Log log = LogFactory.getLog(DatabaseCaptchaBuffer.class.getName());

    //database attributes
    private DataSource datasource;
    private String table = "JCAPTCHA_T";
    private String timeMillisColumn = "timemillis";
    private String hashCodeColumn = "hashCode";
    private String localeColumn = "locale";
    private String captchaColumn = "captcha";
    private static final String DB_ERROR = "SQL Error :";


    public DatabaseCaptchaBuffer(DataSource datasource) {
        log.info("Initializing Buffer");
        this.datasource = datasource;
        log.info("Buffer size : " + size());
        log.info("Buffer initialized");
    }

    public DatabaseCaptchaBuffer(DataSource datasource, String table) {
        log.info("Initializing Buffer");
        this.datasource = datasource;
        this.table = table;
        log.info("Buffer size : " + size());
        log.info("Buffer initialized");
    }

    public DatabaseCaptchaBuffer(DataSource datasource, String table, String timeMillisColumn, String hashCodeColumn, String captchaColumn, String localeColumn) {
        log.info("Initializing Buffer");
        this.datasource = datasource;
        this.table = table;
        this.timeMillisColumn = timeMillisColumn;
        this.hashCodeColumn = hashCodeColumn;
        this.captchaColumn = captchaColumn;
        this.localeColumn = localeColumn;
        log.info("Buffer size : " + size());
        log.info("Buffer initialized");
    }

    //Buffer methods


    /**
     * remove a captcha from the buffer
     *
     * @return a captcha
     *
     * @throws java.util.NoSuchElementException
     *          if there is no captcha throw NoSuchElementException
     */
    public Captcha removeCaptcha() throws NoSuchElementException {
        return removeCaptcha(Locale.getDefault());
    }

    /**
     * remove a captcha from the buffer corresponding to the locale
     *
     * @param locale The locale the catcha to remove
     *
     * @return a captcha correponding to the locale
     *
     * @throws NoSuchElementException if there is no captcha throw NoSuchElementException
     */
    public Captcha removeCaptcha(Locale locale) throws NoSuchElementException {
        Collection col = removeCaptcha(1, locale);
        if (col != null && col.size() > 0) {
            return (Captcha) col.iterator().next();
        } else {
            throw new NoSuchElementException("no captcha in this buffer for locale " + locale);
        }
    }

    /**
     * Remove a precise number of captcha
     *
     * @param number The number of captchas to remove
     *
     * @return a collection of captchas
     */
    public Collection removeCaptcha(int number) {
        return removeCaptcha(number, Locale.getDefault());
    }

    /**
     * Remove a precise number of captcha with a locale
     *
     * @param number The number of captchas to remove
     * @param locale The locale of the removed captchas
     *
     * @return a collection of captchas
     */
    public Collection removeCaptcha(int number, Locale locale) {
        Connection con = null;
        PreparedStatement ps = null;
        PreparedStatement psdel = null;
        ResultSet rs = null;
        Collection collection = new UnboundedFifoBuffer();
        Collection temp = new UnboundedFifoBuffer();
        if (number < 1) {
            return collection;
        }
        try {
            if (log.isDebugEnabled()) {
                log.debug("try to remove " + number + " captchas");
            }
            ;
            con = datasource.getConnection();


            ps = con.prepareStatement("select *  from " + table + " where " + localeColumn
                    + " = ? order by " + timeMillisColumn);

            psdel = con.prepareStatement("delete from " + table + " where " + timeMillisColumn
                    + "= ? and " + hashCodeColumn
                    + "= ? ");//and " + localeColumn
            //+ "= ?");
            ps.setString(1, locale.toString());
            ps.setMaxRows(number);
            //read
            rs = ps.executeQuery();
            int i = 0;
            while (rs.next() && i < number) {
                try {
                    i++;
                    InputStream in = rs.getBinaryStream(captchaColumn);
                    ObjectInputStream objstr = new ObjectInputStream(in);
                    Object captcha = objstr.readObject();
                    temp.add(captcha);
                    //and delete
                    long time = rs.getLong(timeMillisColumn);
                    long hash = rs.getLong(hashCodeColumn);
                    psdel.setLong(1, time);
                    psdel.setLong(2, hash);
                    //psdel.setString(3, rs.getString(localeColumn));
                    psdel.addBatch();

                    if (log.isDebugEnabled()) {
                        log.debug("remove captcha added to batch : " + time + ";" + hash);
                    }

                } catch (IOException e) {
                    log.error("error during captcha deserialization, " +
                            "check your class versions. removing row from database", e);
                    psdel.execute();
                } catch (ClassNotFoundException e) {
                    log.error("Serialized captcha class in database is not in your classpath!", e);
                }

            }
            //execute batch delete
            psdel.executeBatch();
            log.debug("batch executed");
            rs.close();
            //commit the whole stuff
            con.commit();
            log.debug("batch commited");
            //only add after commit
            collection.addAll(temp);
        } catch (SQLException e) {
            log.error(DB_ERROR, e);
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                }
            }

        } finally {

            if (ps != null) {
                try {
                    ps.close();
                }        // rollback on error
                catch (SQLException e) {
                }
            }
            if (con != null) {
                try {
                    con.close();
                }        // rollback on error
                catch (SQLException e) {
                }
            }
        }
        return collection;
    }

    /**
     * Put a captcha with default locale
     */
    public void putCaptcha(Captcha captcha) {
        putCaptcha(captcha, Locale.getDefault());
    }

    /**
     * Put a captcha with a locale
     *
     * @param captcha The captcha to add
     * @param locale  the locale of the captcha
     */
    public void putCaptcha(Captcha captcha, Locale locale) {
        if (captcha != null) {
            Set set = new HashSet();
            set.add(captcha);
            putAllCaptcha(set, locale);
        }
    }

    /**
     * Put a collection of captchas with the default locale
     *
     * @param captchas The captchas to add
     */
    public void putAllCaptcha(Collection captchas) {
        putAllCaptcha(captchas, Locale.getDefault());
    }

    /**
     * Put a collection of captchas with his locale
     *
     * @param captchas The captchas to add
     * @param locale   The locale of the captchas
     */
    public void putAllCaptcha(Collection captchas, Locale locale) {
        Connection con = null;
        PreparedStatement ps = null;


        if (captchas != null && captchas.size() > 0) {
            Iterator captIt = captchas.iterator();
            if (log.isDebugEnabled()) {
                log.debug("try to insert " + captchas.size() + " captchas");
            }

            try {
                con = datasource.getConnection();
                con.setAutoCommit(false);
                ps = con.prepareStatement("insert into " + table + "(" + timeMillisColumn + "," +
                        hashCodeColumn + "," + localeColumn + "," + captchaColumn + ") values (?,?,?,?)");


                while (captIt.hasNext()) {

                    Captcha captcha = (Captcha) captIt.next();
                    try {
                        long currenttime = System.currentTimeMillis();
                        long hash = captcha.hashCode();

                        ps.setLong(1, currenttime);
                        ps.setLong(2, hash);
                        ps.setString(3, locale.toString());
                        // Serialise the entry
                        final ByteArrayOutputStream outstr = new ByteArrayOutputStream();
                        final ObjectOutputStream objstr = new ObjectOutputStream(outstr);
                        objstr.writeObject(captcha);
                        objstr.close();
                        final ByteArrayInputStream inpstream = new ByteArrayInputStream(outstr.toByteArray());

                        ps.setBinaryStream(4, inpstream, outstr.size());

                        ps.addBatch();

                        if (log.isDebugEnabled()) {
                            log.debug("insert captcha added to batch : " + currenttime + ";" + hash);
                        }

                    } catch (IOException e) {
                        log.warn("error during captcha serialization, " +
                                "check your class versions. removing row from database", e);
                    }
                }
                //exexute batch and commit()

                ps.executeBatch();
                log.debug("batch executed");

                con.commit();
                log.debug("batch commited");

            } catch (SQLException e) {
                log.error(DB_ERROR, e);

            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException e) {
                    }
                }
                if (con != null) {
                    try {
                        con.close();
                    } catch (SQLException e) {
                    }
                }
            }
        }


    }

    /**
     * Get the size of the buffer for all locales
     *
     * @return The size of the buffer
     */
    public int size() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int size = 0;

        try {
            con = datasource.getConnection();
            ps = con.prepareStatement("select count(*) from " + table);
            rs = ps.executeQuery();
            if (rs.next()) {
                size = rs.getInt(1);
            }
            rs.close();
            con.commit();
        } catch (SQLException e) {
            log.error(DB_ERROR, e);
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                }
            }
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                }
            }
        }

        return size;

    }

    /**
     * Get the size of the buffer for a locale
     *
     * @param locale the locale to get the size
     *
     * @return The size of the buffer
     */
    public int size(Locale locale) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int size = 0;

        try {
            con = datasource.getConnection();
            ps = con.prepareStatement("select count(*) from " + table + " where " + localeColumn + "=?");
            ps.setString(1, locale.toString());
            rs = ps.executeQuery();
            if (rs.next()) {
                size = rs.getInt(1);
            }
            rs.close();
            con.commit();
        } catch (SQLException e) {
            log.error(DB_ERROR, e);
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                }
            }
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                }
            }
        }

        return size;
    }

    /**
     * Release all the ressources and close the buffer.
     */
    public void dispose() {
    }

    /**
     * Clear the buffer from all locale
     */
    public void clear() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = datasource.getConnection();
            ps = con.prepareStatement("delete from " + table);
            ps.execute();
            con.commit();

        } catch (SQLException e) {
            log.error(DB_ERROR, e);
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                }
            }
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                }
            }
        }


    }

    /**
     * Get all the locales used
     */
    public Collection getLocales() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Set set = new HashSet();

        try {
            con = datasource.getConnection();
            ps = con.prepareStatement("select distinct " + localeColumn + " from " + table);
            rs = ps.executeQuery();
            while (rs.next()) {
                set.add(rs.getString(1));
            }
            rs.close();
            con.commit();
        } catch (SQLException e) {
            log.error(DB_ERROR, e);
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                }
            }
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                }
            }
        }

        return set;
    }


}
