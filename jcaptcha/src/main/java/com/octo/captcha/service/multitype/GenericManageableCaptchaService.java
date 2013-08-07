/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.service.multitype;

import java.awt.image.BufferedImage;
import java.util.Locale;

import javax.sound.sampled.AudioInputStream;

import com.octo.captcha.Captcha;
import com.octo.captcha.engine.CaptchaEngine;
import com.octo.captcha.image.ImageCaptcha;
import com.octo.captcha.service.AbstractManageableCaptchaService;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.captchastore.CaptchaStore;
import com.octo.captcha.service.captchastore.FastHashMapCaptchaStore;
import com.octo.captcha.sound.SoundCaptcha;
import com.octo.captcha.text.TextCaptcha;

/**
 * Generic and new default captchaService implementation. Can accept and serve any captcha type. <br> beware of class
 * cast exception if you call the wrong typed getChallenge method!
 *
 * @author <a href="mailto:marc.antoine.garrigue@gmail.com">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public class GenericManageableCaptchaService extends AbstractManageableCaptchaService implements MultiTypeCaptchaService {

    /**
     * Constructor with FastHashMapCaptchaStore
     *
     * @param captchaEngine the used engine. Use the {@link com.octo.captcha.engine.bufferedengine.BufferedEngineContainer}
     *                      to enable buffered captcha generation
     */
    public GenericManageableCaptchaService(CaptchaEngine captchaEngine,
                                           int minGuarantedStorageDelayInSeconds,
                                           int maxCaptchaStoreSize,
                                           int captchaStoreLoadBeforeGarbageCollection) {
        this(new FastHashMapCaptchaStore(), captchaEngine, minGuarantedStorageDelayInSeconds,
                maxCaptchaStoreSize, captchaStoreLoadBeforeGarbageCollection);
    }

    public GenericManageableCaptchaService(	CaptchaStore captchaStore, 
    										CaptchaEngine captchaEngine,
								            int minGuarantedStorageDelayInSeconds,
								            int maxCaptchaStoreSize,
								            int captchaStoreLoadBeforeGarbageCollection) {
    	super(captchaStore, captchaEngine, minGuarantedStorageDelayInSeconds,
    			maxCaptchaStoreSize, captchaStoreLoadBeforeGarbageCollection);
    }
    
    /**
     * Method to retrive the image challenge corresponding to the given ticket.
     *
     * @param ID the ticket
     *
     * @return the challenge
     *
     * @throws com.octo.captcha.service.CaptchaServiceException
     *          if the ticket is invalid
     */
    public BufferedImage getImageChallengeForID(String ID) throws CaptchaServiceException {
        return (BufferedImage) this.getChallengeForID(ID);
    }

    /**
     * Method to retrive the image challenge corresponding to the given ticket.
     *
     * @param ID the ticket
     *
     * @return the challenge
     *
     * @throws com.octo.captcha.service.CaptchaServiceException
     *          if the ticket is invalid
     */
    public BufferedImage getImageChallengeForID(String ID, Locale locale) throws CaptchaServiceException {
        return (BufferedImage) this.getChallengeForID(ID, locale);
    }

    /**
     * Method to retrive the sound challenge corresponding to the given ticket.
     *
     * @param ID the ticket
     *
     * @return the challenge
     *
     * @throws com.octo.captcha.service.CaptchaServiceException
     *          if the ticket is invalid
     */
    public AudioInputStream getSoundChallengeForID(String ID) throws CaptchaServiceException {
        return (AudioInputStream) this.getChallengeForID(ID);
    }

    /**
     * Method to retrive the sound challenge corresponding to the given ticket.
     *
     * @param ID the ticket
     *
     * @return the challenge
     *
     * @throws com.octo.captcha.service.CaptchaServiceException
     *          if the ticket is invalid
     */
    public AudioInputStream getSoundChallengeForID(String ID, Locale locale) throws CaptchaServiceException {
        return (AudioInputStream) this.getChallengeForID(ID, locale);
    }

    /**
     * Method to retrive the text challenge corresponding to the given ticket.
     *
     * @param ID the ticket
     *
     * @return the challenge
     *
     * @throws com.octo.captcha.service.CaptchaServiceException
     *          if the ticket is invalid
     */
    public String getTextChallengeForID(String ID) throws CaptchaServiceException {
        return (String) this.getChallengeForID(ID);
    }

    /**
     * Method to retrieve the text challenge corresponding to the given ticket.
     *
     * @param ID the ticket
     *
     * @return the challenge
     *
     * @throws com.octo.captcha.service.CaptchaServiceException
     *          if the ticket is invalid
     */
    public String getTextChallengeForID(String ID, Locale locale) throws CaptchaServiceException {
        return (String) this.getChallengeForID(ID, locale);
    }

    /**
     * This method : Retrieve the challenge from the captcha Make and return a clone of the challenge Return the clone
     * It has be design in order to let the service dipose the challenge of the captcha after rendering. It should be
     * implemented for all captcha type (@see ImageCaptchaService implementations for exemple)
     *
     * @return a Challenge Clone
     */
    protected Object getChallengeClone(Captcha captcha) {
        Class captchaClass = captcha.getClass();
        if (ImageCaptcha.class.isAssignableFrom(captchaClass)) {
            BufferedImage challenge = (BufferedImage) captcha.getChallenge();
            BufferedImage clone = new BufferedImage(challenge.getWidth(), challenge.getHeight(), challenge.getType());
            clone.getGraphics().drawImage(challenge, 0, 0, clone.getWidth(), clone.getHeight(), null);
            clone.getGraphics().dispose();
            return clone;
        } else if (SoundCaptcha.class.isAssignableFrom(captchaClass)) {
            AudioInputStream challenge = (AudioInputStream) captcha.getChallenge();
            AudioInputStream clone = new AudioInputStream(challenge, challenge.getFormat(), challenge.getFrameLength());
            return clone;
        } else if (TextCaptcha.class.isAssignableFrom(captchaClass)) {
            return String.valueOf(captcha.getChallenge());
        } else {
            throw new CaptchaServiceException("Unknown captcha type," +
                    " can't clone challenge captchaClass:'" + captcha.getClass() + "'");
        }


    }


}
