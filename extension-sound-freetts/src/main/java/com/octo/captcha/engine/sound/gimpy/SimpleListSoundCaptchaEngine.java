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

package com.octo.captcha.engine.sound.gimpy;


import com.octo.captcha.component.sound.soundconfigurator.FreeTTSSoundConfigurator;
import com.octo.captcha.component.sound.soundconfigurator.SoundConfigurator;
import com.octo.captcha.component.sound.wordtosound.FreeTTSWordToSound;
import com.octo.captcha.engine.sound.ListSoundCaptchaEngine;
import com.octo.captcha.sound.gimpy.GimpySoundFactory;


/**
 * <p>Description: simple gimpy sound engine </p>
 *
 * @author Benoit Doumas
 * @version 1.0
 */
public class SimpleListSoundCaptchaEngine
        extends ListSoundCaptchaEngine {

    protected void buildInitialFactories() {
        com.octo.captcha.component.word.wordgenerator.WordGenerator words = new com.octo.captcha.component.word.wordgenerator.DictionaryWordGenerator(
                new com.octo.captcha.component.word.FileDictionary("toddlist"));

        SoundConfigurator configurator = new FreeTTSSoundConfigurator("kevin16",
                "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory", 1.0f, 100, 70);
        FreeTTSWordToSound wordToSound = new FreeTTSWordToSound(configurator, 4, 10);

        this.addFactory(new GimpySoundFactory(words, wordToSound));
    }

}
