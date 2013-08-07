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

import junit.framework.TestCase;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * <p>Description: </p>
 *
 * @author <a href="mailto:mga@octo.com">Mathieu Gandin</a>
 * @version 1.0
 */
public class SimpleWordToImageTest extends TestCase {

    private SimpleWordToImage simpleWordToImage;

    /**
     * Constructor for SimpleWordToImageTest.
     */
    public SimpleWordToImageTest(String name) {
        super(name);
    }

    public void setUp() {
        this.simpleWordToImage = new SimpleWordToImage();
    }

    public void testGetFont() {
        Font test = this.simpleWordToImage.getFont();
        Font expected = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts()[0];
        assertNotNull(test);
        assertEquals(expected, test);
    }

    public void testGetBackround() {
        BufferedImage test = this.simpleWordToImage.getBackround();
        assertNotNull(test);
    }

    public void testGetMaxAcceptedWordLenght() {
        int test = this.simpleWordToImage.getMaxAcceptedWordLenght();
        int expected = 10;
        assertEquals(expected, test);
    }

    public void testGetMinAcceptedWordLenght() {
        int test = this.simpleWordToImage.getMinAcceptedWordLenght();
        int expected = 1;
        assertEquals(expected, test);
    }

      public void testGetMaxAcceptedWordLength() {
        int test = this.simpleWordToImage.getMaxAcceptedWordLength();
        int expected = 10;
        assertEquals(expected, test);
    }

    public void testGetMinAcceptedWordLength() {
        int test = this.simpleWordToImage.getMinAcceptedWordLength();
        int expected = 1;
        assertEquals(expected, test);
    }

    public void testGetImageHeight() {
        int test = this.simpleWordToImage.getImageHeight();
        int expected = 50;
        assertEquals(expected, test);
    }

    public void testGetImageWidth() {
        int test = this.simpleWordToImage.getImageWidth();
        int expected = 100;
        assertEquals(expected, test);
    }

    public void testGetMinFontSize() {
        int test = this.simpleWordToImage.getMinFontSize();
        int expected = 10;
        assertEquals(expected, test);
    }

}
