/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */
package com.octo.captcha.component.image.textpaster;

import com.octo.captcha.CaptchaException;
import com.octo.captcha.component.image.color.ColorGenerator;

import java.awt.*;
import java.awt.font.TextAttribute;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.HashMap;
import java.util.Map;

/**
 * A <code>TextPaster</code> that pasts the characters on the background image, turned around a random angle from the
 * center of the character and past at a random y position.
 *
 * @author Martijn van Groningen
 * @since Nov 11, 2007
 */
public class NonLinearRandomAngleTextPaster extends AbstractTextPaster {

    private Map renderingHints = new HashMap();

    public NonLinearRandomAngleTextPaster(Integer minAcceptedWordLength, Integer maxAcceptedWordLength,
                                          Color textColor) {
        super(minAcceptedWordLength, maxAcceptedWordLength, textColor);
    }

    public NonLinearRandomAngleTextPaster(Integer minAcceptedWordLength, Integer maxAcceptedWordLength,
                                          ColorGenerator colorGenerator) {
        super(minAcceptedWordLength, maxAcceptedWordLength, colorGenerator);
    }

    public NonLinearRandomAngleTextPaster(Integer minAcceptedWordLength, Integer maxAcceptedWordLength,
                                          ColorGenerator colorGenerator, Boolean manageColorPerGlyph) {
        super(minAcceptedWordLength, maxAcceptedWordLength, colorGenerator, manageColorPerGlyph);
    }

    /**
     * @see com.octo.captcha.component.image.textpaster.AbstractTextPaster#pasteText(java.awt.image.BufferedImage, java.text.AttributedString)
     */
    public BufferedImage pasteText(final BufferedImage background, final AttributedString attributedWord) throws CaptchaException {
        BufferedImage out = copyBackground(background);
        Graphics2D g2d = pasteBackgroundAndSetTextColor(out, background);
        g2d.setRenderingHints(renderingHints);
        g2d.translate(10, background.getHeight() / 2);

        AttributedCharacterIterator iterator = attributedWord.getIterator();
        while (iterator.getIndex() != iterator.getEndIndex()) {
            AttributedString character = new AttributedString(String.valueOf(iterator.current()));
            character.addAttribute(TextAttribute.FONT, iterator.getAttribute(TextAttribute.FONT));
            pasteCharacter(g2d, character);
            iterator.next();
        }

        g2d.dispose();
        return out;
    }

    /**
     * Draws a certain character on the <code>BufferedImage</code> with a random angle and y pos.
     * If the characters angle is greater then 90 degrees and lower then 270 degrees, the bottom
     * of the character will be underlined.
     *
     * @param g2d       The graphics of the <code>BufferedImage</code>
     * @param character The character to be drawn
     */
    protected void pasteCharacter(Graphics2D g2d, AttributedString character) {
        Font font = (Font) character.getIterator().getAttribute(TextAttribute.FONT);
        Rectangle2D rectangle = g2d.getFontMetrics(font).getStringBounds(String.valueOf(character.getIterator().current()), g2d);
        double angle = getRandomAngle();
        int maxTranslatedY = (int) g2d.getTransform().getTranslateY();
        double y = myRandom.nextBoolean() ? myRandom.nextInt(maxTranslatedY) : -myRandom.nextInt(maxTranslatedY - (int) rectangle.getHeight());

        g2d.setFont(font);
        g2d.translate(0, y);
        if ((angle >= Math.PI / 2 || angle <= -(Math.PI / 2))) {
            character.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_LOW_TWO_PIXEL);
        }

        g2d.rotate(angle, rectangle.getX() + (rectangle.getWidth() / 2), rectangle.getY() + (rectangle.getHeight() / 2));
        g2d.drawString(character.getIterator(), 0, 0);
        g2d.rotate(-angle, rectangle.getX() + (rectangle.getWidth() / 2), rectangle.getY() + (rectangle.getHeight() / 2));
        g2d.translate(rectangle.getHeight(), -y);
    }

    /**
     * Returns a random angle between 0 and 360 degrees in radians (inclusive).
     *
     * @return a random angle between 0 and 360 degrees in radians
     */
    protected double getRandomAngle() {
        double number = myRandom.nextDouble() * myRandom.nextInt(10) + 1;
        double angle = Math.PI / number;
        return myRandom.nextBoolean() ? angle : -angle;
    }

    /**
     * Adds <code>RenderingHints</code> for the drawing of the characters.
     *
     * @param key   The RenderingHints Key
     * @param value The RenderingHints value
     */
    public void addRenderingHints(RenderingHints.Key key, Object value) {
        renderingHints.put(key, value);
    }
}