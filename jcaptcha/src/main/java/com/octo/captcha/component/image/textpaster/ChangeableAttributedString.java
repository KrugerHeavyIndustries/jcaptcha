/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.component.image.textpaster;

import com.octo.captcha.CaptchaException;
import com.octo.captcha.component.image.color.ColorGenerator;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.font.TextAttribute;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.Random;

/**
 * This class is the decomposition of a single AttributedString into its component glyphs. It wouldn't be necessary if
 * Java2D correctly handled spacing issues with fonts changed AffineTransformation -- there is a possibility that it
 * will not be necessary with java 1.5
 */
public class ChangeableAttributedString {

    /**
     * each character is stored as its own AttributedString
     */
    AttributedString[] aStrings;

    /**
     * the boundaries are stored as placeholder for placement decisions
     */
    Rectangle2D[] bounds;

    /**
     * we need the line metrics primarily to get the maximum ascent for all characters.
     */
    LineMetrics[] metrics;

    /**
     * Comment for <code>myRandom</code>
     */
    private Random myRandom = new SecureRandom();

    /**
     * In typography, kerning refers to adjusting the space between characters, especially by placing two characters
     * closer together than normal. Kerning makes certain combinations of letters, such as WA, MW, TA, and VA, look
     * better.
     */
    private int kerning;

    /**
     * Given an attributed string and the graphics environment it lives in, pull it apart into its components.
     *
     * @param g2      graphics
     * @param aString attributed String
     */
    protected ChangeableAttributedString(Graphics2D g2, AttributedString aString, int kerning) {
        this.kerning = kerning;
        AttributedCharacterIterator iter = aString.getIterator();
        int n = iter.getEndIndex();
        aStrings = new AttributedString[n];
        bounds = new Rectangle2D[n];
        metrics = new LineMetrics[n];

        for (int i = iter.getBeginIndex(); i < iter.getEndIndex(); i++) {
            iter.setIndex(i);
            aStrings[i] = new AttributedString(iter, i, i + 1);
            Font font = (Font) iter.getAttribute(TextAttribute.FONT);
            if (font != null) {
                g2.setFont(font); // needed for getFont, -and- getFontRenderContext
            }
            final FontRenderContext frc = g2.getFontRenderContext();

            bounds[i] = g2.getFont().getStringBounds(iter, i, i + 1, frc);

            metrics[i] = g2.getFont().getLineMetrics((new Character(iter.current())).toString(),
                    frc);
        }

    }

    /**
     * Draw all characters according to their computed positions
     */
    void drawString(Graphics2D g2) {
        for (int i = 0; i < length(); i++) {
            g2.drawString(getIterator(i), (float) getX(i), (float) getY(i));
        }
    }

    /**
     * Draw all characters according to their computed positions, and a color from the colorGenerator
     *
     * @param colorGenerator generate color for each glyph
     */
    void drawString(Graphics2D g2, ColorGenerator colorGenerator) {
        for (int i = 0; i < length(); i++) {
            g2.setColor(colorGenerator.getNextColor());
            g2.drawString(getIterator(i), (float) getX(i), (float) getY(i));
        }
    }

    Point2D moveToRandomSpot(final BufferedImage background) {
        return moveToRandomSpot(background, null);
    }

