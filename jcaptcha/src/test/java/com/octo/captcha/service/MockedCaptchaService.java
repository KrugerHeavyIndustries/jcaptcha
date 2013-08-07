/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.service;

import com.octo.captcha.Captcha;
import com.octo.captcha.engine.CaptchaEngine;
import com.octo.captcha.engine.MockCaptchaEngine;
import com.octo.captcha.service.captchastore.CaptchaStore;
import com.octo.captcha.service.captchastore.MapCaptchaStore;

/**
 * <p><ul><li></li></ul></p>
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public class MockedCaptchaService extends AbstractCaptchaService {
    public static final String CLONE_CHALLENGE = "clonedChallenge";

    public MockedCaptchaService() {
        super(new MapCaptchaStore(), new MockCaptchaEngine());
    }

    protected MockedCaptchaService(CaptchaStore captchaStore, CaptchaEngine captchaEngine) {
        super(captchaStore, captchaEngine);
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
        return new String(captcha.getChallenge().toString()) + CLONE_CHALLENGE;
    }


}
