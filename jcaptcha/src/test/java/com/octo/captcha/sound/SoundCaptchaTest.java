/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */
package com.octo.captcha.sound;

import com.octo.captcha.component.word.ArrayDictionary;
import com.octo.captcha.component.word.wordgenerator.DictionaryWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.sound.gimpy.GimpySoundFactory;
import junit.framework.TestCase;

import javax.sound.sampled.AudioInputStream;

/**
 * <p/>
 * Description: </p>
 *
 * @author <a href="mailto:mga@octo.com">Mathieu Gandin </a>
 * @author Antoine Véret
 * @version 1.0
 */
public class SoundCaptchaTest extends TestCase {

    private SoundCaptcha soundCaptcha;

    /**
     * this method is for initialisation for all the test cases
     */
    public void setUp() throws Exception {
        super.setUp();

        String[] wordlist = {"and", "oh", "test", "test", "hello", "lame", "eating", "snake"};
        WordGenerator words = new DictionaryWordGenerator(new ArrayDictionary(wordlist));
        
        SoundCaptchaFactory factory = new GimpySoundFactory(words, new WordToSoundMock());
        soundCaptcha = factory.getSoundCaptcha();
    }

    /**
     * This test is for verifying if the question of the captcha is correctly instantiated.
     */
    public void testGetQuestion() {
        assertNotNull(soundCaptcha.getQuestion());
    }

    /**
     * This test is for verifying if the challenge of the captcha is correctly instantiated.
     */
    public void testGetChallenge() {
        assertNotNull(soundCaptcha.getChallenge());
        assertTrue("Captcha challenge is not an AudioInputStream",
                soundCaptcha.getChallenge() instanceof AudioInputStream);
    }

    /**
     * This test is for verifying if the audio captcha are different stream but have the same content.
     */
    public void testGetAudioChallenge() throws Exception {
        Object challengeObject = soundCaptcha.getChallenge();
        assertEquals(AudioInputStream.class, challengeObject.getClass());
        AudioInputStream challengeAudioStream = (AudioInputStream) challengeObject;
        AudioInputStream soundChallengeAudioStream = soundCaptcha.getSoundChallenge();
        assertEquals(soundChallengeAudioStream.getFormat().toString(), challengeAudioStream.getFormat().toString());
        assertEquals(soundChallengeAudioStream.getFrameLength(), challengeAudioStream.getFrameLength());
    }
}