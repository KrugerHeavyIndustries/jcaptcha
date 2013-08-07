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
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.security.SecureRandom;
import java.text.AttributedString;
import java.util.Random;

/**
 * <p/>
 * text decorator that paint holes on the string (erase some parts) </p> You may specify the number of holes per glyph :
 * 3 by default. You may specify the color of holes : white by default.
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue </a>
 * @version 1.0
 * @see {http://www.parc.xerox.com/research/istl/projects/captcha/default.html}
 */
public class BaffleTextDecorator implements TextDecorator {

    private Random myRandom = new SecureRandom();

    /**
     * circleXRatio
     */
    private static double circleXRatio = 0.7;

    /**
     * circleYRatio
     */
    private static double circleYRatio = 0.5;

    /**
     * Number of holes per glyph. Default : 3
     */
    private Integer numberOfHolesPerGlyph = new Integer(3);

    /**
     * ColorGenerator for the holes
     */
    private ColorGenerator holesColorGenerator = null;

    private int alphaCompositeType = AlphaComposite.SRC_OVER;


    /**
     * @param holesColor Color of the holes
     */
    public BaffleTextDecorator(Integer numberOfHolesPerGlyph, Color holesColor) {
        this.numberOfHolesPerGlyph = numberOfHolesPerGlyph != null ? numberOfHolesPerGlyph
                : this.numberOfHolesPerGlyph;
        this.holesColorGenerator = new SingleColorGenerator(holesColor != null ? holesColor
                : Color.white);
    }

    /**
     * @param numberOfHolesPerGlyph Number of holes around glyphes
     * @param holesColorGenerator   The colors for holes
     */
    public BaffleTextDecorator(Integer numberOfHolesPerGlyph, ColorGenerator holesColorGenerator) {

        this.numberOfHolesPerGlyph = numberOfHolesPerGlyph != null ? numberOfHolesPerGlyph
                : this.numberOfHolesPerGlyph;
        this.holesColorGenerator = holesColorGenerator != null ? holesColorGenerator
                : new SingleColorGenerator(Color.white);
    }


    /**
     * @param numberOfHolesPerGlyph Number of holes around glyphes
     * @param holesColorGenerator   The colors for holes
     */
    public BaffleTextDecorator(Integer numberOfHolesPerGlyph, ColorGenerator holesColorGenerator, Integer alphaCompositeType) {
        this(numberOfHolesPerGlyph, holesColorGenerator);
        this.alphaCompositeType = alphaCompositeType != null ? alphaCompositeType.intValue() : this.alphaCompositeType;
    }


    public void decorateAttributedString(Graphics2D g2, AttributedString attributedWord, ChangeableAttributedString newAttrString) {

        Color oldColor = g2.getColor();
        Composite oldComp = g2.getComposite();
        g2.setComposite(AlphaComposite.getInstance(alphaCompositeType));

        for (int j = 0; j < attributedWord.getIterator().getEndIndex(); j++) {
            g2.setColor(holesColorGenerator.getNextColor());

            Rectangle2D bounds = newAttrString.getBounds(j).getFrame();
            double circleMaxSize = (double) bounds.getWidth() / 2;
            for (int i = 0; i < numberOfHolesPerGlyph.intValue(); i++) {
                double circleSize = circleMaxSize * (1 + myRandom.nextDouble()) / 2;
                double circlex = bounds.getMinX() + bounds.getWidth() * circleXRatio
                        * myRandom.nextDouble();
                double circley = bounds.getMinY() - bounds.getHeight() * circleYRatio
                        * myRandom.nextDouble();
                Ellipse2D circle = new Ellipse2D.Double(circlex, circley, circleSize, circleSize);
                g2.fill(circle);
            }
        }
        g2.setColor(oldColor);
        g2.setComposite(oldComp);
    }
}

