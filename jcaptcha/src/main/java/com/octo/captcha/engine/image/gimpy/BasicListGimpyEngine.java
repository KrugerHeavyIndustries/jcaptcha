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
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.TwistedAndShearedRandomFontGenerator;
import com.octo.captcha.component.image.textpaster.DoubleRandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;

import java.awt.*;

/**
 * <p>Description: </p>
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public class BasicListGimpyEngine
        extends com.octo.captcha.engine.image.ListImageCaptchaEngine {

    protected void buildInitialFactories() {
        //word generator
        com.octo.captcha.component.word.wordgenerator.WordGenerator dictionnaryWords =
                new com.octo.captcha.component.word.wordgenerator.DictionaryWordGenerator(
                        new com.octo.captcha.component.word.FileDictionary(
                                "toddlist"));
        //wordtoimage components
        TextPaster randomPaster = new DoubleRandomTextPaster(new Integer(6),
                new Integer(8), Color.white);
        BackgroundGenerator fileBack = new FileReaderRandomBackgroundGenerator(
                new Integer(200), new Integer(100), "gimpybackgrounds");
        //BackgroundGenerator funkyBack = new FunkyBackgroundGenerator(new Integer(200), new Integer(100));
        FontGenerator shearedFont = new TwistedAndShearedRandomFontGenerator(
                new Integer(20), new Integer(25));
        //word2image 1
        com.octo.captcha.component.image.wordtoimage.WordToImage word2image =
                new com.octo.captcha.component.image.wordtoimage.ComposedWordToImage(
                        shearedFont, fileBack, randomPaster);
        this.addFactory(
                new com.octo.captcha.image.gimpy.GimpyFactory(dictionnaryWords,
                        word2image));
    }
}
