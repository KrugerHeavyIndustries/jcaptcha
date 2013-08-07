/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.component.image.wordtoimage;

import com.octo.captcha.CaptchaException;
import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.deformation.ImageDeformation;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.textpaster.TextPaster;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.AttributedString;

/**
 * <p>This implementation uses deformation components to distord the image. </br>It takes three array of deformations :
 * for the background image, for the text only, and for the final image it proceeds as folows : <ul> <li>Checks the word
 * length</li> <li>Creates an java.text.AttributedString from the word</li> <li>Create an image for the background a
 * BackgroundGenerator component</li> <li>Apply background deformations</li> <li>Apply font to the AttributedString
 * using the abstract method getFont</li> <li>Create a transparent backround </li> <li>Put the text on the transparent
 * backround using the abstact method pasteText</li> <li>Apply the text deformations </li> <li>Paste the transparent
 * image using an alpha composite</li> <li>Apply the final deformations </li> <li>Return the newly created image</li>
 * </ul>
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public class DeformedComposedWordToImage extends ComposedWordToImage {

    private ImageDeformation[] backgroundDeformation;
    private ImageDeformation[] textDeformation;
    private ImageDeformation[] finalDeformation;

    /**
     * Composed word to image that applys filters
     *
     * @param fontGenerator         a AbstractFontGenerator to implement the getFont() method
     * @param background            a AbstractBackgroundGenerator to implement the getBackround() method
     * @param textPaster            a AbstractTextParser to implement the pasteText() method
     * @param backgroundDeformation to be apply on the background image
     * @param textDeformation       to be apply on the text image
     * @param finalDeformation      to be apply on the final image
     */
    public DeformedComposedWordToImage(FontGenerator fontGenerator,
                                       BackgroundGenerator background,
                                       TextPaster textPaster,
                                       ImageDeformation backgroundDeformation,
                                       ImageDeformation textDeformation,
                                       ImageDeformation finalDeformation) {
        super(fontGenerator, background, textPaster);
        this.backgroundDeformation = new ImageDeformation[]{backgroundDeformation};
        this.textDeformation = new ImageDeformation[]{textDeformation};
        this.finalDeformation = new ImageDeformation[]{finalDeformation};
    }

    /**
     * Composed word to image that applys filters
     *
     * @param fontGenerator         a AbstractFontGenerator to implement the getFont() method
     * @param background            a AbstractBackgroundGenerator to implement the getBackround() method
     * @param textPaster            a AbstractTextParser to implement the pasteText() method
     * @param backgroundDeformation to be apply on the background image
     * @param textDeformation       to be apply on the text image
     * @param finalDeformation      to be apply on the final image
     */
    public DeformedComposedWordToImage(FontGenerator fontGenerator,
                                       BackgroundGenerator background,
                                       TextPaster textPaster,
                                       ImageDeformation[] backgroundDeformation,
                                       ImageDeformation[] textDeformation,
                                       ImageDeformation[] finalDeformation) {
        super(fontGenerator, background, textPaster);
        this.backgroundDeformation = backgroundDeformation;
        this.textDeformation = textDeformation;
        this.finalDeformation = finalDeformation;
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
     * @throws com.octo.captcha.CaptchaException
     *          if word is invalid or if image generation fails.
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
        for (int i = 0; i < backgroundDeformation.length; i++) {
            out = backgroundDeformation[i].deformImage(out);
        }

        //paste text on a transparent background
        BufferedImage transparent = new BufferedImage(out.getWidth(), out.getHeight(),
                BufferedImage.TYPE_INT_ARGB);

        //use textpaster to paste the text
        transparent = pasteText(transparent, aword);

        //and apply deformation
        for (int i = 0; i < textDeformation.length; i++) {
            transparent = textDeformation[i].deformImage(transparent);
        }


        Graphics2D g3 = (Graphics2D) out.getGraphics();

        g3.drawImage(transparent, 0, 0, null);
        g3.dispose();
        //apply final deformation
        for (int i = 0; i < finalDeformation.length; i++) {
            out = finalDeformation[i].deformImage(out);
        }
        return out;
    }
}
