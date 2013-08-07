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
package com.octo.captcha;

import java.util.Locale;

/**
 * <p><ul><li></li></ul></p>
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public class MockCaptcha implements Captcha {
    private boolean isDisposed = false;
    private boolean asBeenCalled = false;
    private Locale locale = null;

    public static final String QUESTION = "mockQuestion";
    public static final String CHALLENGE = "mockChallenge";

    public MockCaptcha(Locale locale) {
    	this.locale = locale;
    }
    
    /**
     * Accessor captcha question.
     *
     * @return the question
     */
    public String getQuestion() {
        return QUESTION + locale;
    }

    /**
     * Accerssor for the questionned challenge.
     *
     * @return the challenge (may be an image for image captcha...
     */
    public Object getChallenge() {
        asBeenCalled = true;
        return !isDisposed ? CHALLENGE : null;
    }

    /**
     * Validation routine for the response.
     *
     * @param response to the question concerning the chalenge
     *
     * @return true if the answer is correct, false otherwise.
     */
    public Boolean validateResponse(Object response) {
        return new Boolean(response.toString());
    }

    /**
     * Dispose the challenge, once this method is call the getChallenge method will return null.<br> It has been added
     * for technical reasons : a captcha is always used in a two step fashion<br> First submit the challenge, and then
     * wait until the response arrives.<br> It had been asked to have a method to dispose the challenge that is no
     * longer used after being dipslayed. So here it is!
     */
    public void disposeChallenge() {
        isDisposed = true;
    }

    /**
     * This method should return true if the getChalenge method has been called (has been added in order to properly
     * manage the captcha state.
     *
     * @return true if getChallenge has been called false otherwise.
     */
    public Boolean hasGetChalengeBeenCalled() {
        return new Boolean(asBeenCalled);
    }

}
