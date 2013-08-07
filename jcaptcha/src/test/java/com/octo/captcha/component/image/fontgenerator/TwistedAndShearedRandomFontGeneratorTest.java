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


package com.octo.captcha.component.image.fontgenerator;

import junit.framework.TestCase;

import java.awt.*;

/**
 * <p>Description: </p>
 *
 * @author <a href="mailto:mga@octo.com">Mathieu Gandin</a>
 * @version 1.0
 */
public class TwistedAndShearedRandomFontGeneratorTest extends TestCase {

    private TwistedAndShearedRandomFontGenerator
            twistedAndShearedRandomFontGenerator;

    /**
     * Constructor for TwistedAndShearedRandomFontGeneratorTest.
     */
    public TwistedAndShearedRandomFontGeneratorTest(String arg0) {
        super(arg0);
    }

    public void setUp() {
        this.twistedAndShearedRandomFontGenerator =
                new TwistedAndShearedRandomFontGenerator(new Integer(10), null);
    }

    public void testGetFont() {
        Font test = this.twistedAndShearedRandomFontGenerator.getFont();
        assertNotNull(test);
    }


    public void testGet10000Font() {
        for (int i = 0; i < 10000; i++) {
            Font test = this.twistedAndShearedRandomFontGenerator.getFont();
            assertNotNull(test);
        }

    }

}
