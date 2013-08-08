/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.module.acegi;

import com.octo.captcha.service.image.ImageCaptchaService;
import javax.imageio.ImageIO;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

/**
 * <p><ul><li></li></ul></p>
 *
 * @author <a href="mailto:marc.antoine.garrigue@gmail.com">Marc-Antoine Garrigue</a>
 * @version $Id: JCaptchaImageController.java 322 2007-03-26 17:45:25Z antoineveret $
 */
public class JCaptchaImageController implements Controller, InitializingBean {
    private ImageCaptchaService imageCaptchaService;

    public ImageCaptchaService getImageCaptchaService() {
        return imageCaptchaService;
    }

    public void setImageCaptchaService(ImageCaptchaService imageCaptchaService) {
        this.imageCaptchaService = imageCaptchaService;
    }

    public void afterPropertiesSet() throws Exception {

        if (imageCaptchaService == null) {
            throw new RuntimeException("Required imageCaptchaService not set");
        }
    }

    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse response) throws Exception {
        byte[] captchaChallengeAsJpeg = null;

        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();

        //get the session id that will identify the generated captcha.
        //the same id must be used to validate the response, the session id is a good candidate!
        String captchaId = httpServletRequest.getSession().getId();

        BufferedImage challenge =
                imageCaptchaService.getImageChallengeForID(captchaId, httpServletRequest.getLocale());

        ImageIO.write(challenge, "jpg", jpegOutputStream);

        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();

        // configure cache  directives
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        //flush content in the response
        response.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream =
                response.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
        return null;
    }


}
