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
public class DoubleRandomTextPasterTest extends TestCase {

    protected DoubleRandomTextPaster doubleRandomTextPaster;
    protected Integer minAcceptedWordLength = new Integer(10);
    protected Integer maxAcceptedWordLength = new Integer(15);
    protected Color textColor = Color.black;

    /**
     * Constructor for DoubleRandomTextPasterTest.
     */
    public DoubleRandomTextPasterTest(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        this.doubleRandomTextPaster = new DoubleRandomTextPaster(this.minAcceptedWordLength,
                this.maxAcceptedWordLength,
                this.textColor);
    }

    public void testPasteText() {
        BufferedImage imageTest = new BufferedImage(100, 150, BufferedImage.TYPE_INT_RGB);
        AttributedString stringTest = new AttributedString("test");
        BufferedImage test = null;
        try {
            test = this.doubleRandomTextPaster.pasteText(imageTest, stringTest);
        } catch (CaptchaException e) {
            assertNotNull(e);
        }
        assertNotNull(test);
        assertEquals(imageTest.getHeight(), test.getHeight());
        assertEquals(imageTest.getWidth(), test.getWidth());
    }

}
