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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.NoSuchElementException;

import org.apache.commons.collections.MapIterator;
import org.apache.commons.collections.buffer.UnboundedFifoBuffer;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.octo.captcha.Captcha;
import com.octo.captcha.CaptchaException;

/**
 * Simple implmentation of a disk captcha buffer
 *
 * @author Benoit Doumas
 */
public class DiskCaptchaBuffer implements CaptchaBuffer {
    private static final Log log = LogFactory.getLog(DiskCaptchaBuffer.class.getName());

    private RandomAccessFile randomAccessFile;

    private HashedMap diskElements = null;

    private ArrayList freeSpace;

    /**
     * If persistent, the disk file will be kept and reused on next startup. In addition the memory store will flush all
     * contents to spool, and spool will flush all to disk.
     */
    private boolean persistant = false;

    private final String name;

    private File dataFile;

    /**
     * Used to persist elements
     */
    private File indexFile;

    private boolean isInitalized = false;

    /**
     * The size in bytes of the disk elements
     */
    private long totalSize;

    /**
     * The max size in Kbytes of the disk elements
     */
    private int maxDataSize;

    private boolean isDisposed = false;

    /**
     * Constructor for a disk captcha buffer
     *
     * @param fileName   like c:/temp/name
     * @param persistant If the disk buffer is persistant, it will try to load from file name .data et .index existing
     *                   data
     */
    public DiskCaptchaBuffer(String fileName, boolean persistant) {
        log.debug("Creating new Diskbuffer");

        freeSpace = new ArrayList();
        this.name = fileName;
        this.persistant = persistant;

        try {
            initialiseFiles();
        }
        catch (Exception e) {
            log.debug("Error while initialising files " + e);
        }
    }

    private final void initialiseFiles() throws Exception {
        dataFile = new File(name + ".data");

        indexFile = new File(name + ".index");

        readIndex();

        if (diskElements == null || !persistant) {
            if (log.isDebugEnabled()) {
                log.debug("Index file dirty or empty. Deleting data file " + getDataFileName());
            }
            dataFile.delete();
            diskElements = new HashedMap();
        }

        // Open the data file as random access. The dataFile is created if necessary.
        randomAccessFile = new RandomAccessFile(dataFile, "rw");
        isInitalized = true;
        log.info("Buffer initialized");
    }

    /**
     * Gets an entry from the Disk Store.
     *
     * @return The element
     */
    protected synchronized Collection remove(int number, Locale locale) throws IOException {
        if (!isInitalized) return new ArrayList(0);
        DiskElement diskElement = null;
        int index = 0;
        boolean diskEmpty = false;

        Collection collection = new UnboundedFifoBuffer();

        //if no locale
        if (!diskElements.containsKey(locale)) {
            return collection;
        }

        try {
            while (!diskEmpty && index < number) {

                // Check if the element is on disk
                try {
                    diskElement = (DiskElement) ((LinkedList) diskElements.get(locale))
                            .removeFirst();

                    // Load the element
                    randomAccessFile.seek(diskElement.position);
                    byte[] buffer = new byte[diskElement.payloadSize];
                    randomAccessFile.readFully(buffer);
                    ByteArrayInputStream instr = new ByteArrayInputStream(buffer);
                    ObjectInputStream objstr = new ObjectInputStream(instr);

                    collection.add(objstr.readObject());
                    instr.close();
                    objstr.close();

                    freeBlock(diskElement);
                    index++;
                }
                catch (NoSuchElementException e) {
                    diskEmpty = true;
                    log.debug("disk is empty for locale : " + locale.toString());
                }
            }
        }
        catch (Exception e) {
            log.error("Error while reading on disk ", e);
        }
        if (log.isDebugEnabled()) {
            log.debug("removed  " + collection.size() + " from disk buffer with locale "
                    + locale.toString());
        }
        return collection;
    }

