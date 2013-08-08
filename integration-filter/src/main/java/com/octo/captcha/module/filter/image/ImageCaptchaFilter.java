/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.module.filter.image;

import com.octo.captcha.module.filter.FilterConfigUtils;
import com.octo.captcha.module.jmx.JMXRegistrationHelper;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.ManageableCaptchaService;
import com.octo.captcha.service.image.ImageCaptchaService;
import javax.imageio.ImageIO;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.StringTokenizer;

/**
 * ImageCaptchaFilter is a J2EE Filter designed to add image captchas to the entry forms of existing MVC web
 * applications. <br> Current features of ImageCaptchaFilter are: <ul> <li> Generation and rendering of Captcha images
 * (as JPEG) at runtime for inclusion in an existing entry form. ImageCaptchaFilter use a ImageCaptchaEngine to generate
 * captchas : a simple one (com.octo.captcha.image.gimpy.MultipleGimpyEngine, which displays a random string composed
 * with characters A,B,C,D and E) is provided with jcaptcha-j2ee, but you can code your own or use one of those provided
 * with jcaptcha-sample ; </li> <li> Verification of the HTTP client entry in response to the challenge displayed as an
 * image in the entry form: Redirection of the HTTP client to an error page if the captcha challenge is not passed ;
 * Transparent follow up of the request to the web application, without any information concerning the captcha, if the
 * challenge is successfully passed ; Many forms can be protected by captchas in the same web application, each one
 * having its own error redirection page ; </li> </ul> The JMX Management interface for ImageCaptchaFilter is
 * PixCaptchaFilterMbean.
 *
 * @author <a href="mailto:sbr@octo.com">Sebastien Brunot</a>
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine GARRIGUE</a>
 * @version $Id: ImageCaptchaFilter.java 322 2007-03-26 17:45:25Z antoineveret $
 */
public class ImageCaptchaFilter implements Filter {
    ////////////////////////////////////
    // Constants
    ////////////////////////////////////


    /**
     * Name under witch the CaptchaFilter is registered to an MBean server
     */
    public static final String JMX_REGISTERING_NAME =
            "com.octo.captcha.module.servlet:object=ImageCaptchaFilter";

    /**
     * The delimiter used to specify values as CSV in a string in web.xml (used for verification URLs, etc...)
     */
    public static final String CSV_DELIMITER = ";";

    ////////////////////////////////////
    // Private attributes
    ////////////////////////////////////

    /**
     * Logger (commons-logging)
     */
//    private static Log log = LogFactory.getLog(ImageCaptchaFilter.class);


    /**
     * The name of the filter parameter in web.xml for captchaRenderingURL
     */
    public static final String CAPTCHA_ERROR_URL_PARAMETER =
            "CaptchaErrorURL";

    /**
     * The name of the filter parameter in web.xml for captchaRenderingURL
     */
    public static final String CAPTCHA_RENDERING_URL_PARAMETER =
            "CaptchaRenderingURL";

    /**
     * The name of the filter parameter in web.xml for the CSV list of URLs that commands the captcha verification
     */
    public static final String CAPTCHA_VERIFICATION_URLS_PARAMETER =
            "CaptchaVerificationURLs";

    /**
     * The name of the filter parameter in web.xml for the CSV list of URLs (one for each verification URL) to which the
     * request should be forwarded, after cleaning captchaID and the captchaChallengeResponse from it, if the
     * challengeResponse is uncorrect (filter parameter to define in web.xml)
     */
    public static final String CAPTCHA_FAIL_URLS_PARAMETER =
            "CaptchaFailURLs";

//    /**
//     * The name of the filter parameter in web.xml for captchaIDParameterName
//     */
//    public static final String CAPTCHA_ID_PARAMETER_NAME_PARAMETER =
//            "CaptchaIDParameterName";
    /**
     * The name of the filter parameter in web.xml for captchaQuestion
     */
    public static final String CAPTCHA_QUESTION_NAME_PARAMETER =
            "CaptchaQuestionParameterName";


    /**
     * The name of the filter parameter in web.xml for captchaChallengeResponseParameterName
     */
    public static final String CAPTCHA_RESPONSE_PARAMETER_NAME_PARAMETER =
            "CaptchaChallengeResponseParameterName";

    /**
     * The name of the filter parameter in web.xml for the internal ManageableImageCaptchaService
     * ManageableImageCaptchaService.ENGINE_CLASS_INIT_PARAMETER_PROP initialization parameter.
     */
    private static final String CAPTCHA_SERVICE_CLASS_PARAMETER =
            "ImageCaptchaServiceClass";


