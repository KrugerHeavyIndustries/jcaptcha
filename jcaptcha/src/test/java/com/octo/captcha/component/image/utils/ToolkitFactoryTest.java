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

package com.octo.captcha.component.image.utils;

import junit.framework.TestCase;

import java.awt.*;

import com.octo.captcha.CaptchaException;

/**
 * <p>Description: </p>
 *
 * @author <a href="mailto:mga@octo.com">Mathieu Gandin</a>
 * @author <a href="antoine.veret@gmail.com">Antoine Veret</a>
 * @version 1.0
 */
public class ToolkitFactoryTest extends TestCase {

    public void setUp() {
        System.getProperties().remove(ToolkitFactory.TOOLKIT_IMPL);
    }

    public void testGetaDefaultToolkit() {
        assertTrue(ToolkitFactory.getToolkit() instanceof Toolkit);
    }

    public void testGetCustomToolkit() {
        System.setProperty(ToolkitFactory.TOOLKIT_IMPL, MockToolkit.class.getName());
        assertTrue(ToolkitFactory.getToolkit() instanceof MockToolkit);
    }

    public void testGetBadClassToolkit() {
        System.setProperty(ToolkitFactory.TOOLKIT_IMPL, "toto");
        try {
            ToolkitFactory.getToolkit();
            fail("should throw an exception");
        } catch (Exception expected) {
            assertEquals(CaptchaException.class, expected.getClass());
        }
    }

    public void tearDown() {
        System.getProperties().remove(ToolkitFactory.TOOLKIT_IMPL);
    }
}
