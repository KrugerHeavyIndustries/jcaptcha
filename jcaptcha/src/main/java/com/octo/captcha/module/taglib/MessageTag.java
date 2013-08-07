/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.module.taglib;

import com.octo.captcha.module.config.CaptchaModuleConfig;
import com.octo.captcha.service.CaptchaService;

import java.io.IOException;

/**
 * <p><ul><li></li></ul></p>
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public class MessageTag extends BaseCaptchaTag implements javax.servlet.jsp.tagext.Tag {

    private String messageKey = CaptchaModuleConfig.getInstance().getMessageKey();

    public int doEndTag() throws javax.servlet.jsp.JspException {


        String message = (String) pageContext.getRequest().getAttribute(messageKey);
        if (message != null) {
            try {
                pageContext.getOut().write(message);
            } catch (IOException e) {
                throw new javax.servlet.jsp.JspException(e);
            }

        }
        return javax.servlet.jsp.tagext.Tag.EVAL_PAGE;
    }


    protected CaptchaService getService() {
        return null;
    }
}