    /**
     * The name of the filter parameter in web.xml for captchaRegisterToMBeanServer
     */
    private static final String CAPTCHA_REGISTER_TO_MBEAN_SERVER_PARAMETER =
            "RegisterToMBeanServer";

//    /**
//     * The name of the request parameter that contains the captcha ID
//     * (filter parameter to define in web.xml)
//     */
//    private static String captchaIDParameterName = null;

    /**
     * A boolean that signal if the CaptchaFilter should be registered to the MBean Server in the Application Server
     * (default value is false) (filter parameter to define in web.xml)
     */
    private boolean captchaRegisterToMBeanServer = false;


    /**
     * The name of the request parameter that contains the captcha ID (filter parameter to define in web.xml)
     */
    private static String captchaQuestionParameterName = null;

    /**
     * The ImageCaptchaService internaly used by the filter
     */
    private ImageCaptchaService captchaService = null;

    /**
     * The URL that commands a new captcha creation and its rendering as a jpg image (filter parameter to define in
     * web.xml)
     */
    private static String captchaRenderingURL = null;

    /**
     * The URL that commands a new captcha creation and its rendering as a jpg image (filter parameter to define in
     * web.xml)
     */
    private static String captchaErrorURL = null;


    /**
     * The name of the request parameter that contains the challenge response to match (filter parameter to define in
     * web.xml)
     */
    private static String captchaChallengeResponseParameterName = null;


    public static String getCaptchaRenderingURL() {
        return captchaRenderingURL;
    }

    public static String getCaptchaQuestionParameterName() {
        return captchaQuestionParameterName;
    }

//    public static String getCaptchaIDParameterName() {
//        return captchaIDParameterName;
//    }

    public static String getCaptchaChallengeResponseParameterName() {
        return captchaChallengeResponseParameterName;
    }

    /**
     * A hashmap that contains information about forwardError URL for a verification URL. The verificationURL is the
     * key, the stored object is a String (the URL to forward to if there is a verification error). (this map is
     * initialized with filter parameters values defined in web.xml)
     */
    protected Hashtable verificationForwards = new Hashtable();

    /**
     * The ImageService Class name
     */
    protected String captchaServiceClassName;

    ////////////////////////////////////
    // Filter implementation
    ////////////////////////////////////

    /**
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(final FilterConfig theFilterConfig)
            throws ServletException {

        // get rendering URL from web.xml
        this.captchaRenderingURL =
                FilterConfigUtils.getStringInitParameter(theFilterConfig,
                        CAPTCHA_RENDERING_URL_PARAMETER,
                        true);

        // get rendering URL from web.xml
        captchaErrorURL =
                FilterConfigUtils.getStringInitParameter(theFilterConfig,
                        CAPTCHA_ERROR_URL_PARAMETER,
                        true);

        // get verification URLs from web.xml (CSV list of URLs)
        String captchaVerificationURLs =
                FilterConfigUtils.getStringInitParameter(theFilterConfig,
                        CAPTCHA_VERIFICATION_URLS_PARAMETER,
                        true);

        // get forward error URLs from web.xml (CSV list of URLs)
        String captchaForwardErrorURLs =
                FilterConfigUtils.getStringInitParameter(theFilterConfig,
                        CAPTCHA_FAIL_URLS_PARAMETER,
                        true);

        // initialize the verificationForwards hashtable
        StringTokenizer verificationURLs =
                new StringTokenizer(captchaVerificationURLs, CSV_DELIMITER, false);
        StringTokenizer forwardErrorURLs =
                new StringTokenizer(captchaForwardErrorURLs, CSV_DELIMITER, false);
        if (verificationURLs.countTokens() != forwardErrorURLs.countTokens()) {
            // The URL lists are not consistant (there should be a forward and
            // a success for each verification URL)
            throw new ServletException(CAPTCHA_VERIFICATION_URLS_PARAMETER
                    + " and "
                    + CAPTCHA_FAIL_URLS_PARAMETER
                    + " values are not consistant in web.xml : there should be"
                    + " exactly one forward error for each verification URL !");
        }
        while (verificationURLs.hasMoreTokens()) {
            // Create a ForwardInfo for each verification URL and store it in
            // the verificationForward hashtable
            this.verificationForwards.put(verificationURLs.nextToken(),
                    forwardErrorURLs.nextToken());
        }
        //   get captcha Question parameter name from web.xml
        this.captchaQuestionParameterName =
                FilterConfigUtils.getStringInitParameter(theFilterConfig,
                        CAPTCHA_QUESTION_NAME_PARAMETER, true);

        // get captcha ID parameter name from web.xml
//        this.captchaIDParameterName =
//                FilterConfigUtils.getStringInitParameter(theFilterConfig,
//                        CAPTCHA_ID_PARAMETER_NAME_PARAMETER,
//                        true);

        // get challenge response parameter name from web.xml
        this.captchaChallengeResponseParameterName =
                FilterConfigUtils.getStringInitParameter(theFilterConfig,
                        CAPTCHA_RESPONSE_PARAMETER_NAME_PARAMETER,
                        true);

        // get service parameter class name from web.xml
        this.captchaServiceClassName =
                FilterConfigUtils.getStringInitParameter(theFilterConfig,
                        CAPTCHA_SERVICE_CLASS_PARAMETER,
                        true);

        // get from web.xml the indicator signaling if the CaptchaFilter
        // should be registered to an MBean Server
        this.captchaRegisterToMBeanServer =
                FilterConfigUtils.getBooleanInitParameter(theFilterConfig,
                        CAPTCHA_REGISTER_TO_MBEAN_SERVER_PARAMETER,
                        false);

        // create the ImageCaptchaService
        try {
            this.captchaService = (ImageCaptchaService) Class.forName(captchaServiceClassName).newInstance();
        } catch (InstantiationException e) {
            //log.debug("message", e);

            throw new CaptchaServiceException(e);
        } catch (IllegalAccessException e) {
            //log.debug("message", e);

            throw new CaptchaServiceException(e);
        } catch (ClassNotFoundException e) {
            //log.debug("message", e);
            throw new CaptchaServiceException(e);
        }
        // register the ImageCaptchaService to an MBean server if specified
        if (this.captchaRegisterToMBeanServer && captchaService instanceof ManageableCaptchaService) {
            ManageableCaptchaService manageable = (ManageableCaptchaService) captchaService;
            JMXRegistrationHelper.registerToMBeanServer(manageable, JMX_REGISTERING_NAME);
        }


    }

    /**
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse,
     *      javax.servlet.FilterChain)
     */
    public void doFilter(final ServletRequest theRequest,
                         final ServletResponse theResponse,
                         final FilterChain theFilterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) theRequest;
        HttpServletResponse response = (HttpServletResponse) theResponse;

