/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.sound.speller;

import com.octo.captcha.CaptchaException;
import com.octo.captcha.CaptchaQuestionHelper;
import com.octo.captcha.component.sound.wordtosound.WordToSound;
import com.octo.captcha.component.word.worddecorator.SpellerWordDecorator;
import com.octo.captcha.component.word.worddecorator.WordDecorator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.sound.SoundCaptcha;
import com.octo.captcha.sound.SoundCaptchaFactory;

import javax.sound.sampled.AudioInputStream;
import java.security.SecureRandom;
import java.util.Locale;
import java.util.Random;

/**
 * Factory for SpellerSound
 *
 * @author Benoit Doumas
 * @version 1.0
 */
public class SpellerSoundFactory extends SoundCaptchaFactory {

    private WordGenerator wordGenerator;

    private WordToSound word2Sound;

    private WordDecorator wordDecorator;

    private Random myRandom = new SecureRandom();

    /**
     * The bundle question key for CaptchaQuestionHelper
     */
    public static final String BUNDLE_QUESTION_KEY = SpellerSound.class.getName();

    /**
     * Construct a GimpySoundFactory from a word generator component and a wordtosound component
     *
     * @param thewordGenerator component
     * @param theword2Sound    component
     */
    public SpellerSoundFactory(WordGenerator wordGenerator, WordToSound word2Sound,
                               SpellerWordDecorator wordDecorator) {
        if (wordGenerator == null) {
            throw new CaptchaException("Invalid configuration for a "
                    + "SpellingSoundFactory : WordGenerator can't be null");
        }
        if (word2Sound == null) {
            throw new CaptchaException("Invalid configuration for a "
                    + "SpellingSoundFactory : Word2Sound can't be null");
        }
        if (wordDecorator == null) {
            throw new CaptchaException("Invalid configuration for a "
                    + "SpellingSoundFactory : wordDecorator can't be null");
        }
        this.wordGenerator = wordGenerator;
        this.word2Sound = word2Sound;
        this.wordDecorator = wordDecorator;
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
        AudioInputStream sound = this.word2Sound.getSound(wordDecorator.decorateWord(word));
        SoundCaptcha soundCaptcha = new SpellerSound(getQuestion(Locale.getDefault()), sound, word);
        return soundCaptcha;
    }

    /**
     * @param locale the locale
     * @return a localized sound captcha
     */
    public SoundCaptcha getSoundCaptcha(Locale locale) {
        String word = this.wordGenerator.getWord(getRandomLength(), locale);
        AudioInputStream sound = this.word2Sound.getSound(wordDecorator.decorateWord(word), locale);
        SoundCaptcha soundCaptcha = new SpellerSound(getQuestion(locale), sound, word);
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