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
import com.octo.captcha.component.image.backgroundgenerator.FunkyBackgroundGenerator;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.RandomTextPaster;
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
public class FilteredListGimpyEngine
        extends com.octo.captcha.engine.image.ListImageCaptchaEngine {

    protected void buildInitialFactories() {

        //      build filters
        com.jhlabs.image.EmbossFilter emboss = new com.jhlabs.image.EmbossFilter();
        com.jhlabs.image.SphereFilter sphere = new com.jhlabs.image.SphereFilter();
        com.jhlabs.image.RippleFilter rippleBack = new com.jhlabs.image.RippleFilter();
        com.jhlabs.image.RippleFilter ripple = new com.jhlabs.image.RippleFilter();
        com.jhlabs.image.TwirlFilter twirl = new com.jhlabs.image.TwirlFilter();
        com.jhlabs.image.WaterFilter water = new com.jhlabs.image.WaterFilter();
        com.jhlabs.image.WeaveFilter weaves = new com.jhlabs.image.WeaveFilter();
        com.jhlabs.image.CrystalizeFilter crystal = new com.jhlabs.image.CrystalizeFilter();

        emboss.setBumpHeight(2.0f);

        ripple.setWaveType(com.jhlabs.image.RippleFilter.NOISE);
        ripple.setXAmplitude(10);
        ripple.setYAmplitude(3);
        ripple.setXWavelength(20);
        ripple.setYWavelength(10);
        ripple.setEdgeAction(com.jhlabs.image.TransformFilter.CLAMP);

        rippleBack.setWaveType(com.jhlabs.image.RippleFilter.NOISE);
        rippleBack.setXAmplitude(5);
        rippleBack.setYAmplitude(5);
        rippleBack.setXWavelength(10);
        rippleBack.setYWavelength(10);
        rippleBack.setEdgeAction(com.jhlabs.image.TransformFilter.CLAMP);

        water.setAmplitude(5);
        water.setAntialias(true);
        water.setWavelength(10);

        twirl.setAngle(3 / 360);

        sphere.setRefractionIndex(1);

        weaves.setUseImageColors(true);

        crystal.setScale(0.5f);
        crystal.setGridType(com.jhlabs.image.CrystalizeFilter.RANDOM);
        crystal.setFadeEdges(false);
        crystal.setEdgeThickness(0.2f);
        crystal.setRandomness(0.1f);

        TextPaster paster =
                new RandomTextPaster(new Integer(8), new Integer(10),
                        Color.gray);
        BackgroundGenerator back =
                new FunkyBackgroundGenerator(new Integer(200),
                        new Integer(100));
        FontGenerator font =
                new RandomFontGenerator(new Integer(25), new Integer(35));
        com.octo.captcha.component.word.wordgenerator.WordGenerator words =
                new com.octo.captcha.component.word.wordgenerator.DictionaryWordGenerator(
                        new com.octo.captcha.component.word.FileDictionary(
                                "toddlist"));

        //build factories
        com.octo.captcha.image.ImageCaptchaFactory[] factories = new com.octo.captcha.image.ImageCaptchaFactory[3];
        com.octo.captcha.component.image.wordtoimage.WordToImage word2image =
                new com.octo.captcha.component.image.wordtoimage.FilteredComposedWordToImage(
                        font,
                        back,
                        paster,
                        new ImageFilter[]{water},
                        new ImageFilter[]{emboss},
                        new ImageFilter[]{ripple});
        factories[0] =
                new com.octo.captcha.image.gimpy.GimpyFactory(words,
                        word2image);
        //select filters for 2
        word2image =
                new com.octo.captcha.component.image.wordtoimage.FilteredComposedWordToImage(
                        font,
                        back,
                        paster,
                        new ImageFilter[]{rippleBack},
                        new ImageFilter[]{crystal},
                        new ImageFilter[]{ripple});
        factories[1] =
                new com.octo.captcha.image.gimpy.GimpyFactory(words,
                        word2image);
        //select filters for 3
        word2image =
                new com.octo.captcha.component.image.wordtoimage.FilteredComposedWordToImage(
                        font,
                        back,
                        paster,
                        new ImageFilter[]{rippleBack},
                        new ImageFilter[]{},
                        new ImageFilter[]{weaves});
        factories[2] =
                new com.octo.captcha.image.gimpy.GimpyFactory(words,
                        word2image);
        this.addFactories(factories);
    }

}