        // Get the URL the user asked for
        StringBuffer servletPathBuff = new StringBuffer()
                .append(request.getServletPath());
        if (request.getQueryString() != null) servletPathBuff.append("?").append(request.getQueryString());
        String servletPathInfo = servletPathBuff.toString();
        if (servletPathInfo.startsWith(this.captchaRenderingURL)) {
            // This is the URL used to ask for captcha generation : do it !
            try {
                this.generateAndRenderCaptcha(request, response);
            } catch (Throwable e) {
                // Redirect to the error URL

                response.sendRedirect(captchaErrorURL);
                e.printStackTrace();
            }
        } else if (this.verificationForwards.containsKey(servletPathInfo)) {
            // This is the URL used to ask for captcha challenge verification :
            // do it !
            this.verifyAnswerToACaptchaChallenge(request,
                    response,
                    servletPathInfo,
                    theFilterChain);
        } else {
            // this is a question : add it and follow to the filter chain...
            addQuestionToRequest(request, response);
            theFilterChain.doFilter(theRequest, theResponse);
        }
    }

    /**
     * Remove from mbean server if needs to
     */
    public void destroy() {
        if (captchaService instanceof ManageableCaptchaService && this.captchaRegisterToMBeanServer) {
            ManageableCaptchaService manageable = (ManageableCaptchaService) captchaService;
            JMXRegistrationHelper.unregisterFromMBeanServer(JMX_REGISTERING_NAME);
        }
    }

    ////////////////////////////////////
    // Private methods
    ////////////////////////////////////


    /**
     * Add the localized captcha to the current request as an attribute, using the CaptchaQuestionParameterName.
     */
    private void addQuestionToRequest(HttpServletRequest theRequest, HttpServletResponse theResponse) {
        String captchaID = theRequest.getSession().getId();//(String) theRequest.getParameter(captchaIDParameterName);
        String question = this.captchaService.getQuestionForID(captchaID, theRequest.getLocale());
        theRequest.setAttribute(getCaptchaQuestionParameterName(), question);
    }

    /**
     * Generate a new ImageCaptcha, store it in the internal store and render it as JPEG to the client. Captcha are
     * localized using request locale ID used are session ID This method returns a 404 to the client instead of the
     * image if the request isn't correct (missing parameters, etc...).
     *
     * @param theRequest  the request
     * @param theResponse the response
     *
     * @throws java.io.IOException if a problem occurs during the jpeg generation process
     */
    private void generateAndRenderCaptcha(HttpServletRequest theRequest,
                                          HttpServletResponse theResponse)
            throws IOException {


        String captchaID = theRequest.getSession().getId();//(String) theRequest.getParameter(captchaIDParameterName);

        // call the ManageableImageCaptchaService methods
        byte[] captchaChallengeAsJpeg = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            BufferedImage challenge =
                    this.captchaService.getImageChallengeForID(captchaID, theRequest.getLocale());
            // the output stream to render the captcha image as jpeg into

            ImageIO.write(challenge, "jpg", jpegOutputStream);

        } catch (IllegalArgumentException e) {
            // log a security warning and return a 404...
//            if (log.isWarnEnabled())
//            {
//                log.warn(
//                    "There was a try from "
//                        + theRequest.getRemoteAddr()
//                        + " to render an URL without ID"
//                        + " or with a too long one");
//                theResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
//            }
        } 

        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();

        // render the captcha challenge as a JPEG image in the response
        theResponse.setHeader("Cache-Control", "no-store");
        theResponse.setHeader("Pragma", "no-cache");
        theResponse.setDateHeader("Expires", 0);

        theResponse.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream =
                theResponse.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);

    }

    /**
     * Verify client answer to a captcha challenge, and forward to the correponding success or error URL. This method
     * returns a 404 to the client instead of forwarding if the request isn't correct (missing parameters, etc...).
     *
     * @param theRequest         the request
     * @param theResponse        the response
     * @param theVerificationURL the verification URL that was called
     *
     * @throws java.io.IOException            if the image tranformation fails
     * @throws javax.servlet.ServletException if a servlet exception occurs
     */
    private void verifyAnswerToACaptchaChallenge(HttpServletRequest theRequest,
                                                 HttpServletResponse theResponse,
                                                 String theVerificationURL,
                                                 FilterChain theFilterChain)
            throws IOException, ServletException {
        // get captcha ID from the session!!
        String captchaID = theRequest.getSession().getId();//theRequest.getParameter(captchaIDParameterName);

        // get challenge response from the request
        String challengeResponse =
                theRequest.getParameter(captchaChallengeResponseParameterName);
        if (challengeResponse == null) {
            // If the challenge response is not specified, forward error
            this.redirectError(theVerificationURL, theRequest, theResponse);
            return;
        }

        // Call the ManageableImageCaptchaService method
        Boolean isResponseCorrect = Boolean.FALSE;
        try {
            isResponseCorrect =
                    this.captchaService.validateResponseForID(captchaID,
                            challengeResponse);
        } catch (CaptchaServiceException e) {
            // nothing to do : isResponseCorrect is false
            // so the user will be redirected to the error page
        }

        // forward user to the success URL or redirect it to the error URL
        if (isResponseCorrect.booleanValue()) {
            // clean the request and call the next element in filter chain
            // (forward success)
            this.forwardSuccess(theFilterChain, theRequest, theResponse);
        } else {
            // forward
            this.redirectError(theVerificationURL, theRequest, theResponse);
        }
    }

    /**
     * Redirect request to the Error URL
     *
     * @param theVerificationURL the verification URL for which there is an error redirect
     * @param theRequest         the request
     * @param theResponse        the response
     *
     * @see javax.servlet.RequestDispatcher#forward(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
     */
    private void redirectError(String theVerificationURL,
                               HttpServletRequest theRequest,
                               HttpServletResponse theResponse)
            throws ServletException {
        this.removeParametersFromRequest(theRequest);
        try {
            // Redirect to the error URL
            String forwardErrorURL =
                    theRequest.getContextPath()
                            + (String) this.verificationForwards.get(theVerificationURL);
            theResponse.sendRedirect(forwardErrorURL);
        } catch (IOException e) {
            throw new ServletException(e);
        }
    }

    /**
     * Clean the request and call the next element in filter chain
     *
     * @param theFilterChain the filter chain
     * @param theRequest     the request
     * @param theResponse    the response
     *
     * @see javax.servlet.RequestDispatcher#forward(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
     */
    private void forwardSuccess(FilterChain theFilterChain,
                                HttpServletRequest theRequest,
                                HttpServletResponse theResponse)
            throws ServletException {
        this.removeParametersFromRequest(theRequest);
        try {
            theFilterChain.doFilter(theRequest, theResponse);
        } catch (IOException e) {
            throw new ServletException(e);
        }
    }

    /**
     * Remove captcha filter specific parameters from a request
     *
     * @param theRequest the request
     */
    private void removeParametersFromRequest(HttpServletRequest theRequest) {
        theRequest.removeAttribute(getCaptchaChallengeResponseParameterName());
        theRequest.removeAttribute(getCaptchaQuestionParameterName());
    }
}
