/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.component.image.backgroundgenerator;

import com.octo.captcha.component.image.color.ColorGenerator;
import com.octo.captcha.component.image.color.SingleColorGenerator;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * <p/>
 * Contructs uniform painted background, with default wolor White. </p>
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue </a>
 * @version 1.0
 */
public class UniColorBackgroundGenerator extends AbstractBackgroundGenerator {

    private BufferedImage backround;

    private ColorGenerator colorGenerator = null;

    public UniColorBackgroundGenerator(Integer width, Integer height) {
        this(width, height, Color.white);
    }

    public UniColorBackgroundGenerator(Integer width, Integer height, Color color) {
        super(width, height);
        this.colorGenerator = new SingleColorGenerator(color);
    }

    public UniColorBackgroundGenerator(Integer width, Integer height, ColorGenerator colorGenerator) {
        super(width, height);
        this.colorGenerator = colorGenerator;
    }

    /**
     * Generates a backround image on wich text will be paste. Implementations must take into account the imageHeigt and
     * imageWidth.
     *
     * @return the background image
     */
    public BufferedImage getBackground() {
        backround = new BufferedImage(getImageWidth(), getImageHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D pie = (Graphics2D) backround.getGraphics();
        Color color = colorGenerator.getNextColor();

        pie.setColor(color != null ? color : Color.white);
        pie.setBackground(color != null ? color : Color.white);
        pie.fillRect(0, 0, getImageWidth(), getImageHeight());
        pie.dispose();
        return backround;
    }
}
