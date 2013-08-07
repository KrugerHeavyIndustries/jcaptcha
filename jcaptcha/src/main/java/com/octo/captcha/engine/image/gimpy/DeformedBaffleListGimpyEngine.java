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
import com.octo.captcha.component.image.backgroundgenerator.UniColorBackgroundGenerator;
import com.octo.captcha.component.image.deformation.ImageDeformation;
import com.octo.captcha.component.image.deformation.ImageDeformationByFilters;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.TwistedAndShearedRandomFontGenerator;
import com.octo.captcha.component.image.textpaster.BaffleRandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.DeformedComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.wordgenerator.DictionaryWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;

import java.awt.*;
import java.awt.image.ImageFilter;

/**
 * <p><ul><li></li></ul></p>
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public class DeformedBaffleListGimpyEngine extends ListImageCaptchaEngine {

    protected void buildInitialFactories() {

        //build filters
        com.jhlabs.image.EmbossFilter emboss = new com.jhlabs.image.EmbossFilter();
        com.jhlabs.image.SphereFilter sphere = new com.jhlabs.image.SphereFilter();
        com.jhlabs.image.RippleFilter rippleBack = new com.jhlabs.image.RippleFilter();
        com.jhlabs.image.RippleFilter ripple = new com.jhlabs.image.RippleFilter();
        com.jhlabs.image.TwirlFilter twirl = new com.jhlabs.image.TwirlFilter();
        com.jhlabs.image.WaterFilter water = new com.jhlabs.image.WaterFilter();

        com.jhlabs.image.WeaveFilter weaves = new com.jhlabs.image.WeaveFilter();
        com.jhlabs.image.CrystalizeFilter crystal = new com.jhlabs.image.CrystalizeFilter();

        //emboss.setBumpHeight(1.5d);

        ripple.setWaveType(com.jhlabs.image.RippleFilter.NOISE);
        ripple.setXAmplitude(3);
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

        water.setAmplitude(1);
        water.setAntialias(true);
        water.setWavelength(20);

        twirl.setAngle(3 / 360);

        sphere.setRefractionIndex(1);

        weaves.setUseImageColors(true);

        crystal.setScale(0.5f);
        crystal.setGridType(com.jhlabs.image.CrystalizeFilter.RANDOM);
        crystal.setFadeEdges(false);
        crystal.setEdgeThickness(0.2f);
        crystal.setRandomness(0.1f);

        ImageDeformation rippleDef = new ImageDeformationByFilters(
                new ImageFilter[]{ripple});
        ImageDeformation waterDef = new ImageDeformationByFilters(
                new ImageFilter[]{water});
        ImageDeformation embossDef = new ImageDeformationByFilters(
                new ImageFilter[]{emboss});
        ImageDeformation rippleDefBack = new ImageDeformationByFilters(
                new ImageFilter[]{rippleBack});
        ImageDeformation cristalDef = new ImageDeformationByFilters(
                new ImageFilter[]{crystal});
        ImageDeformation weavesDef = new ImageDeformationByFilters(
                new ImageFilter[]{weaves});

        ImageDeformation none = new ImageDeformationByFilters(null);
        //word generator
        WordGenerator words = new DictionaryWordGenerator(
                new com.octo.captcha.component.word.FileDictionary(
                        "toddlist"));
        //wordtoimage components
        TextPaster paster = new BaffleRandomTextPaster(new Integer(6), new Integer(
                8), Color.black, new Integer(3),
                Color.white);
        BackgroundGenerator back = new UniColorBackgroundGenerator(
                new Integer(200), new Integer(100), Color.white);
        //BackgroundGenerator back = new FunkyBackgroundGenerator(new Integer(200), new Integer(100));
        FontGenerator font = new TwistedAndShearedRandomFontGenerator(
                new Integer(30), new Integer(40));
        //Add factories
        WordToImage word2image = new ComposedWordToImage(font, back, paster);
        this.addFactory(
                new com.octo.captcha.image.gimpy.GimpyFactory(words,
                        word2image));
        //build factories
        word2image = new DeformedComposedWordToImage(font, back, paster,
                rippleDef,
                waterDef,
                embossDef);
        this.addFactory(new GimpyFactory(words, word2image));
        //      select filters for 2
        word2image = new DeformedComposedWordToImage(font, back, paster,
                rippleDefBack,
                cristalDef,
                rippleDef);
        this.addFactory(new GimpyFactory(words, word2image));
        //select filters for 3
        word2image = new DeformedComposedWordToImage(font, back, paster,
                rippleDefBack,
                none,
                weavesDef);
        this.addFactory(new GimpyFactory(words, word2image));

    }
}
