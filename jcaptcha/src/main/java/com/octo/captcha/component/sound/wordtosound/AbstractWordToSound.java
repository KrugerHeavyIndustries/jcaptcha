/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.component.sound.wordtosound;

import com.octo.captcha.component.sound.soundconfigurator.SoundConfigurator;

import javax.sound.sampled.AudioInputStream;

/**
 * WordToSound abstract implementation
 *
 * @author Benoit
 * @version 1.0
 */
public abstract class AbstractWordToSound implements WordToSound {
    protected int maxAcceptedWordLength;

    protected int minAcceptedWordLength;

    protected SoundConfigurator configurator = null;

    /**
     * Constructor with a configurator
     *
     * @param configurator         the configuration for this particular voice
     * @param minAcceptedWordLength Length Minimal of generated words
     * @param maxAcceptedWordLength Length Maximal of generated words
     */
    public AbstractWordToSound(SoundConfigurator configurator, int minAcceptedWordLength,
                               int maxAcceptedWordLength) {
        this.configurator = configurator;
        this.minAcceptedWordLength = minAcceptedWordLength;
        this.maxAcceptedWordLength = maxAcceptedWordLength;
    }

    public int getMaxAcceptedWordLength() {
        return maxAcceptedWordLength;
    }

    public int getMinAcceptedWordLength() {
        return minAcceptedWordLength;
    }

    public int getMaxAcceptedWordLenght() {
        return maxAcceptedWordLength;
    }

    public int getMinAcceptedWordLenght() {
        return minAcceptedWordLength;
    }

    /**
     * Add effect to the sound
     *
     * @param sound The sound to modify
     *
     * @return The modified sound.
     */
    protected abstract AudioInputStream addEffects(AudioInputStream sound);
}
