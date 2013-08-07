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

package com.octo.captcha.image.gimpy;

import com.octo.captcha.CaptchaException;
import com.octo.captcha.component.image.wordtoimage.SimpleWordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import junit.framework.TestCase;

public class GimpyFactoryTest extends TestCase {

    GimpyFactory tested;

    protected void setUp() throws Exception {
        super.setUp();
        tested = new GimpyFactory(new RandomWordGenerator("a"), new SimpleWordToImage());
    }

    public void testGetRandomLength() throws Exception {
        //be carefull values tide to SimpleWordToImage.
        for (int i = 1; i < 11; i++) {
            //System.out.println(" a trouver : "+i);
            int j;
            do {

                j = tested.getRandomLength().intValue();
                if (j < 1 || j > 10) {
                    fail("Out of authorized range!");
                }

                //System.out.println("trouvé : "+j);

            } while (j != i);
        }
    }

    public void testGimpyFactory() throws Exception {
        try {
            new GimpyFactory(null, null);
            fail("Test is not implemented");
        } catch (CaptchaException e) {
            assertNotNull(e.getMessage());
        }
        try {
            new GimpyFactory(new RandomWordGenerator("a"), null);
            fail("Test is not implemented");
        } catch (CaptchaException e) {
            assertNotNull(e.getMessage());
        }

        try {
            new GimpyFactory(null, new SimpleWordToImage());
            fail("Test is not implemented");
        } catch (CaptchaException e) {
            assertNotNull(e.getMessage());
        }
    }

}
