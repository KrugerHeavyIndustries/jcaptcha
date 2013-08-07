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
import com.octo.captcha.component.image.textpaster.RandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;

import java.awt.*;

/**
 * <p>Description: </p>
 *
 * @author <a href="mailto:mga@octo.com">Mathieu Gandin</a>
 * @version 1.0
 */
public class SimpleListImageCaptchaEngine
        extends com.octo.captcha.engine.image.ListImageCaptchaEngine {

    protected void buildInitialFactories() {
        com.octo.captcha.component.word.wordgenerator.WordGenerator wordGenerator = new com.octo.captcha.component.word.wordgenerator.RandomWordGenerator(
                "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
        TextPaster textPaster = new RandomTextPaster(new Integer(5),
                new Integer(8), Color.white);
        BackgroundGenerator backgroundGenerator = new FunkyBackgroundGenerator(
                new Integer(200), new Integer(100));
        FontGenerator fontGenerator = new TwistedAndShearedRandomFontGenerator(
                new Integer(25), new Integer(30));
        com.octo.captcha.component.image.wordtoimage.WordToImage wordToImage = new com.octo.captcha.component.image.wordtoimage.ComposedWordToImage(
                fontGenerator, backgroundGenerator, textPaster);
        this.addFactory(
                new com.octo.captcha.image.gimpy.GimpyFactory(wordGenerator,
                        wordToImage));
    }

}
