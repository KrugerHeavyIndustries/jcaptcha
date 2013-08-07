/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.service;

import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.octo.captcha.Captcha;
import com.octo.captcha.engine.CaptchaEngine;
import com.octo.captcha.service.captchastore.CaptchaStore;

/**
 * This is a base class for CaptchaService implementations. It implements the lyfe cycle stuff. It uses  : a
 * CaptchaStore to store captcha during the life cycle, and a CaptchaEngine to build captchas. All concrete
 * implementation (that uses a specific capthcaStore and captchaEngine) should provide a default non argument
 * constructor (by subclassing this class, and calling the constructor of the abstract class)
 *
 * @author Marc-Antoine Garrigue mailto:mag@jcaptcha.net
 */
public abstract class AbstractCaptchaService implements CaptchaService {

    protected CaptchaStore store;
    protected CaptchaEngine engine;
    protected Log logger;


    protected AbstractCaptchaService(CaptchaStore captchaStore,
                                     CaptchaEngine captchaEngine) {
        if (captchaEngine == null || captchaStore == null)
            throw new IllegalArgumentException("Store or gimpy can't be null");
        this.engine = captchaEngine;
        this.store = captchaStore;
        
        logger = LogFactory.getLog(this.getClass());
        
        logger.info("Init " + this.store.getClass().getName());
        this.store.initAndStart();
    }


    /**
     * Method to retrive the challenge corresponding to the given ticket from the store.
     *
     * @param ID the ticket provided by the buildCaptchaAndGetID method
     * @return the challenge
     * @throws CaptchaServiceException if the ticket is invalid
     */
    public Object getChallengeForID(String ID) throws CaptchaServiceException {
        return this.getChallengeForID(ID, Locale.getDefault());
    }

    /**
     * Method to retrive the challenge corresponding to the given ticket.
     *
     * @param ID     ticket
     * @param locale the desired localized capthca
     * @return the localized challenge
     * @throws CaptchaServiceException if the ticket is invalid
     */
    public Object getChallengeForID(String ID, Locale locale)
            throws CaptchaServiceException {
        Captcha captcha;
        Object challenge;
        //check if has capthca
        if (!this.store.hasCaptcha(ID)) {
            //if not generate and store
            captcha = generateAndStoreCaptcha(locale, ID);
        } else {
            //else get it
            captcha = this.store.getCaptcha(ID);
            if (captcha == null) {
                captcha = generateAndStoreCaptcha(locale, ID);
            } else {
                //if dirty
                if (captcha.hasGetChalengeBeenCalled().booleanValue()) {
                    //get a new one and store it
                    captcha = generateAndStoreCaptcha(locale, ID);
                } 
                //else nothing
            }
        }
        challenge = getChallengeClone(captcha);
        captcha.disposeChallenge();

        return challenge;
    }


    /**
     * Method to retrive the question corresponding to the given ticket.
     *
     * @param ID     ticket
     * @param locale the desired localized capthca
     * @return the localized question
     * @throws CaptchaServiceException if the ticket is invalid
     */
    public String getQuestionForID(String ID, Locale locale) throws CaptchaServiceException {
        Captcha captcha;
        //check if has capthca
        if (!this.store.hasCaptcha(ID)) {
            //if not generate it
            captcha = generateAndStoreCaptcha(locale, ID);
        } else {
            captcha = this.store.getCaptcha(ID);
            if (captcha == null) {
                captcha = generateAndStoreCaptcha(locale, ID);
            }else if (locale != null) {
                Locale storedlocale = this.store.getLocale(ID);
                if (!locale.equals(storedlocale)) {
                captcha = generateAndStoreCaptcha(locale, ID);
                }
            }

        }
        return captcha.getQuestion();
    }

    /**
     * Method to retrive the question corresponding to the given ticket from the store.
     *
     * @param ID the ticket provided by the buildCaptchaAndGetID method
     * @return the question
     * @throws CaptchaServiceException if the ticket is invalid
     */
    public String getQuestionForID(String ID) throws CaptchaServiceException {
        return this.getQuestionForID(ID, Locale.getDefault());
    }

    /**
     * Method to validate a response to the challenge corresponding to the given ticket and remove the coresponding
     * captcha from the store.
     *
     * @param ID the ticket provided by the buildCaptchaAndGetID method
     * @return true if the response is correct, false otherwise.
     * @throws CaptchaServiceException if the ticket is invalid
     */
    public Boolean validateResponseForID(String ID, Object response)
            throws CaptchaServiceException {
        if (!store.hasCaptcha(ID)) {
            throw new CaptchaServiceException("Invalid ID, could not validate unexisting or already validated captcha");
        } else {
            Boolean valid = store.getCaptcha(ID).validateResponse(response);
            store.removeCaptcha(ID);
            return valid;
        }
    }


    protected Captcha generateAndStoreCaptcha(Locale locale, String ID) {
        Captcha captcha = engine.getNextCaptcha(locale);
        this.store.storeCaptcha(ID, captcha, locale);
        return captcha;
    }


    /**
     * This method must be implemented by sublcasses and : Retrieve the challenge from the captcha Make and return a
     * clone of the challenge Return the clone It has be design in order to let the service dispose the challenge of the
     * captcha after rendering. It should be implemented for all captcha type (@see ImageCaptchaService implementations
     * for exemple)
     *
     * @return a Challenge Clone
     */
    protected abstract Object getChallengeClone(Captcha captcha);


}
