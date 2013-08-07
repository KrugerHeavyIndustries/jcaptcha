package com.octo.captcha.confluence;

import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;
import com.octo.captcha.service.image.ImageCaptchaService;
import com.octo.captcha.engine.GenericCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * @author mag
 * @Date 13 nov. 2008
 */
public class JcaptchaImageServlet extends javax.servlet.http.HttpServlet {

	private static final long serialVersionUID = 5167034008137243766L;



    public void init() throws ServletException {
        super.init();

    }

    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        String hashString = httpServletRequest.getParameter("config");
        Integer hash;
        try {
            hash =new Integer(hashString);
        } catch (NumberFormatException e) {
            httpServletResponse.getOutputStream().write(("invalid config number : "+e.getMessage()).getBytes());
            return;
        }
        byte[] captchaChallengeAsJpeg = null;
            // the output stream to render the captcha image as jpeg into
             ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
             try {
         
             // call the ImageCaptchaService getChallenge method
                 BufferedImage challenge =
                         ((GimpyFactory)JcaptchaImageMacro.engineRegistry.get(hash)).getImageCaptcha().getImageChallenge();

                 // a jpeg encoder
                 JPEGImageEncoder jpegEncoder =
                         JPEGCodec.createJPEGEncoder(jpegOutputStream);
                 jpegEncoder.encode(challenge);
             } catch (IllegalArgumentException e) {
                 httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
                 return;
             } catch (CaptchaServiceException e) {
                 httpServletResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                 return;
             }

             captchaChallengeAsJpeg = jpegOutputStream.toByteArray(); 
             // flush it in the response
             httpServletResponse.setHeader("Cache-Control", "no-store");
             httpServletResponse.setHeader("Pragma", "no-cache");
             httpServletResponse.setDateHeader("Expires", 0);
             httpServletResponse.setContentType("image/jpeg");
             ServletOutputStream responseOutputStream =
                     httpServletResponse.getOutputStream();
             responseOutputStream.write(captchaChallengeAsJpeg);
             responseOutputStream.flush();
             responseOutputStream.close();

    }
}