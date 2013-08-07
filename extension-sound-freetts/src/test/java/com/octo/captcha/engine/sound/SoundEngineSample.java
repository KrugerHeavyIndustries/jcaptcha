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
package com.octo.captcha.engine.sound;

import com.octo.captcha.component.sound.soundconfigurator.FreeTTSSoundConfigurator;
import com.octo.captcha.component.sound.soundconfigurator.SoundConfigurator;
import com.octo.captcha.component.sound.wordtosound.FreeTTSWordToSound;
import com.octo.captcha.component.sound.wordtosound.WordToSound;
import com.octo.captcha.component.word.DefaultSizeSortedWordList;
import com.octo.captcha.component.word.DictionaryReader;
import com.octo.captcha.component.word.SizeSortedWordList;
import com.octo.captcha.component.word.worddecorator.SpellerWordDecorator;
import com.octo.captcha.component.word.wordgenerator.DictionaryWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.engine.sound.utils.SoundToFile;
import com.octo.captcha.sound.SoundCaptcha;
import com.octo.captcha.sound.SoundCaptchaFactory;
import com.octo.captcha.sound.speller.SpellerSoundFactory;

import javax.sound.sampled.AudioInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

/**
 * Test sample for a sound captcha
 *
 * @author Benoit Doumas
 */
public class SoundEngineSample {
    private static String voiceName = "kevin16";

    private static String voicePackage = "com.sun.speech.freetts.en.us.cmu_time_awb.AlanVoiceDirectory,com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory";

    static String[] wordlist;

    static WordGenerator words;

    static SoundCaptchaFactory factory;

    static WordToSound wordToSound;

    public static void main(String[] args) {
        SoundEngineSample.wordlist = new String[]{"and", "oh", "test", "test", "hello", "lame",
                "eating", "snake", "roots", "yeah", "azerty"};

        SoundEngineSample.words = new DictionaryWordGenerator(
                (new SoundEngineSample()).new ArrayDictionary(wordlist));

        SoundConfigurator configurator = new FreeTTSSoundConfigurator("kevin16",
                "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory", 1.0f, 100, 70);
        SoundEngineSample.wordToSound = new FreeTTSWordToSound(new FreeTTSSoundConfigurator(
                voiceName, voicePackage, 1.0f, 100, 100), 3, 6);
        SpellerWordDecorator decorator = new SpellerWordDecorator(", ");
        SoundEngineSample.factory = new SpellerSoundFactory(words, wordToSound, decorator);
        //SoundEngineSample.factory = new GimpySoundFactory(words, wordToSound);
        for (int i = 1; i <= 10; i++)
            test();

    }

    public static void test() {
        SoundCaptcha tCaptcha = factory.getSoundCaptcha(Locale.US);

        System.out.println(tCaptcha.getQuestion());
        AudioInputStream tInputStream = tCaptcha.getSoundChallenge();
        try {
            SoundToFile.serialize(tInputStream, new File("c:\\test.wav"));
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
        String text = null;
        BufferedReader reader;
        reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter text: ");
        System.out.flush();

        try {
            text = reader.readLine();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        if (tCaptcha.validateResponse(text).booleanValue()) {
            System.out.print("Passed!!!");
        } else {
            System.out.print("Failed!!!");
        }
        tCaptcha.disposeChallenge();
    }

    private class ArrayDictionary implements DictionaryReader {
        private String[] list;

        private DefaultSizeSortedWordList wordList;

        public ArrayDictionary(String[] list) {
            this.list = list;
            wordList = new DefaultSizeSortedWordList(Locale.getDefault());
            for (int i = 0; i < list.length; i++) {
                wordList.addWord(list[i]);
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see com.octo.captcha.component.word.DictionaryReader#getWordList()
         */
        public SizeSortedWordList getWordList() {

            return wordList;
        }

        /*
         * (non-Javadoc)
         * 
         * @see com.octo.captcha.component.word.DictionaryReader#getWordList(java.util.Locale)
         */
        public SizeSortedWordList getWordList(Locale arg0) {

            return wordList;
        }

    }
}