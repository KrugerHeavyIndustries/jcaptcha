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
public class BaffleRandomTextPasterTest extends TestCase {

    protected BaffleRandomTextPaster baffleRandomTextPaster;
    protected Integer minAcceptedWordLength = new Integer(10);
    protected Integer maxAcceptedWordLength = new Integer(10);
    protected Integer numberHoles = new Integer(10);
    protected Color textColor = Color.black;
    protected Color holesColor = Color.white;

    /**
     * Constructor for BaffleRandomTextPasterTest.
     */
    public BaffleRandomTextPasterTest(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        this.baffleRandomTextPaster = new BaffleRandomTextPaster(this.minAcceptedWordLength,
                this.maxAcceptedWordLength,
                this.textColor,
                this.numberHoles,
                this.holesColor);
    }

    public void testPasteText() {
        BufferedImage imageTest = new BufferedImage(100, 50, BufferedImage.TYPE_INT_RGB);
        AttributedString stringTest = new AttributedString("test");
        BufferedImage test = null;
        try {
            test = this.baffleRandomTextPaster.pasteText(imageTest, stringTest);
            assertNotNull(test);
            assertEquals(imageTest.getHeight(), test.getHeight());
            assertEquals(imageTest.getWidth(), test.getWidth());
        } catch (CaptchaException e) {
            assertNotNull(e);
        }
    }

    public void testGetMaxAcceptedWordLenght() {
        assertEquals(this.maxAcceptedWordLength.intValue(),
                this.baffleRandomTextPaster.getMaxAcceptedWordLenght());
    }

    public void testGetMinAcceptedWordLenght() {
        assertEquals(this.minAcceptedWordLength.intValue(),
                this.baffleRandomTextPaster.getMinAcceptedWordLenght());
    }

}
