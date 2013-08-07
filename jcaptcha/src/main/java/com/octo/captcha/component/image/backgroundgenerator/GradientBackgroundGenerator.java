/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.component.image.backgroundgenerator;

import com.octo.captcha.CaptchaException;
import com.octo.captcha.component.image.color.ColorGenerator;
import com.octo.captcha.component.image.color.SingleColorGenerator;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * <p/>
 * Gradient background. Use the constructor to specify colors. Default color are Black and Gray </p>
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue </a>
 * @version 1.0
 */
public class GradientBackgroundGenerator extends AbstractBackgroundGenerator {

    ColorGenerator firstColor = null;

    ColorGenerator secondColor = null;

    public GradientBackgroundGenerator(Integer width, Integer height, Color firstColor,
                                       Color secondColor) {
        super(width, height);
        if (firstColor == null || secondColor == null) {
            throw new CaptchaException("Color is null");
        }
        this.firstColor = new SingleColorGenerator(firstColor);
        this.secondColor = new SingleColorGenerator(secondColor);

    }

    public GradientBackgroundGenerator(Integer width, Integer height, ColorGenerator firstColorGenerator,
                                       ColorGenerator secondColorGenerator) {
        super(width, height);
        if (firstColorGenerator == null || secondColorGenerator == null) {
            throw new CaptchaException("ColorGenerator is null");
        }
        this.firstColor = firstColorGenerator;
        this.secondColor = secondColorGenerator;
    }

    /**
     * Generates a backround image on wich text will be paste. Implementations must take into account the imageHeigt and
     * imageWidth.
     *
     * @return the background image
     */
    public BufferedImage getBackground() {
        BufferedImage bi = new BufferedImage(getImageWidth(), getImageHeight(),
                BufferedImage.TYPE_INT_RGB);
        Graphics2D pie = (Graphics2D) bi.getGraphics();
        pie.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gp = new GradientPaint(0, getImageHeight(), firstColor.getNextColor(), getImageWidth(), 0,
                secondColor.getNextColor());
        pie.setPaint(gp);
        pie.fillRect(0, 0, getImageWidth(), getImageHeight());
        pie.dispose();
        return bi;
    }
}
