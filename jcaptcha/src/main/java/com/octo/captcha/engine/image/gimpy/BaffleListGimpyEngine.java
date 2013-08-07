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

import java.awt.Color;

import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.UniColorBackgroundGenerator;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.BaffleRandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;

/**
 * <p>Description: </p>
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public class BaffleListGimpyEngine extends ListImageCaptchaEngine {

    protected void buildInitialFactories() {
        //word generator
        com.octo.captcha.component.word.wordgenerator.WordGenerator dictionnaryWords = new com.octo.captcha.component.word.wordgenerator.ComposeDictionaryWordGenerator(
                new com.octo.captcha.component.word.FileDictionary(
                        "toddlist"));
        //wordtoimage components
        TextPaster randomPaster = new BaffleRandomTextPaster(new Integer(8), new Integer(
                15), Color.black,
                new Integer(3), Color.white);
        BackgroundGenerator back = new UniColorBackgroundGenerator(
                new Integer(200), new Integer(100), Color.white);
        //BackgroundGenerator back = new FunkyBackgroundGenerator(new Integer(200), new Integer(100));
        FontGenerator shearedFont = new RandomFontGenerator(new Integer(20),
                new Integer(25));
        //word2image 1
        com.octo.captcha.component.image.wordtoimage.WordToImage word2image = new com.octo.captcha.component.image.wordtoimage.ComposedWordToImage(
                shearedFont, back, randomPaster);

        this.addFactory(
                new com.octo.captcha.image.gimpy.GimpyFactory(dictionnaryWords,
                        word2image));
    }
}
