/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.service.sound;

import com.octo.captcha.Captcha;
import com.octo.captcha.service.AbstractManageableCaptchaService;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.captchastore.CaptchaStore;

import javax.sound.sampled.AudioInputStream;
import java.util.Locale;

/**
 * Base implementation of the SoundCaptchaService.
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public abstract class AbstractManageableSoundCaptchaService extends AbstractManageableCaptchaService
        implements SoundCaptchaService {

    protected AbstractManageableSoundCaptchaService(CaptchaStore captchaStore,
                                                    com.octo.captcha.engine.CaptchaEngine captchaEngine,
                                                    int minGuarantedStorageDelayInSeconds,
                                                    int maxCaptchaStoreSize,
                                                    int captchaStoreLoadBeforeGarbageCollection) {
        super(captchaStore, captchaEngine,
                minGuarantedStorageDelayInSeconds, maxCaptchaStoreSize,
                captchaStoreLoadBeforeGarbageCollection);
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
    public AudioInputStream getSoundChallengeForID(String ID) throws CaptchaServiceException {
        return (AudioInputStream) this.getChallengeForID(ID);
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
    public AudioInputStream getSoundChallengeForID(String ID, Locale locale) throws CaptchaServiceException {
        return (AudioInputStream) this.getChallengeForID(ID, locale);
    }

    /**
     * This method must be implemented by sublcasses and : Retrieve the challenge from the captcha Make and return a
     * clone of the challenge Return the clone It has be design in order to let the service dipose the challenge of the
     * captcha after rendering. It should be implemented for all captcha type (@see ImageCaptchaService implementations
     * for exemple)
     *
     * @return a Challenge Clone
     */
    protected Object getChallengeClone(Captcha captcha) {
        AudioInputStream challenge = (AudioInputStream) captcha.getChallenge();
        AudioInputStream clone = new AudioInputStream(challenge, challenge.getFormat(), challenge.getFrameLength());
        return clone;
    }
}