    /**
     * Puts items into the store.
     */
    protected synchronized void store(Collection collection, Locale locale) throws IOException {
        if (!isInitalized) return;
        // Write elements to the DB
        for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
            final Object element = iterator.next();

            // Serialise the entry
            final ByteArrayOutputStream outstr = new ByteArrayOutputStream();
            final ObjectOutputStream objstr = new ObjectOutputStream(outstr);
            objstr.writeObject(element);
            objstr.close();

            //check if there is space
            store(element, locale);
        }

    }

    /**
     * Puts items into the store.
     */
    protected synchronized void store(Object element, Locale locale) throws IOException {
        if (!isInitalized) return;
        // Serialise the entry
        final ByteArrayOutputStream outstr = new ByteArrayOutputStream();
        final ObjectOutputStream objstr = new ObjectOutputStream(outstr);
        objstr.writeObject(element);
        objstr.close();
        final byte[] buffer = outstr.toByteArray();

        //check if there is space
        //        if (diskElements.size() >= maxDataSize)
        //        {
        //            return false;
        //        }

        // Check for a free block
        DiskElement diskElement = findFreeBlock(buffer.length);
        if (diskElement == null) {
            diskElement = new DiskElement();
            diskElement.position = randomAccessFile.length();
            diskElement.blockSize = buffer.length;
        }

        // TODO - cleanup block on failure
        // Write the record
        randomAccessFile.seek(diskElement.position);

        //TODO the free block algorithm will gradually leak disk space, due to
        //payload size being less than block size
        //this will be a problem for the persistent cache
        randomAccessFile.write(buffer);

        // Add to index, update stats
        diskElement.payloadSize = buffer.length;
        totalSize += buffer.length;

        //create the localized buffer
        if (!diskElements.containsKey(locale)) {

            diskElements.put(locale, new LinkedList());
        }
        ((LinkedList) diskElements.get(locale)).addLast(diskElement);


        if (log.isDebugEnabled()) {
            long menUsed = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            log.debug("Store " + locale.toString() + " on object, total size : " + size()
                    + " Total unsed elements : " + freeSpace.size() + " memory used " + menUsed);
        }
    }

    /**
     * Marks a block as free.
     */
    private void freeBlock(final DiskElement element) {
        totalSize -= element.payloadSize;
        element.payloadSize = 0;
        freeSpace.add(element);
    }

    /**
     * Removes all cached items from the cache. <p/>
     */
    public synchronized void clearFile() throws IOException {
        try {

            // Ditch all the elements, and truncate the file
            diskElements.clear();
            freeSpace.clear();
            totalSize = 0;
            randomAccessFile.setLength(0);

            indexFile.delete();
            indexFile.createNewFile();
        }
        catch (Exception e) {
            // Clean up
            log.error(" Cache: Could not rebuild disk store", e);
            dispose();
        }
    }


    /**
     * Shuts down the disk store in preparation for cache shutdown <p/>If a VM crash happens, the shutdown hook will not
     * run. The data file and the index file will be out of synchronisation. At initialisation we always delete the
     * index file after we have read the elements, so that it has a zero length. On a dirty restart, it still will have
     * and the data file will automatically be deleted, thus preserving safety.
     */
    public synchronized void dispose() {

        //set allready in case some concurrent access
        isDisposed = true;
        // Close the cache
        try {
            //Flush the spool if persistent, so we don't lose any data.
            writeIndex();
            //Clear in-memory data structures

            diskElements.clear();
            freeSpace.clear();
            if (randomAccessFile != null) {
                randomAccessFile.close();
            }

        }
        catch (Exception e) {
            log.error("Cache: Could not shut down disk cache", e);
        }
        finally {
            randomAccessFile = null;
        }
    }

    /**
     * Writes the Index to disk on shutdown <p/>The index consists of the elements Map and the freeSpace List <p/>Note
     * that the cache is locked for the entire time that the index is being written
     */
    private synchronized void writeIndex() throws IOException {

        ObjectOutputStream objectOutputStream = null;
        try {
            FileOutputStream fout = new FileOutputStream(indexFile);
            objectOutputStream = new ObjectOutputStream(fout);
            objectOutputStream.writeObject(diskElements);
            objectOutputStream.writeObject(freeSpace);
        }
        finally {
            if (objectOutputStream != null) {
                objectOutputStream.flush();
                objectOutputStream.close();
            }

        }
    }

    /**
     * Reads Index to disk on startup. <p/>if the index file does not exist, it creates a new one. <p/>Note that the
     * cache is locked for the entire time that the index is being written
     */
    private synchronized void readIndex() throws IOException {
        ObjectInputStream objectInputStream = null;
        FileInputStream fin = null;
        if (indexFile.exists() && persistant) {
            try {
                fin = new FileInputStream(indexFile);
                objectInputStream = new ObjectInputStream(fin);
                diskElements = (HashedMap) objectInputStream.readObject();
                freeSpace = (ArrayList) objectInputStream.readObject();
            }
            catch (StreamCorruptedException e) {
                log.error("Corrupt index file. Creating new index.");

                createNewIndexFile();
            }
            catch (IOException e) {
                log.error("IOException reading index. Creating new index. ");
                createNewIndexFile();
            }
            catch (ClassNotFoundException e) {
                log.error("Class loading problem reading index. Creating new index. ", e);
                createNewIndexFile();
            }
            finally {
                try {
                    if (objectInputStream != null) {
                        objectInputStream.close();
                    } else if (fin != null) {
                        fin.close();
                    }
                }
                catch (IOException e) {
                    log.error("Problem closing the index file.");
                }
            }
        } else {
            createNewIndexFile();
        }
    }

    private void createNewIndexFile() throws IOException {
        if (indexFile.exists()) {
            indexFile.delete();
            if (log.isDebugEnabled()) {
                log.debug("Index file " + indexFile + " deleted.");
            }
        }
        if (indexFile.createNewFile()) {
            if (log.isDebugEnabled()) {
                log.debug("Index file " + indexFile + " created successfully");
            }
        } else {
            throw new IOException("Index file " + indexFile + " could not created.");
        }
    }

    /**
     * Allocates a free block.
     */
    private DiskElement findFreeBlock(final int length) {
        for (int i = 0; i < freeSpace.size(); i++) {
            final DiskElement element = (DiskElement) freeSpace.get(i);
            if (element.blockSize >= length) {
                freeSpace.remove(i);
                return element;
            }
        }
        return null;
    }

    /**
     * Returns a {@link String}representation of the {@link DiskCaptchaBuffer}
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[ dataFile = ").append(dataFile.getAbsolutePath()).append(", totalSize=")
                .append(totalSize).append(", status=").append(isInitalized).append(" ]");
        return sb.toString();
    }

    /**
     * A reference to an on-disk elements.
     */
    private static class DiskElement implements Serializable {
        /**
         * the file pointer
         */
        private long position;

        /**
         * The size used for data.
         */
        private int payloadSize;

        /**
         * the size of this element.
         */
        private int blockSize;

    }

    /**
     * @return the total size of the data file and the index file, in bytes.
     */
    public long getTotalFileSize() {
        return getDataFileSize() + getIndexFileSize();
    }

    /**
     * @return the size of the data file in bytes.
     */
    public long getDataFileSize() {
        return dataFile.length();
    }

    /**
     * The design of the layout on the data file means that there will be small gaps created when DiskElements are
     * reused.
     *
     * @return the sparseness, measured as the percentage of space in the Data File not used for holding data
     */
    public float calculateDataFileSparseness() {
        return 1 - ((float) getUsedDataSize() / (float) getDataFileSize());
    }

    /**
     * When elements are deleted, spaces are left in the file. These spaces are tracked and are reused when new elements
     * need to be written. <p/>This method indicates the actual size used for data, excluding holes. It can be compared
     * with {@link #getDataFileSize()}as a measure of fragmentation.
     */
    public long getUsedDataSize() {
        return totalSize;
    }

    /**
     * @return the size of the index file, in bytes.
     */
    public long getIndexFileSize() {
        if (indexFile == null) {
            return 0;
        } else {
            return indexFile.length();
        }
    }

    /**
     * @return the file name of the data file where the disk store stores data, without any path information.
     */
    public String getDataFileName() {
        return name + ".data";
    }

    /**
     * @return the file name of the index file, which maintains a record of elements and their addresses on the data
     *         file, without any path information.
     */
    public String getIndexFileName() {
        return name + ".index";
    }

    /**
     * @see com.octo.captcha.engine.bufferedengine.buffer.CaptchaBuffer#removeCaptcha()
     */
    public Captcha removeCaptcha() throws NoSuchElementException {
        if (isDisposed) return null;
        return removeCaptcha(Locale.getDefault());
    }

    /**
     * @see com.octo.captcha.engine.bufferedengine.buffer.CaptchaBuffer#removeCaptcha(int)
     */
    public Collection removeCaptcha(int number) {
        if (isDisposed) return null;
        log.debug("Entering removeCaptcha(int number) ");
        Collection c = null;
        try {
            c = remove(number, Locale.getDefault());
        }
        catch (IOException e) {

            throw new CaptchaException(e);
        }
        return c;
    }

    /**
     * @see com.octo.captcha.engine.bufferedengine.buffer.CaptchaBuffer#putCaptcha(com.octo.captcha.Captcha)
     */
    public void putCaptcha(Captcha captcha) {
        log.debug("Entering putCaptcha(Captcha captcha)");
        putCaptcha(captcha, Locale.getDefault());
    }

    /**
     * @see com.octo.captcha.engine.bufferedengine.buffer.CaptchaBuffer#putAllCaptcha(java.util.Collection)
     */
    public void putAllCaptcha(Collection captchas) {
        log.debug("Entering putAllCaptcha()");

        putAllCaptcha(captchas, Locale.getDefault());
    }

    /**
     * @see com.octo.captcha.engine.bufferedengine.buffer.CaptchaBuffer#size()
     */
    public int size() {
        if (!isInitalized) return 0;
        int total = 0;
        MapIterator it = diskElements.mapIterator();
        while (it.hasNext()) {
            it.next();
            total += ((LinkedList) it.getValue()).size();
        }
        return total;
    }

    /**
     * @see com.octo.captcha.engine.bufferedengine.buffer.CaptchaBuffer#maxSize()
     */
    public int maxSize() {
        return (int) this.maxDataSize;
    }

    /**
     * @see com.octo.captcha.engine.bufferedengine.buffer.CaptchaBuffer#removeCaptcha(java.util.Locale)
     */
    public Captcha removeCaptcha(Locale locale) throws NoSuchElementException {
        log.debug("entering removeCaptcha(Locale locale)");

        Collection captchas = null;
        try {
            captchas = remove(1, locale);
        }
        catch (IOException e) {
            throw new CaptchaException(e);
        }
        if (captchas.size() == 0) {
            throw new NoSuchElementException();
        }
        return (Captcha) captchas.toArray()[0];
    }

    /**
     * @see CaptchaBuffer#removeCaptcha(int, java.util.Locale)
     */
    public Collection removeCaptcha(int number, Locale locale) {
        if (isDisposed) return null;

        try {
            return remove(number, locale);
        }
        catch (IOException e) {
            throw new CaptchaException(e);
        }
    }

    /**
     * @see CaptchaBuffer#putCaptcha(com.octo.captcha.Captcha,
     *      java.util.Locale)
     */
    public void putCaptcha(Captcha captcha, Locale locale) {
        if (isDisposed) return;

        try {
            store(captcha, locale);
        }
        catch (IOException e) {
            throw new CaptchaException(e);
        }
    }

    /**
     * @see CaptchaBuffer#putAllCaptcha(java.util.Collection,
     *      java.util.Locale)
     */
    public void putAllCaptcha(Collection captchas, Locale locale) {
        if (isDisposed) return;
        try {
            store(captchas, locale);
            log.debug("trying to store " + captchas.size());
        }
        catch (IOException e) {
            throw new CaptchaException(e);
        }
    }

    /**
     * @see com.octo.captcha.engine.bufferedengine.buffer.CaptchaBuffer#size(java.util.Locale)
     */
    public int size(Locale locale) {
        if (!isInitalized || isDisposed) return 0;
        return ((LinkedList) diskElements.get(locale)).size();
    }

    /**
     * @see com.octo.captcha.engine.bufferedengine.buffer.CaptchaBuffer#clear()
     */
    public void clear() {
        try {
            clearFile();
        }
        catch (IOException e) {
            throw new CaptchaException(e);
        }

    }

    /**
     * @see com.octo.captcha.engine.bufferedengine.buffer.CaptchaBuffer#getLocales()
     */
    public Collection getLocales() {
        if (isDisposed) return null;
        return diskElements.keySet();
    }

}