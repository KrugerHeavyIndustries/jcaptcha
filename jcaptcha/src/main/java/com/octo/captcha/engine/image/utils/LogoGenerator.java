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

package com.octo.captcha.engine.image.utils;

import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.FileReaderRandomBackgroundGenerator;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.TwistedAndShearedRandomFontGenerator;
import com.octo.captcha.component.image.textpaster.SimpleTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.wordgenerator.DummyWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.image.ImageCaptcha;
import com.octo.captcha.image.ImageCaptchaFactory;
import com.octo.captcha.image.gimpy.GimpyFactory;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * <p>Description: Generate a sample logo for the master webSite. Main method takes one arg : the file path of the
 * generated logo</p>
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public class LogoGenerator {

    public static void main(String[] args) throws IOException {
        TextPaster paster = new SimpleTextPaster(new Integer(8),
                new Integer(8), Color.white);
        BackgroundGenerator back = new FileReaderRandomBackgroundGenerator(
                new Integer(200), new Integer(100),
                "/gimpybackgrounds");
        FontGenerator font = new TwistedAndShearedRandomFontGenerator(
                new Integer(30), null);
        WordGenerator words = new DummyWordGenerator("JCAPTCHA");
        WordToImage word2image = new ComposedWordToImage(font, back, paster);
        ImageCaptchaFactory factory = new GimpyFactory(words, word2image);
        ImageCaptcha pix = factory.getImageCaptcha();
        ImageToFile.serialize(pix.getImageChallenge(), new File(args[0]));
    }
}
