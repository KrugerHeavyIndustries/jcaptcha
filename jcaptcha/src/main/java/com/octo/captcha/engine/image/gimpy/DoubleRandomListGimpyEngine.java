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
import com.octo.captcha.engine.image.ListImageCaptchaEngine;

import java.awt.*;

/**
 * <p>Description: </p>
 *
 * @author <a href="mailto:mga@octo.com">Mathieu Gandin</a>
 * @version 1.0
 */
public class DoubleRandomListGimpyEngine extends ListImageCaptchaEngine {

    protected void buildInitialFactories() {
        com.octo.captcha.component.word.wordgenerator.WordGenerator wordGenerator =
                new com.octo.captcha.component.word.wordgenerator.DictionaryWordGenerator(
                        new com.octo.captcha.component.word.FileDictionary(
                                "toddlist"));

        TextPaster doubleRandomTextPaster = new DoubleRandomTextPaster(
                new Integer(8), new Integer(10), Color.white);

        BackgroundGenerator back = new MultipleShapeBackgroundGenerator(
                new Integer(200), new Integer(100));

        FontGenerator fontGenerator = new DeformedRandomFontGenerator(
                new Integer(20), new Integer(25));

        com.octo.captcha.component.image.wordtoimage.WordToImage word2image =
                new com.octo.captcha.component.image.wordtoimage.ComposedWordToImage(
                        fontGenerator,
                        back, doubleRandomTextPaster);

        com.octo.captcha.image.ImageCaptchaFactory imageCaptchaFactory =
                new com.octo.captcha.image.gimpy.GimpyFactory(
                        wordGenerator, word2image);

        this.addFactory(imageCaptchaFactory);
    }

}
