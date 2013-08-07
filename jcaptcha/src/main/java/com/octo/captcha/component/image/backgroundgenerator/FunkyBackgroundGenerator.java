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
 * Three color gradient background with randomization </p>
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue </a>
 * @version 1.0
 */
public class FunkyBackgroundGenerator extends AbstractBackgroundGenerator {
    ColorGenerator colorGeneratorLeftUp = null;

    ColorGenerator colorGeneratorLeftDown = null;

    ColorGenerator colorGeneratorRightUp = null;

    ColorGenerator colorGeneratorRightDown = null;

    float perturbationlevel = 0.1f;

    public FunkyBackgroundGenerator(Integer width, Integer height) {
        this(width, height, new SingleColorGenerator(Color.yellow), new SingleColorGenerator(Color.red), new SingleColorGenerator(Color.yellow), new SingleColorGenerator(Color.green), 0.5f);
    }

    public FunkyBackgroundGenerator(Integer width, Integer height, ColorGenerator colorGenerator) {
        this(width, height, colorGenerator, colorGenerator, colorGenerator, colorGenerator, 0.5f);
    }

    public FunkyBackgroundGenerator(Integer width, Integer height,
                                    ColorGenerator colorGeneratorLeftUp, ColorGenerator colorGeneratorLeftDown,
                                    ColorGenerator colorGeneratorRightUp, ColorGenerator colorGeneratorRightDown, float perturbationLevel) {
        super(width, height);
        this.colorGeneratorLeftUp = colorGeneratorLeftUp;
        this.colorGeneratorLeftDown = colorGeneratorLeftDown;
        this.colorGeneratorRightDown = colorGeneratorRightDown;
        this.colorGeneratorRightUp = colorGeneratorRightUp;
        this.perturbationlevel = perturbationLevel;
    }

    /**
     * Generates a backround image on wich text will be paste. Implementations must take into account the imageHeigt and
     * imageWidth.
     *
     * @return the background image
     */
    public BufferedImage getBackground() {
        Color colorLeftUp = colorGeneratorLeftUp.getNextColor();
        Color colorLeftDown = colorGeneratorLeftDown.getNextColor();
        Color colorRightUp = colorGeneratorRightUp.getNextColor();
        Color colorRightDown = colorGeneratorRightDown.getNextColor();

        BufferedImage bimgTP = new BufferedImage(getImageWidth(), getImageHeight(),
                BufferedImage.TYPE_INT_BGR);
        Graphics2D g2d = bimgTP.createGraphics();
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, getImageHeight(), getImageWidth());

        float height = getImageHeight();
        float width = getImageWidth();

        for (int j = 0; j < getImageHeight(); j++) {
            for (int i = 0; i < getImageWidth(); i++) {
                float leftUpRatio = (1 - i / width) * (1 - j / height);
                float leftDownRatio = (1 - i / width) * (j / height);
                float rightUpRatio = (i / width) * (1 - j / height);
                float rightDownRatio = (i / width) * (j / height);

                float red = colorLeftUp.getRed() / 255.0f * leftUpRatio + colorLeftDown.getRed() / 255.0f
                        * leftDownRatio + colorRightUp.getRed() / 255.0f * rightUpRatio
                        + colorRightDown.getRed() / 255.0f * rightDownRatio;

                float green = colorLeftUp.getGreen() / 255.0f * leftUpRatio + colorLeftDown.getGreen()
                        / 255.0f * leftDownRatio + colorRightUp.getGreen() / 255.0f * rightUpRatio
                        + colorRightDown.getGreen() / 255.0f * rightDownRatio;

                float blue = colorLeftUp.getBlue() / 255.0f * leftUpRatio + colorLeftDown.getBlue()
                        / 255.0f * leftDownRatio + colorRightUp.getBlue() / 255.0f * rightUpRatio
                        + colorRightDown.getBlue() / 255.0f * rightDownRatio;

                if (myRandom.nextFloat() > perturbationlevel)
                    g2d.setColor(new Color(red, green,
                            blue, 1.0f));
                else
                    g2d.setColor(new Color(compute(red), compute(green),
                            compute(blue), 1.0f));
                g2d.drawLine(i, j, i, j);
            }
        }
        g2d.dispose();
        return bimgTP;
    }

    private float compute(float f) {
        //we take the smallest range of variation
        float range = (1 - f < f) ? 1 - f : f;
        if (myRandom.nextFloat() > 0.5f)
            return f - myRandom.nextFloat() * range;
        else
            return f + myRandom.nextFloat() * range;
    }

}
