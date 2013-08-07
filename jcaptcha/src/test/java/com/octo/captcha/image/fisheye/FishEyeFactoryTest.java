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

import com.octo.captcha.component.image.backgroundgenerator.UniColorBackgroundGenerator;
import com.octo.captcha.component.image.deformation.ImageDeformationByFilters;
import junit.framework.TestCase;

import java.awt.*;

public class FishEyeFactoryTest extends TestCase {
    FishEyeFactory fishEyeFactory;


    protected void setUp() throws Exception {
        super.setUp();
        this.fishEyeFactory = new FishEyeFactory(new UniColorBackgroundGenerator(new Integer(300), new Integer(300),
                Color.black), new ImageDeformationByFilters(null), new Integer(10), new Integer(0));
    }

    public void testGetImageCaptcha() throws Exception {
        for (int i = 0; i < 10; i++) {
            assertTrue("sould be not null", fishEyeFactory.getImageCaptcha().getChallenge() != null);
        }
        
        try {
            this.fishEyeFactory = new FishEyeFactory(new UniColorBackgroundGenerator(new Integer(10), new Integer(10),
                    Color.black), new ImageDeformationByFilters(null), new Integer(100), new Integer(100));
            fail("should not be able to construct");
        } catch (Exception e) {
        	assertNotNull(e.getMessage());
        }
        
        this.fishEyeFactory = new FishEyeFactory(new UniColorBackgroundGenerator(new Integer(10), new Integer(10),
                Color.black), new ImageDeformationByFilters(null), new Integer(1), new Integer(10));
        for (int i = 0; i < 10; i++) {
            assertTrue("sould be never fail", fishEyeFactory.getImageCaptcha().validateResponse(new Point(5, 5)).booleanValue());
        }
    }
}