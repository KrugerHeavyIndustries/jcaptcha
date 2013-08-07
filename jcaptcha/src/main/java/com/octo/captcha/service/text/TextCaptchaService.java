/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.service.text;

import com.octo.captcha.service.CaptchaService;
import com.octo.captcha.service.CaptchaServiceException;

import java.util.Locale;

/**
 * <p><ul><li></li></ul></p>
 *
 * @author <a href="mailto:marc.antoine.garrigue@gmail.com">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public interface TextCaptchaService extends CaptchaService {
    /**
     * Method to retrive the text challenge corresponding to the given ticket.
     *
     * @param ID the ticket
     * @return the challenge
     * @throws com.octo.captcha.service.CaptchaServiceException
     *          if the ticket is invalid
     */
    String getTextChallengeForID(String ID) throws CaptchaServiceException;

    /**
     * Method to retrieve the text challenge corresponding to the given ticket.
     *
     * @param ID the ticket
     * @return the challenge
     * @throws com.octo.captcha.service.CaptchaServiceException
     *          if the ticket is invalid
     */
    String getTextChallengeForID(String ID, Locale locale) throws CaptchaServiceException;

}
