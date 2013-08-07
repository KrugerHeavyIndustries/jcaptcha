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

package com.octo.captcha.component.image.fontgenerator;

import junit.framework.TestCase;

import java.awt.*;

/**
 * <p>Description: </p>
 *
 * @author <a href="mailto:mga@octo.com">Mathieu Gandin</a>
 * @version 1.0
 */
public class DeformedRandomFontGeneratorTest extends TestCase {

    private DeformedRandomFontGenerator deformedRandomFontGenerator;
    private Integer minFontSize = new Integer(10);

    /**
     * Constructor for DeformedRandomFontGeneratorTest.
     */
    public DeformedRandomFontGeneratorTest(String name) {
        super(name);
    }

    public void setUp() {
        this.deformedRandomFontGenerator =
                new DeformedRandomFontGenerator(this.minFontSize, null);
    }

    public void testGetFont() {
        Font test = this.deformedRandomFontGenerator.getFont();
        assertNotNull(test);
    }
}
