/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

/*
 * jcaptcha, the open source java framework for captcha definition and integration
 * copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

/*
 * jcaptcha, the open source java framework for captcha definition and integration
 * copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */


package com.octo.captcha.component.image.wordtoimage;


import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.GradientBackgroundGenerator;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.SimpleTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import junit.framework.TestCase;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * <p>Description: </p>
 *
 * @author <a href="mailto:mga@octo.com">Mathieu Gandin</a>
 * @version 1.0
 */
public class ComposedWordToImageTest extends TestCase {

    private ComposedWordToImage composedWordToImage;
    private Integer minAcceptedWordLength = new Integer(1);
    private Integer maxAcceptedWordLength = new Integer(10);
    private Integer imageHeight = new Integer(100);
    private Integer imageWidth = new Integer(100);
    private Integer minFontSize = new Integer(10);
    private Integer maxFontSize = new Integer(12);

    /**
     * Constructor for ComposedWordToImageTest.
     */
    public ComposedWordToImageTest(String name) {
        super(name);
    }

    public void setUp() {
        BackgroundGenerator background = new GradientBackgroundGenerator(this.imageWidth, this.imageHeight, Color.black, Color.white);
        FontGenerator fontGenerator = new RandomFontGenerator(this.minFontSize, this.maxFontSize);
        TextPaster textPaster = new SimpleTextPaster(this.minAcceptedWordLength, this.maxAcceptedWordLength, Color.blue);
        this.composedWordToImage = new ComposedWordToImage(fontGenerator, background, textPaster);
    }

    public void testGetFont() {
        Font test = this.composedWordToImage.getFont();
        assertNotNull(test);
    }

    public void testGetBackround() {
        BufferedImage test = this.composedWordToImage.getBackround();
        assertNotNull(test);
    }

    public void testGetMaxAcceptedWordLength() {
        int expected = this.maxAcceptedWordLength.intValue();
        int test = this.composedWordToImage.getMaxAcceptedWordLength();
        assertEquals(expected, test);
    }

    public void testGetMinAcceptedWordLength() {
        int expected = this.minAcceptedWordLength.intValue();
        int test = this.composedWordToImage.getMinAcceptedWordLength();
        assertEquals(expected, test);
    }

    public void testGetImageHeight() {
        int expected = this.imageHeight.intValue();
        int test = this.composedWordToImage.getImageHeight();
        assertEquals(expected, test);
    }

    public void testGetImageWidth() {
        int expected = this.imageWidth.intValue();
        int test = this.composedWordToImage.getImageWidth();
        assertEquals(expected, test);
    }

    public void testGetMinFontSize() {
        int expected = this.minFontSize.intValue();
        int test = this.composedWordToImage.getMinFontSize();
        assertEquals(expected, test);
    }

}
