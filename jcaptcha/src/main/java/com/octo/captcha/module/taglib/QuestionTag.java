/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.module.taglib;

import java.io.IOException;

/**
 * <p><ul><li></li></ul></p>
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public abstract class QuestionTag extends BaseCaptchaTag implements javax.servlet.jsp.tagext.Tag {

    public int doEndTag() throws javax.servlet.jsp.JspException {
        String question = getService().getQuestionForID(pageContext.getSession().getId()
                , pageContext.getRequest().getLocale());

        try {
            pageContext.getOut().write(question);
        } catch (IOException e) {
            throw new javax.servlet.jsp.JspException(e);
        }
        return javax.servlet.jsp.tagext.Tag.EVAL_PAGE;
    }


}
