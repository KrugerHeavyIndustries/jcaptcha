/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.service.image;

import com.octo.captcha.Captcha;
import com.octo.captcha.engine.CaptchaEngine;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.EhcacheManageableCaptchaService;

import java.awt.image.BufferedImage;
import java.util.Locale;

/**
 * Base implementation of the EhcacheManageableImageCaptchaService.
 * @deprecated
 */
public abstract class EhcacheManageableImageCaptchaService extends EhcacheManageableCaptchaService implements ImageCaptchaService {

    protected EhcacheManageableImageCaptchaService(CaptchaEngine captchaEngine, int minGuarantedStorageDelayInSeconds, int maxCaptchaStoreSize) {
        super(captchaEngine, minGuarantedStorageDelayInSeconds, maxCaptchaStoreSize);
    }

    protected EhcacheManageableImageCaptchaService(CaptchaEngine captchaEngine, int minGuarantedStorageDelayInSeconds, int maxCaptchaStoreSize, String cacheName) {
        super(captchaEngine, minGuarantedStorageDelayInSeconds, maxCaptchaStoreSize, cacheName);
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
     * This method must be implemented by sublcasses and : Retrieve the challenge from the captcha Make and return a
     * clone of the challenge Return the clone It has be design in order to let the service dipose the challenge of the
     * captcha after rendering. It should be implemented for all captcha type (@see ImageCaptchaService implementations
     * for exemple)
     *
     * @return a Challenge Clone
     */
    protected Object getChallengeClone(Captcha captcha) {
        BufferedImage challenge = (BufferedImage) captcha.getChallenge();
        BufferedImage clone = new BufferedImage(challenge.getWidth(), challenge.getHeight(), challenge.getType());

        clone.getGraphics().drawImage(challenge, 0, 0, clone.getWidth(), clone.getHeight(), null);
        clone.getGraphics().dispose();


        return clone;
    }

}
