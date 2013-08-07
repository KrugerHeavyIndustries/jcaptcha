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
import com.octo.captcha.component.image.fontgenerator.TwistedAndShearedRandomFontGenerator;
import com.octo.captcha.component.image.textpaster.DoubleRandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.engine.image.DefaultImageCaptchaEngine;

import java.awt.*;

/**
 * <p>Description: </p>
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public class BasicGimpyEngine extends DefaultImageCaptchaEngine {

    static com.octo.captcha.image.ImageCaptchaFactory[] factories;

    static {
        //word generator
        //WordGenerator dictionnaryWords = new DictionaryWordGenerator(new FileDictionnary("toddlist"));
        com.octo.captcha.component.word.wordgenerator.WordGenerator randomWords = new com.octo.captcha.component.word.wordgenerator.RandomWordGenerator(
                "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");

        //wordtoimage components
        TextPaster randomPaster = new DoubleRandomTextPaster(new Integer(3),
                new Integer(5), Color.white);

        //BackgroundGenerator fileBack = new FileReaderRandomBackgroundGenerator(new Integer(200), new Integer(100), "gimpybackgrounds");
        BackgroundGenerator funkyBack = new FunkyBackgroundGenerator(
                new Integer(200), new Integer(100));

        FontGenerator shearedFont = new TwistedAndShearedRandomFontGenerator(
                new Integer(20), new Integer(30));

        //word2image 1
        com.octo.captcha.component.image.wordtoimage.WordToImage word2image = new com.octo.captcha.component.image.wordtoimage.ComposedWordToImage(
                shearedFont, funkyBack, randomPaster);

        //Add to array
        factories = new com.octo.captcha.image.ImageCaptchaFactory[1];
        factories[0] =
                new com.octo.captcha.image.gimpy.GimpyFactory(randomWords,
                        word2image);

    }

    public BasicGimpyEngine() {

        super(factories);

    }

}
