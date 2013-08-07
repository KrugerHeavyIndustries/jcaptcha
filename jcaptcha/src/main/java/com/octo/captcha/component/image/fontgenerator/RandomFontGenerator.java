/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.component.image.fontgenerator;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * <p>Description: Random font generator that return one of the available system's (or optionay specified) fonts, using a min and max
 * font size. This list is formerly cleaned of OCR readable font and symbol font</p>
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public class RandomFontGenerator extends AbstractFontGenerator {

    /**
     * These are the valid font styles.
     */
    private int[] STYLES = {Font.PLAIN, Font.ITALIC, Font.BOLD, Font.ITALIC | Font.BOLD};


    /**
     * Any font that this class uses must be able to generate all of the characters in this list.
     */
    private String requiredCharacters = "abcdefghijklmnopqrstuvwxyz0123456789";

    /**
     * Prefixes of font names that are avoided by default.  The default values list fonts that are totally fine in terms of
     * representing characters, of course, but they're too commonly available in OCR programs.
     */
    public static String[] defaultBadFontNamePrefixes = {
            "Courier",
            "Times Roman",
    };

    /**
     * Prefixes of font names that should be avoided.  The default values list fonts that are totally fine in terms of
     * representing characters, of course, but they're too commonly available in OCR programs.
     */
    private String[] badFontNamePrefixes = defaultBadFontNamePrefixes;

    private static final int GENERATED_FONTS_ARRAY_SIZE = 3000;

    private Font[] generatedFonts = null;

    protected Random myRandom = new SecureRandom();

    public RandomFontGenerator(Integer minFontSize, Integer maxFontSize) {
        super(minFontSize, maxFontSize);
        initializeFonts(GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts());
    }

    public RandomFontGenerator(Integer minFontSize, Integer maxFontSize, Font[] fontsList) {
        super(minFontSize, maxFontSize);
        if (fontsList == null || fontsList.length < 1) {
            throw new IllegalArgumentException("fonts list cannot be null or empty");
        }
        initializeFonts(fontsList);
    }

    public RandomFontGenerator(Integer minFontSize, Integer maxFontSize, String[] badFontNamePrefixes) {
        super(minFontSize, maxFontSize);
        this.badFontNamePrefixes = badFontNamePrefixes;   
        initializeFonts(GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts());
    }                                                                                                        

	private void initializeFonts(Font[] fontList) {
		List fonts = cleanFontList(fontList);
        checkInitializedFontListSize(fonts);
        generatedFonts = generateCustomStyleFontArray(fonts);
	}
    
    private void checkInitializedFontListSize(List fontList) {
        if (fontList.size() < 1) {
            throw new IllegalArgumentException("fonts list cannot be null or empty, some of your font are removed from the list by this class, Courrier and TimesRoman");
        }
    }

    /**
     * Method from imageFromWord method to apply font to String. Implementations must take into account the minFontSize
     * and the MaxFontSize.
     *
     * @return a Font
     */
    public Font getFont() {
        return generatedFonts[Math.abs(myRandom.nextInt(GENERATED_FONTS_ARRAY_SIZE))];
    }

    /**
     * @return a array of generated Fonts
     */
    private Font[] generateCustomStyleFontArray(List fontList) {
        Font[] generatedFonts = new Font[GENERATED_FONTS_ARRAY_SIZE];
        for (int i = 0; i < GENERATED_FONTS_ARRAY_SIZE; i++) {
            Font font = (Font) fontList.get(myRandom.nextInt(fontList.size()));

            Font styled = applyStyle(font);
            generatedFonts[i] = applyCustomDeformationOnGeneratedFont(styled);
        }
        return generatedFonts;
    }

	protected Font applyStyle(Font font) {
		int fontSizeIncrement = 0;
		if (getFontSizeDelta() > 0) {
		    fontSizeIncrement = Math.abs(myRandom.nextInt(getFontSizeDelta()));
		}

		Font styled = font.deriveFont( 
		                STYLES[myRandom.nextInt(STYLES.length)],
		                getMinFontSize() + fontSizeIncrement);
		return styled;
	}

	private int getFontSizeDelta() {
		return getMaxFontSize() - getMinFontSize();
	}

    /**
     * Provides a way for children class to customize the generated font array
     *
     * @param font
     * @return a customized font
     */
    protected Font applyCustomDeformationOnGeneratedFont(Font font) {
        return font;
    }


    /**
     * Create an array of fonts that is known to properly represent all the characters in requiredCharacters.
     *
     * @return array of fonts
     * @see #requiredCharacters
     */
    protected List cleanFontList(Font[] uncheckFonts) {

        // get a copy of the fonts
        // NB: be careful with this first array! -- the graphics environment obligingly
        // provides a pointer into its internal font array.

        List goodFonts = new ArrayList(uncheckFonts.length);
        // add copy of copy of list of fonts because of asList's special class and also because
        // of the graphics environment's internal point
        goodFonts.addAll(Arrays.asList(uncheckFonts));

        // Iterate through all fonts, remove the bad ones
        for (Iterator iter = goodFonts.iterator(); iter.hasNext();) {
            
        	Font f = (Font) iter.next();        	
            if (!checkFontNamePrefix(iter, f)) {     		
        		checkFontCanDisplayCharacters(iter, f);
            }
        }

        return goodFonts;
    }

	/**
     * @param iter Font iterator
     * @param f The current font
     * @return true if the font has been removed
     */
	private boolean checkFontNamePrefix(Iterator iter, Font f) {
		
		boolean removed = false;
		
		// a font is also removed if it is prefixed by a known-bad name
		for (int i = 0; i < badFontNamePrefixes.length; i++) {
		    String prefix = badFontNamePrefixes[i];
		    // verify prefix is not null
		    if (prefix != null && !"".equals(prefix)) {
		        // verify font name start with prefix
		        if (f.getName().startsWith(prefix)) {
		            iter.remove();
		            removed = true;
		            break;
		        }
		    }                                          
		}
		
		return removed;
	}

    /**
     * @param iter Font iterator
     * @param f The current font
     * @return true if the font has been removed
     */
	private boolean checkFontCanDisplayCharacters(Iterator iter, Font f) {

		boolean removed = false;
		// a font is removed if it cannot display the characters we need.

		for (int i = 0; i < requiredCharacters.length(); i++) {
		    if (!f.canDisplay(requiredCharacters.charAt(i))) {
		        iter.remove();
		        removed = true;
		        break;
		    }
		}
		return removed;
	}

    /**
     * @return a list of characters that this class must be able to represent
     */
    public String getRequiredCharacters() {
        return requiredCharacters;
    }

    /**
     * @param requiredCharacters a list of characters that this class must be able to represent
     */
    public void setRequiredCharacters(String requiredCharacters) {
        this.requiredCharacters = requiredCharacters;

    }

    /**
     * @return an array of font name prefixes that should be not used in generating captchas
     */
    public String[] getBadFontNamePrefixes() {
        return badFontNamePrefixes;
    }

    /**
     * @param badFontNamePrefixes an array of font name prefixes that should be not used in generating captchas
     */
    public void setBadFontNamePrefixes(String[] badFontNamePrefixes) {
        this.badFontNamePrefixes = badFontNamePrefixes;
    }

}
