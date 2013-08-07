/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.text;

import com.octo.captcha.Captcha;

/**
 * A text captcha is a captcha with a Text challenge... This class is abstract. 
 */
public abstract class TextCaptcha implements Captcha {

    private Boolean hasChallengeBeenCalled = Boolean.FALSE;
    protected String question;
    protected String challenge;

    protected TextCaptcha(String question, String challenge) {
        this.challenge = challenge;
        this.question = question;
    }

    /**
     * Accessor captcha question.
     *
     * @return the question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Accerssor for the questionned challenge.
     *
     * @return the challenge (may be an image for image captcha...
     */
    public Object getChallenge() {
        return getTextChallenge();
    }

    /**
     * Accerssor for the questionned challenge in text format.
     *
     * @return the challenge
     */
    public String getTextChallenge() {
        hasChallengeBeenCalled = Boolean.TRUE;
        return challenge;
    }


    /**
     * Dispose the challenge, once this method is call the getChallenge method will return null.<br> It has been added
     * for technical reasons : a captcha is always used in a two step fashion<br> First submit the challenge, and then
     * wait until the response arrives.<br> It had been asked to have a method to dispose the challenge that is no
     * longer used after being dipslayed. So here it is!
     */
    public void disposeChallenge() {
        this.challenge = null;

    }

    /**
     * This method should return true if the getChalenge method has been called (has been added in order to properly
     * manage the captcha state.
     *
     * @return true if getChallenge has been called false otherwise.
     */
    public Boolean hasGetChalengeBeenCalled() {
        return hasChallengeBeenCalled;
    }
}
