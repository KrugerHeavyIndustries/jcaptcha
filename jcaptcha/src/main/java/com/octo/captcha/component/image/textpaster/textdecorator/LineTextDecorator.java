/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.component.image.textpaster.textdecorator;

import com.octo.captcha.component.image.color.ColorGenerator;
import com.octo.captcha.component.image.color.SingleColorGenerator;
import com.octo.captcha.component.image.textpaster.ChangeableAttributedString;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;
import java.security.SecureRandom;
import java.text.AttributedString;
import java.util.Random;

/**
 * <p/>
 * text paster that paint  lines on the string </p> You may specify the number of line per glyph : 3 by default. You may
 * specify the color of lines : white by default.
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue </a>
 * @version 1.0
 * @see {http://www.parc.xerox.com/research/istl/projects/captcha/default.html}
 */
public class LineTextDecorator implements TextDecorator {

    private Random myRandom = new SecureRandom();

    /**
     * Number of holes per glyph. Default : 3
     */
    private Integer numberOfLinesPerGlyph = new Integer(3);

    /**
     * ColorGenerator for the holes
     */
    private ColorGenerator linesColorGenerator = null;

    private int alphaCompositeType = AlphaComposite.SRC_OVER;


    /**
     * @param linesColor Color of the lines
     */
    public LineTextDecorator(Integer numberOfLinesPerGlyph, Color linesColor) {
        this.numberOfLinesPerGlyph = numberOfLinesPerGlyph != null ? numberOfLinesPerGlyph
                : this.numberOfLinesPerGlyph;
        this.linesColorGenerator = new SingleColorGenerator(linesColor != null ? linesColor
                : Color.white);
    }

    /**
     * @param numberOfLinesPerGlyph Number of lines around glyphes
     * @param linesColorGenerator   The colors for the lines
     */
    public LineTextDecorator(Integer numberOfLinesPerGlyph, ColorGenerator linesColorGenerator) {
        this.numberOfLinesPerGlyph = numberOfLinesPerGlyph != null ? numberOfLinesPerGlyph
                : this.numberOfLinesPerGlyph;
        this.linesColorGenerator = linesColorGenerator != null ? linesColorGenerator
                : new SingleColorGenerator(Color.white);
    }


    /**
     * @param numberOfLinesPerGlyph Number of lines around glyphes
     * @param linesColorGenerator   The colors for the lines
     */
    public LineTextDecorator(Integer numberOfLinesPerGlyph, ColorGenerator linesColorGenerator, Integer alphaCompositeType) {
        this(numberOfLinesPerGlyph, linesColorGenerator);
        this.alphaCompositeType = alphaCompositeType != null ? alphaCompositeType.intValue() : this.alphaCompositeType;
    }

    public void decorateAttributedString(Graphics2D g2, AttributedString attributedWord, ChangeableAttributedString newAttrString) {
        Color oldColor = g2.getColor();
        Composite oldComp = g2.getComposite();
        g2.setComposite(AlphaComposite.getInstance(alphaCompositeType));
        for (int j = 0; j < attributedWord.getIterator().getEndIndex(); j++) {
            g2.setColor(linesColorGenerator.getNextColor());
            Rectangle2D bounds = newAttrString.getBounds(j).getFrame();
            for (int i = 0; i < numberOfLinesPerGlyph.intValue(); i++) {
                // double circleSize = circleMaxSize * (1 + myRandom.nextDouble()) / 2;
                double circlex = bounds.getMinX() + bounds.getWidth() * 0.7 * myRandom.nextDouble();
                double circley = bounds.getMinY() - bounds.getHeight() * 0.5
                        * myRandom.nextDouble();
                //width
                double width = 5 + myRandom.nextInt(25);
                //length
                double length = 5 + myRandom.nextInt(25);
                //get an angle between 0 and PI
                double angle = Math.PI * myRandom.nextDouble();
                //rotation and translation where the character is
                AffineTransform transformation = new AffineTransform(Math.cos(angle), -Math
                        .sin(angle), Math.sin(angle), Math.cos(angle), circlex, circley);

                QuadCurve2D q = new QuadCurve2D.Double();
                // start poitn , control point, finhsi point
                q.setCurve(0, 0, (length / 2.0) + 15.0 * myRandom.nextDouble()
                        * (myRandom.nextBoolean() ? -1 : 1), (width / 2.0) + 15.0
                        * myRandom.nextDouble() * (myRandom.nextBoolean() ? -1 : 1), length, width);
                g2.setStroke(new BasicStroke(2 + myRandom.nextInt(4)));
                g2.draw(transformation.createTransformedShape(q));
            }
        }
        g2.setComposite(oldComp);
        g2.setColor(oldColor);
    }
}

