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

package com.octo.captcha.engine.image.gimpy;

import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.MultipleShapeBackgroundGenerator;
import com.octo.captcha.component.image.fontgenerator.DeformedRandomFontGenerator;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.textpaster.DoubleRandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;

import java.awt.*;
import java.awt.image.ImageFilter;

/**
 * <p>Description: </p>
 *
 * @author <a href="mailto:mga@octo.com">Mathieu Gandin</a>
 * @version 1.0
 * @deprecated
 */
public class FilteredDoubleRandomListGimpyEngine
        extends com.octo.captcha.engine.image.ListImageCaptchaEngine {

    protected void buildInitialFactories() {

        com.jhlabs.image.RippleFilter rippleBack = new com.jhlabs.image.RippleFilter();

        rippleBack.setWaveType(com.jhlabs.image.RippleFilter.NOISE);
        rippleBack.setXAmplitude(5);
        rippleBack.setYAmplitude(5);
        rippleBack.setXWavelength(10);
        rippleBack.setYWavelength(10);
        rippleBack.setEdgeAction(com.jhlabs.image.TransformFilter.CLAMP);

        TextPaster paster =
                new DoubleRandomTextPaster(new Integer(8), new Integer(15),
                        Color.black);
        BackgroundGenerator back = new MultipleShapeBackgroundGenerator(
                new Integer(200), new Integer(100));
        FontGenerator font =
                new DeformedRandomFontGenerator(new Integer(25),
                        new Integer(27));
        com.octo.captcha.component.word.wordgenerator.WordGenerator words =
                new com.octo.captcha.component.word.wordgenerator.DictionaryWordGenerator(
                        new com.octo.captcha.component.word.FileDictionary(
                                "toddlist"));

        com.octo.captcha.component.image.wordtoimage.WordToImage word2image =
                new com.octo.captcha.component.image.wordtoimage.FilteredComposedWordToImage(
                        font,
                        back,
                        paster,
                        new ImageFilter[]{rippleBack},
                        new ImageFilter[]{},
                        new ImageFilter[]{});
        com.octo.captcha.image.ImageCaptchaFactory factory = new com.octo.captcha.image.gimpy.GimpyFactory(
                words, word2image);
        this.addFactory(factory);
    }

}
