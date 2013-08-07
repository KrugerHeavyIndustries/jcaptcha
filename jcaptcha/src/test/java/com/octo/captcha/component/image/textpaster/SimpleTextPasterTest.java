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

package com.octo.captcha.component.image.textpaster;

import com.octo.captcha.CaptchaException;
import junit.framework.TestCase;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.AttributedString;

/**
 * <p>Description: </p>
 *
 * @author <a href="mailto:mga@octo.com">Mathieu Gandin</a>
 * @version 1.0
 */
public class SimpleTextPasterTest extends TestCase {

    private SimpleTextPaster simpleTextPaster;
    private Integer minAcceptedWordLength = new Integer(10);
    private Integer maxAcceptedWordLength = new Integer(15);

    /**
     * Constructor for SimpleTextPasterTest.
     */
    public SimpleTextPasterTest(String name) {
        super(name);
    }

    public void setUp() {
        this.simpleTextPaster = new SimpleTextPaster(this.minAcceptedWordLength, this.maxAcceptedWordLength, Color.blue);
    }

    public void testPasteText() {
        BufferedImage testBufferedImage = new BufferedImage(100, 50, BufferedImage.TYPE_INT_RGB);
        AttributedString testAttributedString = new AttributedString("test");
        BufferedImage test = null;
        try {
            test = this.simpleTextPaster.pasteText(testBufferedImage, testAttributedString);
        } catch (CaptchaException e) {
            assertNotNull(e);
        }
        assertNotNull(test);
        assertEquals(testBufferedImage.getHeight(), test.getHeight());
        assertEquals(testBufferedImage.getWidth(), test.getWidth());
    }

    public void testGetMaxAcceptedWordLenght() {
        assertEquals(this.maxAcceptedWordLength.intValue(),
                this.simpleTextPaster.getMaxAcceptedWordLenght());
    }

    public void testGetMinAcceptedWordLenght() {
        assertEquals(this.minAcceptedWordLength.intValue(),
                this.simpleTextPaster.getMinAcceptedWordLenght());
    }

     public void testGetMaxAcceptedWordLength() {
        assertEquals(this.maxAcceptedWordLength.intValue(),
                this.simpleTextPaster.getMaxAcceptedWordLength());
    }

    public void testGetMinAcceptedWordLength() {
        assertEquals(this.minAcceptedWordLength.intValue(),
                this.simpleTextPaster.getMinAcceptedWordLength());
    }

}
