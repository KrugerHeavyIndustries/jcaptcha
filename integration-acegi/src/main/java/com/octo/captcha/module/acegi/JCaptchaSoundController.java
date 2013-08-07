/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.module.acegi;

import com.octo.captcha.service.sound.SoundCaptchaService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.ByteArrayOutputStream;

/**
 * <p><ul><li></li></ul></p>
 *
 * @author <a href="mailto:marc.antoine.garrigue@gmail.com">Marc-Antoine Garrigue</a>
 * @version $Id: JCaptchaSoundController.java 322 2007-03-26 17:45:25Z antoineveret $
 */
public class JCaptchaSoundController implements Controller, InitializingBean {
    private SoundCaptchaService soundCaptchaService;

    public SoundCaptchaService getSoundCaptchaService() {
        return soundCaptchaService;
    }

    public void setSoundCaptchaService(SoundCaptchaService soundCaptchaService) {
        this.soundCaptchaService = soundCaptchaService;
    }

    public void afterPropertiesSet() throws Exception {

        if (soundCaptchaService == null) {
            throw new RuntimeException("Required soundCaptchaService not set");
        }
    }

    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse response) throws Exception {
        byte[] captchaChallengeAsWav = null;

        ByteArrayOutputStream wavOutputStream = new ByteArrayOutputStream();

        //get the session id that will identify the generated captcha.
        //the same id must be used to validate the response, the session id is a good candidate!
        String captchaId = httpServletRequest.getSession().getId();

        AudioInputStream challenge =
                soundCaptchaService.getSoundChallengeForID(captchaId, httpServletRequest.getLocale());

        // a jpeg encoder
        AudioInputStream stream = (AudioInputStream) challenge;

        // call the ImageCaptchaService method to retrieve a captcha
        AudioSystem.write(stream, AudioFileFormat.Type.WAVE, wavOutputStream);

        captchaChallengeAsWav = wavOutputStream.toByteArray();

        // configure cache  directives
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        //flush content in the response
        response.setContentType("audio/x-wav");
        ServletOutputStream responseOutputStream =
                response.getOutputStream();
        responseOutputStream.write(captchaChallengeAsWav);
        responseOutputStream.flush();
        responseOutputStream.close();
        return null;
    }


}
