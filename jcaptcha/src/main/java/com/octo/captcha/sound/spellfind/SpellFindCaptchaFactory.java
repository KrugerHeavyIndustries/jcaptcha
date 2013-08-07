/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.sound.spellfind;

import com.octo.captcha.CaptchaException;
import com.octo.captcha.CaptchaQuestionHelper;
import com.octo.captcha.component.sound.wordtosound.WordToSound;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.sound.SoundCaptcha;
import com.octo.captcha.sound.SoundCaptchaFactory;
import com.octo.captcha.sound.speller.SpellerSound;

import javax.sound.sampled.AudioInputStream;
import java.security.SecureRandom;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * <p><ul><li></li></ul></p>
 *
 * @author <a href="mailto:marc.antoine.garrigue@gmail.com">Marc-Antoine Garrigue</a>
 * @version $Id: SpellFindCaptchaFactory.java 322 2007-03-26 17:45:25Z antoineveret $
 */
public class SpellFindCaptchaFactory extends SoundCaptchaFactory {

    private WordGenerator wordGenerator;

    private WordToSound word2Sound;

    private Random myRandom = new SecureRandom();
//
//    private int minWords;
//    private int maxWords;

    /**
     * The bundle question key for CaptchaQuestionHelper
     */
    public static final String BUNDLE_QUESTION_KEY = SpellFindCaptchaFactory.class.getName();

    /**
     * Construct a GimpySoundFactory from a word generator component and a wordtosound component
     */
    public SpellFindCaptchaFactory(WordGenerator wordGenerator, WordToSound word2Sound) {
        if (wordGenerator == null) {
            throw new CaptchaException("Invalid configuration for a "
                    + "SpellFindCaptchaFactory : WordGenerator can't be null");
        }
        if (word2Sound == null) {
            throw new CaptchaException("Invalid configuration for a "
                    + "SpellFindCaptchaFactory : Word2Sound can't be null");
        }
        /*if (minWords<0||maxWords<minWords) {
            throw new CaptchaException("Invalid configuration for a "
                    + "SpellFindCaptchaFactory : should be maxWords>minWords>0");
        }
        this.maxWords=maxWords;
        this.minWords=minWords;*/
        this.wordGenerator = wordGenerator;
        this.word2Sound = word2Sound;


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
        return getSoundCaptcha(Locale.getDefault());
    }

    /**
     * @param locale the locale
     * @return a localized sound captcha
     */
    public SoundCaptcha getSoundCaptcha(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle(this.getClass().getName(), locale);
        int length = getRandomLength().intValue();

        //WordAndPosition[] wordsAndPositions = new WordAndPosition[length];
        StringBuffer challenge = new StringBuffer();
        StringBuffer response = new StringBuffer();
        for (int i = 0; i < length; i++) {
            //get a new word
            String word = this.wordGenerator.getWord(new Integer(getRandomLength().intValue()), locale);
            //add it to collection and add its position
            int position = Math.abs(myRandom.nextInt() % word.length());
            //append to challenge
            challenge.append(bundle.getString("number"));
            challenge.append(" ");
            challenge.append(position + 1);
            challenge.append(" ");
            challenge.append(bundle.getString("word"));
            challenge.append(" ");
            challenge.append(word);
            challenge.append(" ");
            challenge.append(length - 1 == i ? bundle.getString("end") : bundle.getString("transition"));
            //append to response
            response.append(word.charAt(position));
        }

        AudioInputStream sound = this.word2Sound.getSound(challenge.toString(), locale);
        SoundCaptcha soundCaptcha = new SpellerSound(getQuestion(locale), sound, response.toString());
        return soundCaptcha;
    }

    protected String getQuestion(Locale locale) {
        return CaptchaQuestionHelper.getQuestion(locale, BUNDLE_QUESTION_KEY);
    }


    protected Integer getRandomLength() {
        /*Integer wordLength;
        int range = maxWords-minWords;
        int randomRange = range != 0 ? myRandom.nextInt(range + 1) : 0;
        return randomRange + minWords;*/
        Integer wordLength;
        int range = getWordToSound().getMaxAcceptedWordLength()
                - getWordToSound().getMinAcceptedWordLength();
        int randomRange = range != 0 ? myRandom.nextInt(range + 1) : 0;
        wordLength = new Integer(randomRange + getWordToSound().getMinAcceptedWordLength());
        return wordLength;
    }

    /*

private class WordAndPosition{
public WordAndPosition(String word, int position) {
    this.word = word;
    this.position = position;
}

String word;
int position;
}
    */
}
