/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.component.image.backgroundgenerator;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.image.BufferedImage;

/**
 * <p>Black ellipses drawn on a white background</p>
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public class EllipseBackgroundGenerator extends AbstractBackgroundGenerator {

    public EllipseBackgroundGenerator(Integer width, Integer height) {
        super(width, height);
    }

    /**
     * Generates a backround image on wich text will be paste. Implementations must take into account the imageHeigt and
     * imageWidth.
     *
     * @return the background image
     */
    public BufferedImage getBackground() {
        BufferedImage bimgTP = new BufferedImage(getImageWidth(),
                getImageHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bimgTP.createGraphics();
        // g2d.setColor(Color.white);
        //g2d.fillRect(0, 0, getImageWidth(), getImageHeight());
        BasicStroke bs = new BasicStroke(2.0f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER, 2.0f, new float[]{2.0f, 2.0f}, 0.0f);
        g2d.setStroke(bs);
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                0.75f);
        g2d.setComposite(ac);

        g2d.translate(getImageWidth() * -1.0, 0.0);
        double delta = 5.0;
        double xt;
        double ts = 0.0;
        for (xt = 0.0; xt < (2.0 * getImageWidth()); xt += delta) {
            Arc2D arc = new Arc2D.Double(0, 0,
                    getImageWidth(), getImageHeight(), 0.0, 360.0, Arc2D.OPEN);
            g2d.draw(arc);
            g2d.translate(delta, 0.0);
            ts += delta;
        }
        return bimgTP;
    }
}
