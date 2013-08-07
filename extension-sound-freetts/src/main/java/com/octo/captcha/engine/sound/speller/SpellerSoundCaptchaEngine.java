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
package com.octo.captcha.engine.sound.speller;

import com.octo.captcha.component.sound.soundconfigurator.FreeTTSSoundConfigurator;
import com.octo.captcha.component.sound.soundconfigurator.SoundConfigurator;
import com.octo.captcha.component.sound.wordtosound.FreeTTSWordToSound;
import com.octo.captcha.component.word.worddecorator.SpellerWordDecorator;
import com.octo.captcha.engine.sound.ListSoundCaptchaEngine;
import com.octo.captcha.sound.speller.SpellerSoundFactory;

/**
 * <p/>
 * Engine to generate a SpellerSound captcha. This captcha provide a sound that is the spelling of a word </p>
 *
 * @author Benoit Doumas
 * @version 1.0
 */
public class SpellerSoundCaptchaEngine extends ListSoundCaptchaEngine {

    /**
     * @see com.octo.captcha.engine.sound.ListSoundCaptchaEngine#buildInitialFactories()
     */
    protected void buildInitialFactories() {

        com.octo.captcha.component.word.wordgenerator.WordGenerator words =
                new com.octo.captcha.component.word.wordgenerator.RandomWordGenerator("0123456789");

        SoundConfigurator configurator = new FreeTTSSoundConfigurator("kevin16",
                "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory", 1.0f, 100, 110);
        FreeTTSWordToSound wordToSound = new FreeTTSWordToSound(configurator, 4, 10);

        SpellerWordDecorator decorator = new SpellerWordDecorator(", ");
        this.addFactory(new SpellerSoundFactory(words, wordToSound, decorator));

    }

}