    /**
     * Given a background image (for size only), pick a random spot such that the entire string can be displayed. This
     * method implicitly assumes that all resizing issues have been taken care of first. If you resize afterwards, any
     * type of clipping is possible.
     *
     * @param background    the image that will lie under the text
     * @param startingPoint the suggested starting point, or null if any point is acceptable.
     * @return a Point2D object indicating the initial starting point of the text
     * @throws com.octo.captcha.CaptchaException
     *          if the image size is too small, or the word too long, or the fonts too large.
     */
    Point2D moveToRandomSpot(final BufferedImage background, Point2D startingPoint) {
        int maxHeight = (int) getMaxHeight();

        // this padding is necessary due to flaws in this algorithm and how it interacts
        // with java. we are getting the logical bounds of the character, not the actual
        // bound of the character. So ascenders on rotated characters may extend out of the
        // box vertically and horizontally (for rotated letters), which means that we can
        // place the letter such that the final character, f, say, has its top outside the image.
        // the TextLayout class should be investigated more later; it didn't work well earlier.
        final int arbitraryHorizontalPadding = 10;
        final int arbitraryVerticalPadding = 5;
        double maxX = background.getWidth() - getTotalWidth() - arbitraryHorizontalPadding;
        double maxY = background.getHeight() - maxHeight - arbitraryVerticalPadding;

        int newY;

        if (startingPoint == null) {
            // we cannot start above the maximum ascent, or below the difference
            // between text size and image size. nextInt requires values > 0.
            // no suggested starting point is given - any spot on the vertical axis is ok
            newY = (int) getMaxAscent() + myRandom.nextInt(Math.max(1, (int) maxY));
        } else {
            newY = (int) (startingPoint.getY() + myRandom.nextInt(arbitraryVerticalPadding * 2));
        }

        // the bounding box we're using is too small. can we fix the problem?
        if (maxX < 0 || maxY < 0) {
            String problem = "too tall:"; // no, we cannot handle this case

            if (maxX < 0 && maxY > 0) {
                problem = "too long:";

                // ok, the text slammed into the end of the image. let's try half the kerning:
                useMinimumSpacing(kerning / 2);
                maxX = background.getWidth() - getTotalWidth();
                if (maxX < 0) {
                    // that didn't work. let's try no kerning
                    useMinimumSpacing(0);

                    maxX = background.getWidth() - getTotalWidth();
                    if (maxX < 0) {
                        // that didn't work either. let's try gradual steps of negative kerning.
                        maxX = reduceHorizontalSpacing(background.getWidth(), 0.05 /*
                                                                                                                    * max
                                                                                                                    * reduction
                                                                                                                    * pct
                                                                                                                    */);
                    }
                }

                // if one of the above steps worked, then return now;
                // otherwise, fall through to exception
                if (maxX > 0) {
                    moveTo(0, newY);
                    return new Point2D.Float(0, newY);
                }
            }

            // situtation is unrecoverable -- throw exception
            throw new CaptchaException("word is " + problem
                    + " try to use less letters, smaller font" + " or bigger background: "
                    + " text bounds = " + this + " with fonts " + this.getFontListing()
                    + " versus image width = " + background.getWidth() + ", height = "
                    + background.getHeight());
        }

        int newX;
        if (startingPoint == null) {
            // no suggested starting point - the string can start anywhere horizontal if
            // the string is long enough
            newX = myRandom.nextInt(Math.max(1, (int) maxX));
        } else {
            newX = (int) (startingPoint.getX() + myRandom.nextInt(arbitraryHorizontalPadding));
        }

        moveTo(newX, newY);
        return new Point2D.Float(newX, newY);
    }

    /**
     * helper method for error message
     *
     * @return list of fonts
     */
    String getFontListing() {
        StringBuffer buf = new StringBuffer();
        final String RS = "\n\t";
        buf.append("{");
        for (int i = 0; i < length(); i++) {
            AttributedCharacterIterator iter = aStrings[i].getIterator();
            Font font = (Font) iter.getAttribute(TextAttribute.FONT);
            if (font != null) {
                buf.append(font.toString()).append(RS);
            }
        }
        buf.append("}");
        return buf.toString();
    }

    /**
     * Rearrange the string so that all characters are treated as if they are as wide as the widest character in the
     * same string.
     *
     * @param kerning the space between the characters
     */
    void useMonospacing(double kerning) {
        double maxWidth = getMaxWidth();
        // for every glyph after the first, space it out so that they are maxWidth characters apart
        for (int i = 1; i < bounds.length; i++) {
            // each character between where the previous character ends
            getBounds(i).setRect(getX(i - 1) + maxWidth + kerning, getY(i), getWidth(i),
                    getHeight(i));
        }
    }

    /**
     * Rearrange the string so that all characters are treated as if they are as wide as the widest character in the
     * same string.
     *
     * @param kerning the space between the characters
     */
    void useMinimumSpacing(double kerning) {

        for (int i = 1; i < length(); i++) {
            bounds[i].setRect(bounds[i - 1].getX() + bounds[i - 1].getWidth() + kerning, bounds[i]
                    .getY(), bounds[i].getWidth(), bounds[i].getHeight());
        }
    }

