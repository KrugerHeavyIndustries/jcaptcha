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

package com.octo.captcha.component.image.wordtoimage;

import java.awt.Color;
import java.awt.image.ImageFilter;
import java.awt.image.ReplicateScaleFilter;

import junit.framework.TestCase;

import com.octo.captcha.CaptchaException;
import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.GradientBackgroundGenerator;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.SimpleTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;

/**
 * <p>Description: </p>
 *
 * @author <a href="mailto:mga@octo.com">Mathieu Gandin</a>
 * @version 1.0
 * @deprecated
 */
public class FilteredComposedWordToImageTest extends TestCase {

    private FilteredComposedWordToImage filteredComposedWordToImage;
    private Integer minAcceptedWordLength = new Integer(1);
    private Integer maxAcceptedWordLength = new Integer(10);
    private Integer imageHeight = new Integer(100);
    private Integer imageWidth = new Integer(100);
    private Integer minFontSize = new Integer(10);
    private Integer maxFontSize = new Integer(10);

    public void setUp() {

        BackgroundGenerator background = new GradientBackgroundGenerator(this.imageHeight, this.imageWidth, Color.black, Color.white);
        FontGenerator fontGenerator = new RandomFontGenerator(this.minFontSize, this.maxFontSize);
        TextPaster textPaster = new SimpleTextPaster(this.minAcceptedWordLength, this.maxAcceptedWordLength, Color.blue);

        ImageFilter backFilter = new ReplicateScaleFilter(background.getImageWidth(),
                background.getImageHeight());

        ImageFilter textFilter = new ReplicateScaleFilter(background.getImageWidth(),
                background.getImageHeight());

        ImageFilter finalFilter = new ReplicateScaleFilter(background.getImageWidth(),
                background.getImageHeight());

        ImageFilter[] backFilters = {backFilter};

        ImageFilter[] textFilters = {textFilter};

        ImageFilter[] finalFilters = {finalFilter};

        this.filteredComposedWordToImage = new FilteredComposedWordToImage(fontGenerator,
                background,
                textPaster,
                backFilters,
                textFilters,
                finalFilters);
    }

    public void testGetImage() throws CaptchaException {
        String test = "test";
        assertNotNull(this.filteredComposedWordToImage.getImage(test));
    }

    public void testGetImageNull() {
        try {
            this.filteredComposedWordToImage.getImage(null);
            fail();
        } catch (CaptchaException e) {
            assertNotNull(e);
        }
    }

}
