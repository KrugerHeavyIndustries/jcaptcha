/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.component.image.textpaster;

import com.octo.captcha.CaptchaException;
import com.octo.captcha.component.image.color.ColorGenerator;
import com.octo.captcha.component.image.color.SingleColorGenerator;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.text.AttributedString;

/**
 * <p/>
 * text paster that paint white lines on the string </p> You may specify the number of line per glyph : 3 by default.
 * You may specify the color of lines : TextColor by default.
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue </a>
 * @version 1.0
 * @see {http://www.parc.xerox.com/research/istl/projects/captcha/default.html}
 * @deprecated use the {@link DecoratedRandomTextPaster} instead
 */
public class LineRandomTextPaster extends RandomTextPaster {

    /**
     * Number of line that will be drawn around each glyph
     */
    private Integer numberOfLinesPerGlyph = new Integer(3);

    /**
     * The color generator for the line
     */
    private ColorGenerator linesColorGenerator = null;

    /**
     * @param minAcceptedWordLength Max length of a word
     * @param maxAcceptedWordLength Min length of a word
     * @param textColor             Unique color of text
     * @param numberOfLinesPerGlyph Number of lines around glyphes
     * @param linesColor            Color of the lines
     */
    public LineRandomTextPaster(Integer minAcceptedWordLength, Integer maxAcceptedWordLength,
                                Color textColor, Integer numberOfLinesPerGlyph, Color linesColor) {
        this(minAcceptedWordLength, maxAcceptedWordLength,
                textColor != null ? new SingleColorGenerator(textColor) : null, Boolean.FALSE,
                numberOfLinesPerGlyph, new SingleColorGenerator(linesColor != null ? linesColor
                : textColor));

    }

    /**
     * @param minAcceptedWordLength Max length of a word
     * @param maxAcceptedWordLength Min length of a word
     * @param colorGenerator        Color generatior for the text
     * @param numberOfLinesPerGlyph Number of lines around glyphes
     * @param linesColor            The color for the lines (one color for all lines)
     */
    public LineRandomTextPaster(Integer minAcceptedWordLength, Integer maxAcceptedWordLength,
                                ColorGenerator colorGenerator, Integer numberOfLinesPerGlyph, Color linesColor) {
        this(minAcceptedWordLength, maxAcceptedWordLength, colorGenerator, Boolean.FALSE,
                numberOfLinesPerGlyph, new SingleColorGenerator(linesColor != null ? linesColor
                : colorGenerator.getNextColor()));
    }

    /**
     * @param minAcceptedWordLength Max length of a word
     * @param maxAcceptedWordLength Min length of a word
     * @param colorGenerator        Color generatior for the text
     * @param manageColorPerGlyph   Boolean to set if each glyph can have a new color from the color generator
     * @param numberOfLinesPerGlyph Number of lines around glyphes
     * @param linesColor            The color genator for the lines (one color for all lines)
     */
    public LineRandomTextPaster(Integer minAcceptedWordLength, Integer maxAcceptedWordLength,
                                ColorGenerator colorGenerator, Boolean manageColorPerGlyph, Integer numberOfLinesPerGlyph,
                                Color linesColor) {
        this(minAcceptedWordLength, maxAcceptedWordLength, colorGenerator, manageColorPerGlyph,
                numberOfLinesPerGlyph, new SingleColorGenerator(linesColor != null ? linesColor
                : colorGenerator.getNextColor()));
    }

    /**
     * @param minAcceptedWordLength Max length of a word
     * @param maxAcceptedWordLength Min length of a word
     * @param colorGenerator        Color generatior for the text
     * @param manageColorPerGlyph   Boolean to set if each glyph can have a new color from the color generator
     * @param numberOfLinesPerGlyph Number of lines around the glyph
     * @param linesColorGenerator   The color genator for the lines (one color for all lines)
     */
    public LineRandomTextPaster(Integer minAcceptedWordLength, Integer maxAcceptedWordLength,
                                ColorGenerator colorGenerator, Boolean manageColorPerGlyph, Integer numberOfLinesPerGlyph,
                                ColorGenerator linesColorGenerator) {
        super(minAcceptedWordLength, maxAcceptedWordLength, colorGenerator, manageColorPerGlyph);
        this.numberOfLinesPerGlyph = numberOfLinesPerGlyph != null ? numberOfLinesPerGlyph
                : this.numberOfLinesPerGlyph;
        this.linesColorGenerator = linesColorGenerator != null ? linesColorGenerator
                : colorGenerator;
    }

    /**
     * Pastes the attributed string on the backround image and return the final image. Implementation must take into
     * account the fact that the text must be readable by human and non by programs
     *
     * @return the final image
     *
     * @throws CaptchaException if any exception accurs during paste routine.
     */
    public BufferedImage pasteText(BufferedImage background, AttributedString attributedWord)
            throws CaptchaException {
        BufferedImage out = copyBackground(background);
        Graphics2D g2 = pasteBackgroundAndSetTextColor(out, background);
        g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
                RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        // this attribute doesn't do anything in JDK 1.4, but maybe it will in JDK 1.5
        // attributedString.addAttribute(TextAttribute.WIDTH, TextAttribute.WIDTH_EXTENDED);

        // convert string into a series of glyphs we can work with
        ChangeableAttributedString newAttrString = new ChangeableAttributedString(g2,
                attributedWord, kerning);

        // space out the glyphs with a little kerning
        newAttrString.useMinimumSpacing(kerning);
        // shift string to a random spot in the output imge
        newAttrString.moveToRandomSpot(background);
        // now draw each glyph at the appropriate spot on th eimage.
        if (isManageColorPerGlyph())
            newAttrString.drawString(g2, getColorGenerator());
        else
            newAttrString.drawString(g2);

        g2.setColor(linesColorGenerator.getNextColor());

        for (int j = 0; j < attributedWord.getIterator().getEndIndex(); j++) {
            Rectangle2D bounds = newAttrString.getBounds(j).getFrame();
            for (int i = 0; i < numberOfLinesPerGlyph.intValue(); i++) {
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

        g2.dispose();
        return out;
    }
}
