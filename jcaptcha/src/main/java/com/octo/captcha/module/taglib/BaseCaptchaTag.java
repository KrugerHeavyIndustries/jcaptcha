/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.module.taglib;

import com.octo.captcha.service.CaptchaService;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

/**
 * Defines the service for the module.
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public abstract class BaseCaptchaTag implements Tag {


    protected PageContext pageContext;

    protected Tag parent;


    public void setPageContext(javax.servlet.jsp.PageContext pageContext) {
        this.pageContext = pageContext;

    }

    public void setParent(javax.servlet.jsp.tagext.Tag tag) {
        this.parent = tag;
    }

    public javax.servlet.jsp.tagext.Tag getParent() {
        return parent;
    }


    public int doStartTag() throws javax.servlet.jsp.JspException {
        return javax.servlet.jsp.tagext.Tag.SKIP_BODY;
    }

    public void release() {

    }

    public abstract int doEndTag() throws JspException;

    protected abstract CaptchaService getService();
}
