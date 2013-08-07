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
import com.octo.captcha.component.image.backgroundgenerator.FileReaderRandomBackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.FunkyBackgroundGenerator;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.TwistedAndShearedRandomFontGenerator;
import com.octo.captcha.component.image.fontgenerator.TwistedRandomFontGenerator;
import com.octo.captcha.component.image.textpaster.DoubleRandomTextPaster;
import com.octo.captcha.component.image.textpaster.RandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;

import java.awt.*;

/**
 * <p>Description: very simple gimpy</p>
 *
 * @author <a href="mailto:mga@octo.com">Mathieu Gandin</a>
 * @version 1.0
 * @deprecated
 */
public class MultipleGimpyEngine
        extends com.octo.captcha.engine.image.DefaultImageCaptchaEngine {

    static com.octo.captcha.image.ImageCaptchaFactory[] factories;

    static {
        //word generator
        com.octo.captcha.component.word.wordgenerator.WordGenerator dictionnaryWords = new com.octo.captcha.component.word.wordgenerator.DictionaryWordGenerator(
                new com.octo.captcha.component.word.FileDictionary(
                        "toddlist"));

        //wordtoimage components
        TextPaster randomPaster = new RandomTextPaster(new Integer(6),
                new Integer(8), Color.white);
        TextPaster doublePaster = new DoubleRandomTextPaster(new Integer(6),
                new Integer(8), Color.white);

        BackgroundGenerator fileBack = new FileReaderRandomBackgroundGenerator(
                new Integer(200), new Integer(100), "/gimpybackgrounds");
        BackgroundGenerator funkyBack = new FunkyBackgroundGenerator(
                new Integer(200), new Integer(100));

        FontGenerator twistedFont = new TwistedRandomFontGenerator(
                new Integer(30), new Integer(45));
        FontGenerator shearedFont = new TwistedAndShearedRandomFontGenerator(
                new Integer(30), new Integer(45));

        //word2image 1
        com.octo.captcha.component.image.wordtoimage.WordToImage word2image = new com.octo.captcha.component.image.wordtoimage.ComposedWordToImage(
                twistedFont, fileBack, randomPaster);
        com.octo.captcha.component.image.wordtoimage.WordToImage word2image1 = new com.octo.captcha.component.image.wordtoimage.ComposedWordToImage(
                shearedFont, fileBack, randomPaster);
        com.octo.captcha.component.image.wordtoimage.WordToImage word2image2 = new com.octo.captcha.component.image.wordtoimage.ComposedWordToImage(
                twistedFont, funkyBack, randomPaster);
        com.octo.captcha.component.image.wordtoimage.WordToImage word2image3 = new com.octo.captcha.component.image.wordtoimage.ComposedWordToImage(
                shearedFont, funkyBack, randomPaster);
        com.octo.captcha.component.image.wordtoimage.WordToImage word2image4 = new com.octo.captcha.component.image.wordtoimage.ComposedWordToImage(
                twistedFont, fileBack, doublePaster);
        com.octo.captcha.component.image.wordtoimage.WordToImage word2image5 = new com.octo.captcha.component.image.wordtoimage.ComposedWordToImage(
                shearedFont, fileBack, doublePaster);
        com.octo.captcha.component.image.wordtoimage.WordToImage word2image6 = new com.octo.captcha.component.image.wordtoimage.ComposedWordToImage(
                twistedFont, funkyBack, doublePaster);
        com.octo.captcha.component.image.wordtoimage.WordToImage word2image7 = new com.octo.captcha.component.image.wordtoimage.ComposedWordToImage(
                shearedFont, funkyBack, doublePaster);

        //Add to array
        factories = new com.octo.captcha.image.ImageCaptchaFactory[8];
        factories[0] =
                new com.octo.captcha.image.gimpy.GimpyFactory(dictionnaryWords,
                        word2image);
        factories[1] =
                new com.octo.captcha.image.gimpy.GimpyFactory(dictionnaryWords,
                        word2image1);
        factories[2] =
                new com.octo.captcha.image.gimpy.GimpyFactory(dictionnaryWords,
                        word2image2);
        factories[3] =
                new com.octo.captcha.image.gimpy.GimpyFactory(dictionnaryWords,
                        word2image3);
        factories[4] =
                new com.octo.captcha.image.gimpy.GimpyFactory(dictionnaryWords,
                        word2image4);
        factories[5] =
                new com.octo.captcha.image.gimpy.GimpyFactory(dictionnaryWords,
                        word2image5);
        factories[6] =
                new com.octo.captcha.image.gimpy.GimpyFactory(dictionnaryWords,
                        word2image6);
        factories[7] =
                new com.octo.captcha.image.gimpy.GimpyFactory(dictionnaryWords,
                        word2image7);

    }

    public MultipleGimpyEngine() {

        super(factories);

    }

}
