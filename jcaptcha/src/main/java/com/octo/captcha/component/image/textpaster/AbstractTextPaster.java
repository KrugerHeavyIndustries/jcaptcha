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
import java.awt.image.BufferedImage;
import java.security.SecureRandom;
import java.util.Random;

/**
 * <p/>
 * Base class for Test pasters. Sub classes must implement the pasteText(BufferedImage background, AttributedString
 * attributedWord) method that return an image containing the pasted string.</br> use constructor to specify your paster
 * properties. This base class use two Integers, maxAcceptedWordLength and minAcceptedWordLength by wich are the length
 * boundaries for the implementation. By default minAcceptedWordLength = 6 and maxAcceptedWordLength = 20 </p>
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue </a>
 * @version 1.0
 */
public abstract class AbstractTextPaster implements TextPaster {
    /**
     * Comment for <code>myRandom</code>
     */
    public Random myRandom = new SecureRandom();

    /**
     * Max length of a word
     */
    private int max = 20;

    /**
     * Min length of a word
     */
    private int min = 6;

    /**
     * ColorGenerator for the text paster
     */
    private ColorGenerator colorGenerator = new SingleColorGenerator(Color.black);

    /**
     * If false (default) color is set for the whole test, otherwise each glyph can have its owne color
     */
    private boolean manageColorPerGlyph = false;

    /**
     * Default constructor with just min and max length of a word
     *
     * @param minAcceptedWordLength Max length of a word
     * @param maxAcceptedWordLength Min length of a word
     */
    AbstractTextPaster(Integer minAcceptedWordLength, Integer maxAcceptedWordLength) {
        this.max = maxAcceptedWordLength != null ? maxAcceptedWordLength.intValue() : this.max;
        this.min = minAcceptedWordLength != null && minAcceptedWordLength.intValue() <= this.max ? minAcceptedWordLength
                .intValue()
                : Math.min(this.min, this.max - 1);
    }

    /**
     * Default constructor with unique color of text
     *
     * @param minAcceptedWordLength Max length of a word
     * @param maxAcceptedWordLength Min length of a word
     * @param textColor             Unique color of text
     */
    AbstractTextPaster(Integer minAcceptedWordLength, Integer maxAcceptedWordLength, Color textColor) {
        this(minAcceptedWordLength, maxAcceptedWordLength);

        if (textColor != null) {
            this.colorGenerator = new SingleColorGenerator(textColor);
        }
    }

    /**
     * Default Constructor with a color generator for the text
     */
    AbstractTextPaster(Integer minAcceptedWordLength, Integer maxAcceptedWordLength,
                       ColorGenerator colorGenerator) {
        this(minAcceptedWordLength, maxAcceptedWordLength);
        if (colorGenerator == null) {
            throw new CaptchaException("ColorGenerator is null");
        }
        this.colorGenerator = colorGenerator;
    }

    /**
     * Default Constructor with a color generator for the text, and color is managed per glyph, each glyph can have a
     * new color
     *
     * @param manageColorPerGlyph Boolean to set if each glyph can have a new color from the color generator
     */
    AbstractTextPaster(Integer minAcceptedWordLength, Integer maxAcceptedWordLength,
                       ColorGenerator colorGenerator, Boolean manageColorPerGlyph) {
        this(minAcceptedWordLength, maxAcceptedWordLength, colorGenerator);
        this.manageColorPerGlyph = manageColorPerGlyph != null ? manageColorPerGlyph.booleanValue() : this.manageColorPerGlyph;
    }

    /**
     * @return the max word length accepted by this word2image service
     * @deprecated misspelled, use {@link #getMaxAcceptedWordLength()} instead
     */
    public int getMaxAcceptedWordLenght() {
        return max;
    }

    /**
     * @return the min word length accepted by this word2image service
     * @deprecated misspelled, use {@link #getMinAcceptedWordLength()} instead
     */
    public int getMinAcceptedWordLenght() {
        return min;
    }

    /**
     * @return the max word length accepted by this word2image service
     */
    public int getMaxAcceptedWordLength() {
        return max;
    }

    /**
     * @return the min word length accepted by this word2image service
     */
    public int getMinAcceptedWordLength() {
        return min;
    }


    /**
     * @return the color generator
     */
    protected ColorGenerator getColorGenerator() {
        return colorGenerator;
    }

    /**
     * @return the copy of the background
     */
    BufferedImage copyBackground(final BufferedImage background) {
        BufferedImage out = new BufferedImage(background.getWidth(), background.getHeight(),
                background.getType());
        return out;
    }

    /**
     * @param out
     * @param background
     * @return a graphic2D
     */
    Graphics2D pasteBackgroundAndSetTextColor(BufferedImage out, final BufferedImage background) {
        Graphics2D pie = (Graphics2D) out.getGraphics();
        //paste background
        pie.drawImage(background, 0, 0, out.getWidth(), out.getHeight(), null);
        //pie.setColor(getTextColor());
        pie.setColor(colorGenerator.getNextColor());
        return pie;
    }

    /**
     * @return true if this component manage color per glyph
     */
    public boolean isManageColorPerGlyph() {
        return manageColorPerGlyph;
    }

    /**
     * @param colorGenerator
     */
    public void setColorGenerator(ColorGenerator colorGenerator) {
        this.colorGenerator = colorGenerator;
    }
}
