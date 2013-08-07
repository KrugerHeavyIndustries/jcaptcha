/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.component.image.backgroundgenerator;

import com.octo.captcha.component.image.color.ColorGenerator;
import com.octo.captcha.component.image.color.SingleColorGenerator;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * <p/>
 * Draw mutliple different shape with different colors. see attributes to construct it in a proper way. </p>
 *
 * @author <a href="mailto:mga@octo.com">Mathieu Gandin </a>
 * @version 1.0
 */
public class MultipleShapeBackgroundGenerator extends
        AbstractBackgroundGenerator {

    public MultipleShapeBackgroundGenerator(Integer width, Integer height) {
        super(width, height);
    }

    /**
     * Default value for the first color (black) of the gradient paint of ellipses.
     */
    private ColorGenerator firstEllipseColorGenerator = new SingleColorGenerator(new Color(210, 210, 210));

    /**
     * Default value for the first color (White) of the gradient paint of ellipses.
     */
    private ColorGenerator secondEllipseColorGenerator = new SingleColorGenerator(new Color(0, 0, 0));

    /**
     * Default value for the first color (black) of the gradient paint of rectangles.
     */
    private ColorGenerator firstRectangleColorGenerator = new SingleColorGenerator(new Color(210, 210, 210));

    /**
     * Default value for the first color (White) of the gradient paint of rectangles.
     */
    private ColorGenerator secondRectangleColorGenerator = new SingleColorGenerator(new Color(0, 0, 0));

    /**
     * Default space between lines: 10 pixels.
     */
    private Integer spaceBetweenLine = new Integer(10);

    /**
     * Default space between circles: 10 pixels.
     */
    private Integer spaceBetweenCircle = new Integer(10);

    /**
     * Default height for the ellipse: 8 pixels.
     */
    private Integer ellipseHeight = new Integer(8);

    /**
     * Default width for the ellipse: 8 pixels.
     */
    private Integer ellipseWidth = new Integer(8);

    /**
     * Default width for the rectangle: 3 pixels.
     */
    private Integer rectangleWidth = new Integer(3);

    public MultipleShapeBackgroundGenerator(Integer width, Integer height,
                                            Color firstEllipseColor, Color secondEllipseColor,
                                            Integer spaceBetweenLine, Integer spaceBetweenCircle,
                                            Integer ellipseHeight, Integer ellipseWidth,
                                            Color firstRectangleColor, Color secondRectangleColor,
                                            Integer rectangleWidth) {

        super(width, height);

        if (firstEllipseColor != null)
            this.firstEllipseColorGenerator = new SingleColorGenerator(firstEllipseColor);
        if (secondEllipseColor != null)
            this.secondEllipseColorGenerator = new SingleColorGenerator(secondEllipseColor);
        if (spaceBetweenLine != null)
            this.spaceBetweenLine = spaceBetweenCircle;
        if (spaceBetweenCircle != null)
            this.spaceBetweenCircle = spaceBetweenCircle;
        if (ellipseHeight != null)
            this.ellipseHeight = ellipseHeight;
        if (ellipseWidth != null)
            this.ellipseWidth = ellipseWidth;
        if (firstRectangleColor != null)
            this.firstRectangleColorGenerator = new SingleColorGenerator(firstRectangleColor);
        if (secondRectangleColor != null)
            this.secondRectangleColorGenerator = new SingleColorGenerator(secondRectangleColor);
        if (rectangleWidth != null)
            this.rectangleWidth = rectangleWidth;
    }


    /**
     * Main method. It generates a background of the captcha with a large number of lines, ellipse, and gradient paint.
     *
     * @return the background full of shapes
     */
    public BufferedImage getBackground() {
        BufferedImage bi = new BufferedImage(getImageWidth(), getImageHeight(),
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) bi.getGraphics();
        g2.setBackground(Color.white);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        for (int variableOnWidth = 0; variableOnWidth < getImageWidth(); variableOnWidth = variableOnWidth + this.getSpaceBetweenLine())
        {
            Color firstEllipseColor = this.firstEllipseColorGenerator
                    .getNextColor();
            Color secondEllipseColor = this.secondEllipseColorGenerator
                    .getNextColor();
            Color firstRectangleColor = this.firstRectangleColorGenerator
                    .getNextColor();
            Color secondRectangleColor = this.secondRectangleColorGenerator
                    .getNextColor();
            for (int variableOnHeight = 0; variableOnHeight < getImageHeight(); variableOnHeight = variableOnHeight
                    + this.getSpaceBetweenCircle()) {
                Ellipse2D e2 = new Ellipse2D.Double(variableOnWidth, variableOnHeight, this
                        .getEllipseWidth(), this.getEllipseHeight());
                GradientPaint gp = new GradientPaint(0,
                        this.getEllipseHeight(), firstEllipseColor, this
                        .getEllipseWidth(), 0, secondEllipseColor, true);

                g2.setPaint(gp);
                g2.fill(e2);
            }
            GradientPaint gp2 = new GradientPaint(0, getImageHeight(),
                    firstRectangleColor, this.getRectangleWidth(), 0,
                    secondRectangleColor, true);
            g2.setPaint(gp2);
            Rectangle2D r2 = new Rectangle2D.Double(variableOnWidth, 0, this
                    .getRectangleWidth(), getImageHeight());
            g2.fill(r2);
        }
        g2.dispose();
        return bi;
    }

    /**
     * Helper method to get the int value of the number of pixels between lines.
     *
     * @return number of pixels between lines.
     */
    protected int getSpaceBetweenLine() {
        return this.spaceBetweenLine.intValue();
    }

    /**
     * Helper method to get the int value of the number of pixels between circles.
     *
     * @return number of pixels between circles.
     */
    protected int getSpaceBetweenCircle() {
        return this.spaceBetweenCircle.intValue();
    }

    /**
     * Helper method to get the height of drawn ellipses.
     *
     * @return height of ellipses.
     */
    protected int getEllipseHeight() {
        return this.ellipseHeight.intValue();
    }

    /**
     * Helper method to get the width of drawn ellipses.
     *
     * @return width of ellipses.
     */
    protected int getEllipseWidth() {
        return this.ellipseWidth.intValue();
    }

    /**
     * Helper method to get the width of drawn rectangles.
     *
     * @return width of rectangles.
     */
    protected int getRectangleWidth() {
        return this.rectangleWidth.intValue();
    }
}