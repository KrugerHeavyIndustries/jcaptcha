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
public class TwistedRandomFontGeneratorTest extends TestCase {

    private TwistedRandomFontGenerator
            twistedRandomFontGenerator;

    /**
     * Constructor for TwistedRandomFontGeneratorTest.
     */
    public TwistedRandomFontGeneratorTest(String arg0) {
        super(arg0);
    }

    public void setUp() {
        this.twistedRandomFontGenerator =
                new TwistedRandomFontGenerator(new Integer(10), null);
    }

    public void testGetFont() {
        Font test = this.twistedRandomFontGenerator.getFont();
        assertNotNull(test);
    }

}
