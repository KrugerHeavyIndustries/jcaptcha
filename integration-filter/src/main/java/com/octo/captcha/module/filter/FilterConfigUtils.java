/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.module.filter;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

/**
 * A class that provides methods to extract init parameters from a FilterConfig
 *
 * @author <a href="mailto:sbr@octo.com">Sebastien Brunot</a>
 * @version $Id: FilterConfigUtils.java 322 2007-03-26 17:45:25Z antoineveret $
 */
public class FilterConfigUtils {
    /**
     * Get a String init parameter from a FilterConfig
     *
     * @param theFilterConfig      the FilterConfig from wich the parameter should be extracted
     * @param theInitParameterName the name of the init parameter
     * @param isMandatory          a boolean indicating if the init parameter is mandatory
     *
     * @return the init parameter value as a string, or null if this init parameter is not defined but not mandatory
     *
     * @throws javax.servlet.ServletException is the initParameter is undefined whereas mandatory (a message is provided
     *                                        in the exception)
     */
    public static String getStringInitParameter(FilterConfig theFilterConfig,
                                                String theInitParameterName,
                                                boolean isMandatory)
            throws ServletException {
        String returnedValue =
                theFilterConfig.getInitParameter(theInitParameterName);
        if (isMandatory && (returnedValue == null)) {
            throw new ServletException(theInitParameterName
                    + " parameter must be declared for "
                    + theFilterConfig.getFilterName()
                    + " in web.xml");
        }
        return returnedValue;
    }

    /**
     * Get an Integer init parameter from a FilterConfig
     *
     * @param theFilterConfig      the FilterConfig from wich the parameter should be extracted
     * @param theInitParameterName the name of the init parameter
     * @param isMandatory          a boolean indicating if the init parameter is mandatory
     * @param theMinValue          the minimum value the init parameter can have
     * @param theMaxValue          the maximum value the init parameter can have
     *
     * @return the init parameter value as an Integer, or null if this Integer parameter is not defined but not
     *         mandatory
     *
     * @throws javax.servlet.ServletException if : <UL> <LI>the initParameter is undefined whereas mandatory </LI>
     *                                        <LI>the initParameter is defined but is not an integer value </LI> <LI>the
     *                                        initParameter is < minValue or > maxValue </LI> </UL> (a message is
     *                                        provided in the exception for each case).
     */
    public static Integer getIntegerInitParameter(FilterConfig theFilterConfig,
                                                  String theInitParameterName,
                                                  boolean isMandatory,
                                                  int theMinValue,
                                                  int theMaxValue)
            throws ServletException {
        Integer returnedValue = null;
        String returnedValueAsString =
                theFilterConfig.getInitParameter(theInitParameterName);
        if (isMandatory && returnedValueAsString == null) {
            throw new ServletException(theInitParameterName
                    + " parameter must be declared for "
                    + theFilterConfig.getFilterName()
                    + " in web.xml");
        }
        try {
            returnedValue = new Integer(returnedValueAsString);
        } catch (NumberFormatException e) {
            throw new ServletException(theInitParameterName
                    + " parameter must be an integer value "
                    + theFilterConfig.getFilterName()
                    + " in web.xml");
        }
        if ((returnedValue.intValue() < theMinValue)
                || (returnedValue.intValue() > theMaxValue)) {
            throw new ServletException(theInitParameterName
                    + " parameter for "
                    + theFilterConfig.getFilterName()
                    + " in web.xml must be >= "
                    + theMinValue
                    + " and <= "
                    + theMaxValue);
        }
        return returnedValue;
    }

    /**
     * Get a boolean init parameter from a FilterConfig
     *
     * @param theFilterConfig      the FilterConfig from wich the parameter should be extracted
     * @param theInitParameterName the name of the init parameter
     * @param isMandatory          a boolean indicating if the init parameter is mandatory
     *
     * @return the init parameter value as a boolean, or null if this init parameter is not defined but not mandatory
     *
     * @throws javax.servlet.ServletException is the initParameter is undefined whereas mandatory (a message is provided
     *                                        in the exception)
     */
    public static boolean getBooleanInitParameter(FilterConfig theFilterConfig,
                                                  String theInitParameterName,
                                                  boolean isMandatory)
            throws ServletException {
        String returnedValueAsString =
                theFilterConfig.getInitParameter(theInitParameterName);
        if (isMandatory && (returnedValueAsString == null)) {
            throw new ServletException(theInitParameterName
                    + " parameter must be declared for "
                    + theFilterConfig.getFilterName()
                    + " in web.xml");
        }
        boolean returnedValue = false;
        if (returnedValueAsString != null) {
            returnedValue = new Boolean(returnedValueAsString).booleanValue();
        }
        return returnedValue;
    }

}
