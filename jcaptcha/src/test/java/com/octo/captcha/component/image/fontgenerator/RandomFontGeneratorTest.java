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
public class RandomFontGeneratorTest extends TestCase {

    private RandomFontGenerator randomFontGenerator;

    private RandomFontGenerator randomFontGeneratorWithList;
    
    private int minFontSize = 8;
    
    private int maxFontSize = 8;

    /**
     * Constructor for RandomFontGeneratorTest.
     */
    public RandomFontGeneratorTest(String name) {
        super(name);
    }

    public void setUp() {
        this.randomFontGenerator =
                new RandomFontGenerator(new Integer(minFontSize), new Integer(maxFontSize));

        Font[] fontsList = new Font[2];
        fontsList[0] = new Font("Courier", Font.BOLD, 10);
        fontsList[1] = new Font("Arial", Font.BOLD, 10);

        this.randomFontGeneratorWithList =
                new RandomFontGenerator(new Integer(minFontSize), new Integer(maxFontSize), fontsList);
    }

    public void testGetFont() {
        Font test = this.randomFontGenerator.getFont();
        assertNotNull(test);
    }

    public void testGetFontWithList() {
        Font test = this.randomFontGeneratorWithList.getFont();
        assertNotNull(test);
        assertTrue(test.getName().startsWith("Arial"));
    }


    public void testGetFontWithEmptyList() {
        Font[] fontsList = new Font[0];
        try {
            new RandomFontGenerator(new Integer(10), new Integer(10), fontsList);

            fail("should have thrown an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        	assertNotNull(e.getMessage());
        }
    }


    public void testGetFontWithBadFontList() {
        Font[] fontsList = new Font[1];
        fontsList[0] = new Font("Courier", Font.BOLD, 10);

        try {
            new RandomFontGenerator(new Integer(10), new Integer(10), fontsList);
            fail("should have thrown an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        	assertNotNull(e.getMessage());
        }
    }

    public void testGetFontWithBadFontPrefix() {

        this.randomFontGenerator.setBadFontNamePrefixes(new String[] {"Cour"});

        Font arial = new Font("Arial", Font.BOLD, 10);

        Font[] fontsList = new Font[2];
        fontsList[0] = new Font("Courier", Font.BOLD, 10);
        fontsList[1] = arial;

        java.util.List checkedFontList = this.randomFontGenerator.cleanFontList(fontsList);
        assertEquals(1, checkedFontList.size());
        assertEquals(arial, checkedFontList.get(0));
    }                                                                                        

    public void testGetFontWithEmptyBadFontPrefix() {

        this.randomFontGenerator.setBadFontNamePrefixes(new String[] {""});

        Font[] fontsList = new Font[2];
        fontsList[0] = new Font("Courier", Font.BOLD, 10);
        fontsList[1] = new Font("Arial", Font.BOLD, 10);

        java.util.List checkedFontList = this.randomFontGenerator.cleanFontList(fontsList);
        assertEquals("All fonts should be preserved", 2, checkedFontList.size());
    }
    
    public void testMinFontSize() {
    	Font helvetica = new Font("Helvetica", Font.BOLD, 2);
    	Font styled = this.randomFontGeneratorWithList.applyStyle(helvetica);
    	assertTrue(minFontSize <= styled.getSize());
    }
    
    public void testMaxFontSize() {
    	Font helvetica = new Font("Helvetica", Font.BOLD, 24);
    	Font styled = this.randomFontGeneratorWithList.applyStyle(helvetica);
    	assertTrue(maxFontSize >= styled.getSize());
    }

}
