/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.component.image.wordtoimage;

import com.octo.captcha.CaptchaException;
import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.utils.ToolkitFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.text.AttributedString;

/**
 * <p>This implementation uses filters to distord the image. </br>It takes three array of filters : for the background
 * image, for the text only, and for the final image it proceeds as folows : <ul> <li>Checks the word length</li>
 * <li>Creates an java.text.AttributedString from the word</li> <li>Create an image for the background using the abstact
 * method getBackround</li> <li>Apply background filters</li> <li>Apply font to the AttributedString using the abstract
 * method getFont</li> <li>Create a transparent backround </li> <li>Put the text on the transparent backround using the
 * abstact method pasteText</li> <li>Apply the text filters </li> <li>Paste the transparent image using an alpha
 * composite</li> <li>Apply the final filters </li> <li>Return the newly created image</li> </ul>
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 * @deprecated since beta-4 : use DeformedComposedWordToImage, that now use the new deformation component.
 */
public class FilteredComposedWordToImage extends ComposedWordToImage {

    private ImageFilter[] backgroundFilters;
    private ImageFilter[] textFilters;
    private ImageFilter[] finalFilters;

    /**
     * Composed word to image that applys filters
     *
     * @param fontGenerator     a AbstractFontGenerator to implement the getFont() method
     * @param background        a AbstractBackgroundGenerator to implement the getBackround() method
     * @param textPaster        a AbstractTextParser to implement the pasteText() method
     * @param backgroundFilters to be apply on the background image
     * @param textFilters       to be apply on the text image
     * @param finalFilters      to be apply on the final image
     */
    public FilteredComposedWordToImage(FontGenerator fontGenerator,
                                       BackgroundGenerator background,
                                       TextPaster textPaster,
                                       ImageFilter[] backgroundFilters,
                                       ImageFilter[] textFilters,
                                       ImageFilter[] finalFilters) {
        super(fontGenerator, background, textPaster);
        this.backgroundFilters = backgroundFilters;
        this.textFilters = textFilters;
        this.finalFilters = finalFilters;
    }

    /**
     * Creates an image of the provided String This method is a skeleton for creation algorithm. it proceeds as folows :
     * <ul> <li>Checks the word length</li> <li>Creates an java.text.AttributedString from the word</li> <li>Create an
     * image for the background using the abstact method getBackround</li> <li>Apply background filters</li> <li>Apply
     * font to the AttributedString using the abstract method getFont</li> <li>Create a transparent backround </li>
     * <li>Put the text on the transparent backround using the abstact method pasteText</li> <li>Apply the text filters
     * </li> <li>Paste the transparent image using an alpha composite</li> <li>Apply the final filters </li> <li>Return
     * the newly created image</li> </ul>
     *
     * @return an image representation of the word
     *
     * @throws CaptchaException if word is invalid or if image generation fails.
     */
    public BufferedImage getImage(String word) throws CaptchaException {
        BufferedImage background = getBackround();
        AttributedString aword = getAttributedString(word, checkWordLength(word));
        //copy background
        BufferedImage out = new BufferedImage(background.getWidth(), background.getHeight(),
                background.getType());
        Graphics2D g2 = (Graphics2D) out.getGraphics();
        //paste background
        g2.drawImage(background, 0, 0, out.getWidth(), out.getHeight(), null);
        g2.dispose();
        //apply filters to backround
        applyFilters(out, backgroundFilters);

        //paste text on a transparent background
        BufferedImage transparent = new BufferedImage(out.getWidth(), out.getHeight(),
                BufferedImage.TYPE_INT_ARGB);

        //use textpaster to paste the text
        transparent = pasteText(transparent, aword);

        //and apply deformation
        applyFilters(transparent, textFilters);

        Graphics2D g3 = (Graphics2D) out.getGraphics();

        g3.drawImage(transparent, 0, 0, null);
        g3.dispose();
        //apply final deformation
        applyFilters(out, finalFilters);
        return out;
    }

    private void applyFilters(BufferedImage image, ImageFilter[] filters) {
        FilteredImageSource filtered;
        if (filters != null) {
            for (int i = 0; i < filters.length; i++) {
                ImageFilter backgroundFilter = filters[i];
                filtered = new FilteredImageSource(image.getSource(),
                        backgroundFilter);
                Image temp = ToolkitFactory.getToolkit().createImage(filtered);
                image.getGraphics().drawImage(temp, 0, 0, new Color(255, 255, 255, 0), null);
            }
        }
    }
}