    /**
     * Gradually reduce spacing between letters until the total length is less than the final image width. In many
     * cases, this will guarantee collisions between the letters.
     *
     * @param maxReductionPct maximum percentage reduction
     * @return if positive, the highest X value that can be safely used for placement of box; if negative, there is no
     *         safe way to display the text without clipping the ends.
     */
    double reduceHorizontalSpacing(int imageWidth, double maxReductionPct) {
        double maxX = imageWidth - getTotalWidth();

        double pct = 0;
        final double stepSize = maxReductionPct / 25;
        for (pct = stepSize; pct < maxReductionPct && maxX < 0; pct += stepSize) {
            for (int i = 1; i < length(); i++) {
                bounds[i].setRect((1 - pct) * bounds[i].getX(), bounds[i].getY(), bounds[i]
                        .getWidth(), bounds[i].getHeight());
            }
            maxX = (imageWidth - getTotalWidth());
        }
        return maxX;
    }

    /**
     * Change the x,y values in the boundaries so they can be used for position.
     */
    void moveTo(double newX, double newY) {
        bounds[0].setRect(newX, newY, bounds[0].getWidth(), bounds[0].getHeight());
        for (int i = 1; i < length(); i++) {
            bounds[i].setRect(newX + bounds[i].getX(), newY, bounds[i].getWidth(), bounds[i]
                    .getHeight());
        }
    }

    /*
     * shift string to a non-linear layout in the output image
     */
    protected void shiftBoundariesToNonLinearLayout(double backgroundWidth, double backgroundHeight) {
        double newX = backgroundWidth / 20;
        double middleY = backgroundHeight / 2;
        Random myRandom = new SecureRandom();

        bounds[0].setRect(newX, middleY, bounds[0].getWidth(), bounds[0].getHeight());
        for (int i = 1; i < length(); i++)
        {
            double characterHeight = bounds[i].getHeight();
            double randomY = myRandom.nextInt() % (backgroundHeight / 4);
            double currentY = middleY + ((myRandom.nextBoolean()) ? randomY : -randomY) + (characterHeight / 4);
            bounds[i].setRect(newX + bounds[i].getX(), currentY, bounds[i].getWidth(), bounds[i].getHeight());
        }
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("{text=");
        for (int i = 0; i < length(); i++) {
            buf.append(aStrings[i].getIterator().current());
        }
        final String RS = "\n\t";
        buf.append(RS);
        for (int i = 0; i < length(); i++) {
            buf.append(bounds[i].toString());
            final String FS = " ";
            final LineMetrics m = metrics[i];
            // height = ascent + descent + leading
            buf.append(" ascent=").append(m.getAscent()).append(FS);
            buf.append("descent=").append(m.getDescent()).append(FS);
            buf.append("leading=").append(m.getLeading()).append(FS);

            buf.append(RS);
        }
        buf.append("}");
        return buf.toString();
    }

    public int length() {
        return bounds.length;
    }

    public double getX(int index) {
        return getBounds(index).getX();
    }

    public double getY(int index) {
        return getBounds(index).getY();
    }

    public double getHeight(int index) {
        return getBounds(index).getHeight();
    }

    public double getTotalWidth() {
        return getX(length() - 1) + getWidth(length() - 1);
    }

    public double getWidth(int index) {
        return getBounds(index).getWidth();
    }

    public double getAscent(int index) {
        return getMetric(index).getAscent();
    }

    double getDescent(int index) {
        return getMetric(index).getDescent();
    }

    public double getMaxWidth() {
        double maxWidth = -1;

        for (int i = 0; i < bounds.length; i++) {
            final double w = getWidth(i);

            if (maxWidth < w) {
                maxWidth = w;
            }
        }
        return maxWidth;
    }

    public double getMaxAscent() {
        double maxAscent = -1;

        for (int i = 0; i < bounds.length; i++) {
            final double a = getAscent(i);

            if (maxAscent < a) {
                maxAscent = a;
            }
        }
        return maxAscent;
    }

    public double getMaxDescent() {
        double maxDescent = -1;

        for (int i = 0; i < bounds.length; i++) {
            final double d = getDescent(i);

            if (maxDescent < d) {
                maxDescent = d;
            }
        }
        return maxDescent;
    }

    public double getMaxHeight() {
        double maxHeight = -1;
        for (int i = 0; i < bounds.length; i++) {
            double h = getHeight(i);

            if (maxHeight < h) {
                maxHeight = h;
            }
        }
        return maxHeight;
    }

    public double getMaxX() {
        return getX(0) + getTotalWidth();
    }

    public double getMaxY() {
        return getY(0) + getMaxHeight();
    }

    public Rectangle2D getBounds(int index) {
        return bounds[index];
    }

    public LineMetrics getMetric(int index) {
        return metrics[index];
    }

    public AttributedCharacterIterator getIterator(int i) {
        return aStrings[i].getIterator();
    }

}
