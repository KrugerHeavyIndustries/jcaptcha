/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.module.struts.image;

import com.octo.captcha.module.config.CaptchaModuleConfigHelper;
import com.octo.captcha.module.struts.CaptchaServicePlugin;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

/**
 * <p><ul><li></li></ul></p>
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public class RenderImageCaptchaAction extends Action {
    private Log log = LogFactory.getLog(RenderImageCaptchaAction.class);


    public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm,
                                 HttpServletRequest httpServletRequest,
                                 HttpServletResponse httpServletResponse)
            throws Exception {

        ImageCaptchaService service = (ImageCaptchaService)
                CaptchaServicePlugin.getInstance().getService();
        String captchaID = CaptchaModuleConfigHelper.getId(httpServletRequest);
        //(String) theRequest.getParameter(captchaIDParameterName);

        // call the ManageableImageCaptchaService methods
        byte[] captchaChallengeAsJpeg = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            BufferedImage challenge =
                    service.getImageChallengeForID(captchaID,
                            httpServletRequest.getLocale());
            // the output stream to render the captcha image as jpeg into

            // a jpeg encoder
            JPEGImageEncoder jpegEncoder =
                    JPEGCodec.createJPEGEncoder(jpegOutputStream);
            jpegEncoder.encode(challenge);
        } catch (IllegalArgumentException e) {
            // log a security warning and return a 404...
            if (log.isWarnEnabled()) {
                log.warn("There was a try from "
                        + httpServletRequest.getRemoteAddr()
                        + " to render an URL without ID"
                        + " or with a too long one");
                httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
                log.error("should never pass here!");
                return actionMapping.findForward("error");
            }
        } catch (CaptchaServiceException e) {
            // log and return a 404 instead of an image...
            log.warn("Error trying to generate a captcha and "
                    + "render its challenge as JPEG",
                    e);
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            // log.error("should never pass here!");
            return actionMapping.findForward("error");
        }

        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();

        // render the captcha challenge as a JPEG image in the response
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream =
                httpServletResponse.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
        // log.error("should never pass here!");
        return null;
    }

}

