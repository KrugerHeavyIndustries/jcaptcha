/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.module.acegi;

import com.octo.captcha.service.CaptchaService;
import com.octo.captcha.service.CaptchaServiceException;
import org.acegisecurity.captcha.CaptchaServiceProxy;

/**
 * @author <a href="mailto:marc.antoine.garrigue@gmail.com">Marc-Antoine Garrigue</a>
 * @version $Id: JCaptchaServiceProxy.java 322 2007-03-26 17:45:25Z antoineveret $
 */
public class JCaptchaServiceProxy implements CaptchaServiceProxy {

    private CaptchaService service;


    public JCaptchaServiceProxy(CaptchaService service) {
        super();
        this.service = service;
    }


    public boolean validateReponseForId(String id, Object response) {
        if (id == null || response == null || "".equals(id)) {
            return false;
        } else {
            try {
                return service.validateResponseForID(id, response).booleanValue();
            } catch (CaptchaServiceException e) {
                return false;
            }
        }

    }
}
