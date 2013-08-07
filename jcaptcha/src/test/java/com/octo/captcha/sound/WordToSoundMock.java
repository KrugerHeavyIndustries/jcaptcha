/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.sound;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.octo.captcha.CaptchaException;
import com.octo.captcha.component.sound.wordtosound.WordToSound;

/**
 * Allow to test Sound captcha support without a real implementation
 *
 * @author <a href="mailto:antoine.veret@gmail.com">Antoine Véret</a>
 * @date 19 avr. 2007
 */
public class WordToSoundMock implements WordToSound {

    private AudioInputStream audioInputStream = null;

    public WordToSoundMock() throws IOException, UnsupportedAudioFileException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("challenge1.wav");
        audioInputStream = AudioSystem.getAudioInputStream(inputStream);
    }

    public int getMaxAcceptedWordLenght() {
        return 0;
    }

    public int getMaxAcceptedWordLength() {
        return 6;
    }

    public int getMinAcceptedWordLenght() {
        return 0;
    }

    public int getMinAcceptedWordLength() {
        return 2;
    }

    public AudioInputStream getSound(String word) throws CaptchaException {
        return audioInputStream;
    }

    public AudioInputStream getSound(String word, Locale locale) throws CaptchaException {
        return audioInputStream; 
    }
}
