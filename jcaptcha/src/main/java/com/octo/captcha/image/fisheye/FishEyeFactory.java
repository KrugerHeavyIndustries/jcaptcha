/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.image.fisheye;

import com.octo.captcha.CaptchaException;
import com.octo.captcha.CaptchaQuestionHelper;
import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.deformation.ImageDeformation;
import com.octo.captcha.image.ImageCaptcha;
import com.octo.captcha.image.ImageCaptchaFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;
import java.util.Locale;
import java.util.Random;

/**
 * This factory use a Backgroud generator to retrieve a picture, Selects a random square center for the deformation, and
 * apply it.
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public class FishEyeFactory extends ImageCaptchaFactory {

    public static final String BUNDLE_QUESTION_KEY = FishEye.class.getName();

    private Random myRandom = new SecureRandom();
    private BackgroundGenerator generator;
    private ImageDeformation deformation;
    private Integer tolerance;
    private Integer scale;

    /**
     * Construct a new fishEye factory
     *
     * @param generator   the picture generator
     * @param deformation a deformation to be apply on the background
     * @param scale       the size of the defprmed part (percent)
     * @param tolerance   the tolerence (see FishEye)
     * @see FishEye
     */
    public FishEyeFactory(BackgroundGenerator generator,
                          ImageDeformation deformation, Integer scale,
                          Integer tolerance) {
        if (generator == null) {
            throw new CaptchaException("Invalid configuration for a FishEyeFactory "
                    + ": BackgroundGenerator can't be null");
        }
        if (deformation == null) {
            throw new CaptchaException("Invalid configuration "
                    + "for a FishEyeFactory : ImageDeformation"
                    + " can't be null");
        }
        this.deformation = deformation;
        this.generator = generator;
        if (scale == null || scale.intValue() < 1 || scale.intValue() > 99) {
            throw new CaptchaException("Invalid configuration for a"
                    + " FishEyeFactory : scale"
                    + " can't be null, and must be between 1 and 99");
        }
        this.scale = scale;
        if (tolerance == null || tolerance.intValue() < 0) {
            throw new CaptchaException("Invalid configuration for"
                    + " a FishEyeFactory : tolerance"
                    + " can't be null, and must be positive");
        }

        this.tolerance = tolerance;
    }

    /**
     * gimpies are ImageCaptcha
     *
     * @return the image captcha with default locale
     */
    public ImageCaptcha getImageCaptcha() {
        return getImageCaptcha(Locale.getDefault());
    }

    /**
     * gimpies are ImageCaptcha
     *
     * @return a pixCaptcha with the question :"spell the word"
     */
    public ImageCaptcha getImageCaptcha(Locale locale) {
        BufferedImage background = generator.getBackground();
        BufferedImage out = new BufferedImage(background.getWidth(),
                background.getHeight(), background.getType());
        out.getGraphics().drawImage(background, 0, 0, null, null);
        int x = background.getWidth();
        int y = background.getHeight();
        //taking a square from original

        int scaledX = Math.max(x * scale.intValue() / 100, 1);
        int scaledY = Math.max(y * scale.intValue() / 100, 1);
        int xPos = myRandom.nextInt(x - scaledX);
        int yPos = myRandom.nextInt(y - scaledY);
        BufferedImage clone = out.getSubimage(xPos, yPos, scaledX, scaledY);
        out.getGraphics().drawImage(deformation.deformImage(clone), xPos, yPos,
                Color.white, null);
        out.getGraphics().dispose();
        Point center = new Point(xPos + (scaledX / 2), yPos + (scaledY / 2));

        return new FishEye(CaptchaQuestionHelper.getQuestion(locale, BUNDLE_QUESTION_KEY),
                out, center, tolerance);
    }

}
