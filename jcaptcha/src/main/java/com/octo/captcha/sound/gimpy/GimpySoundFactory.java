/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.sound.gimpy;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;

import com.octo.captcha.CaptchaException;
import com.octo.captcha.CaptchaQuestionHelper;
import com.octo.captcha.component.sound.wordtosound.WordToSound;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.sound.SoundCaptcha;
import com.octo.captcha.sound.SoundCaptchaFactory;

/**
 * @author Gandin Mathieu
 * @author Benoit Doumas
 * @version 1.1
 */
public class GimpySoundFactory extends SoundCaptchaFactory {

    private WordGenerator wordGenerator;

    private WordToSound word2Sound;

    private Random myRandom = new SecureRandom();

    /**
     * The bundle question key for CaptchaQuestionHelper
     */
    public static final String BUNDLE_QUESTION_KEY = GimpySound.class.getName();

    /**
     * Construct a GimpySoundFactory from a word generator component and a wordtosound component
     *
     * @param thewordGenerator component
     * @param theword2Sound    component
     */
    public GimpySoundFactory(WordGenerator thewordGenerator, WordToSound theword2Sound) {
        if (thewordGenerator == null) {
            throw new CaptchaException("Invalid configuration for a "
                    + "GimpySoundFactory : WordGenerator can't be null");
        }
        if (theword2Sound == null) {
            throw new CaptchaException("Invalid configuration for a "
                    + "GimpySoundFactory : Word2Sound can't be null");
        }
        this.wordGenerator = thewordGenerator;
        this.word2Sound = theword2Sound;
    }

    public WordToSound getWordToSound() {
        return this.word2Sound;
    }

    public WordGenerator getWordGenerator() {
        return this.wordGenerator;
    }

    /**
     * @return a Sound Captcha
     */
    public SoundCaptcha getSoundCaptcha() {
        String word = this.wordGenerator.getWord(getRandomLength(), Locale.getDefault());
        AudioInputStream sound = this.word2Sound.getSound(word);
        SoundCaptcha soundCaptcha = new GimpySound(getQuestion(Locale
                .getDefault()), sound, word);
        return soundCaptcha;
    }

    /**
     * @param locale the locale
     * @return a localized sound captcha
     */
    public SoundCaptcha getSoundCaptcha(Locale locale) {
        String word = this.wordGenerator.getWord(getRandomLength(), locale);
        AudioInputStream sound = this.word2Sound.getSound(word, locale);
        SoundCaptcha soundCaptcha = new GimpySound(getQuestion(locale), sound, word);
        return soundCaptcha;
    }

    protected String getQuestion(Locale locale) {
        return CaptchaQuestionHelper.getQuestion(locale, BUNDLE_QUESTION_KEY);
    }

    protected Integer getRandomLength() {
        Integer wordLength;
        int range = getWordToSound().getMaxAcceptedWordLength()
                - getWordToSound().getMinAcceptedWordLength();
        int randomRange = range != 0 ? myRandom.nextInt(range + 1) : 0;
        wordLength = new Integer(randomRange + getWordToSound().getMinAcceptedWordLength());
        return wordLength;
    }

}