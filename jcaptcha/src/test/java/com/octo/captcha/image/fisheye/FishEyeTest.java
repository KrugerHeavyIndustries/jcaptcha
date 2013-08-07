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

package com.octo.captcha.image.fisheye;


import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.UniColorBackgroundGenerator;
import junit.framework.TestCase;

import java.awt.*;

public class FishEyeTest extends TestCase {
    FishEye fishEye;
    BackgroundGenerator back = new UniColorBackgroundGenerator(new Integer(300), new Integer(300),
            Color.black);

    public void testValidateResponse() throws Exception {
        fishEye = new FishEye("question", back.getBackground(), new Point(10, 10), new Integer(0));
        assertTrue("string answer", fishEye.validateResponse("10,10").booleanValue());
        assertTrue("point answer", fishEye.validateResponse(new Point(10, 10)).booleanValue());
        assertFalse("invalid point answer", fishEye.validateResponse(new Point(11, 10)).booleanValue());
        assertFalse("invalid string", fishEye.validateResponse("toto,10").booleanValue());
        assertFalse("invalid string", fishEye.validateResponse(",10").booleanValue());
        assertFalse("invalid string", fishEye.validateResponse("10,").booleanValue());
        assertFalse("invalid string", fishEye.validateResponse("10;10").booleanValue());
    }

    public void testValidateResponseTolerance() throws Exception {
        fishEye = new FishEye("question", back.getBackground(), new Point(10, 10), new Integer(1));
        assertTrue("string answer", fishEye.validateResponse("10,11").booleanValue());
        assertTrue("string answer", fishEye.validateResponse("11,10").booleanValue());
        assertTrue("point answer", fishEye.validateResponse(new Point(11, 10)).booleanValue());
        assertTrue("point answer", fishEye.validateResponse(new Point(10, 11)).booleanValue());
        assertFalse("invalid point answer", fishEye.validateResponse(new Point(11, 11)).booleanValue());

    }


}