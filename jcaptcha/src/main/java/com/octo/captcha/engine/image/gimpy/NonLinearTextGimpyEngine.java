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
import com.octo.captcha.component.image.backgroundgenerator.GradientBackgroundGenerator;
import com.octo.captcha.component.image.color.RandomListColorGenerator;
import com.octo.captcha.component.image.fontgenerator.DeformedRandomFontGenerator;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.textpaster.NonLinearTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;

/**
 * Use the non-linear text paster
 * @date 19 mars 2007
 * @author <a href="mailto:antoine.veret@gmail.com">Antoine Véret</a>
 */
public class NonLinearTextGimpyEngine extends ListImageCaptchaEngine {

    protected void buildInitialFactories() {
        
        com.octo.captcha.component.word.wordgenerator.WordGenerator wordGenerator =
                new com.octo.captcha.component.word.wordgenerator.RandomWordGenerator(
                "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");

        TextPaster textPaster = new NonLinearTextPaster(new Integer(5),
                new Integer(7), new RandomListColorGenerator(new Color[] {Color.BLACK, Color.YELLOW,
                Color.WHITE}), Boolean.TRUE);

        BackgroundGenerator backgroundGenerator = new GradientBackgroundGenerator(
                new Integer(200), new Integer(100), Color.CYAN, Color.GRAY);

        FontGenerator fontGenerator = new DeformedRandomFontGenerator(
                new Integer(25), new Integer(30));

        WordToImage wordToImage = new ComposedWordToImage(fontGenerator,
                backgroundGenerator, textPaster);

        this.addFactory(new com.octo.captcha.image.gimpy.GimpyFactory(wordGenerator, wordToImage));
    }
}
