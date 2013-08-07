/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.sound;

import com.octo.captcha.Captcha;
import com.octo.captcha.CaptchaException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;

/**
 * <p/>
 * Description: String question about a Line challenge, this class is abstract. </p>
 *
 * @author <a href="mailto:mga@octo.com">Mathieu Gandin </a>
 * @author Benoit Doumas
 * @author Richard Hull
 * @version 1.1
 */
public abstract class SoundCaptcha implements Captcha {

    protected Boolean hasChallengeBeenCalled = Boolean.FALSE;

    protected String question;

    protected byte[] challenge;

    protected SoundCaptcha(String thequestion, AudioInputStream thechallenge) {
        this.question = thequestion;

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            AudioSystem.write(thechallenge, AudioFileFormat.Type.WAVE, out);
            this.challenge = out.toByteArray();
        } catch (IOException ioe) {
            throw new CaptchaException("unable to serialize input stream", ioe);
        }
    }

    /**
     * Accessor to the question.
     */
    public final String getQuestion() {
        return this.question;
    }

    /**
     * Accessor to the challenge.
     */
    public final Object getChallenge() {
        return this.getSoundChallenge();
    }

    /**
     * Accessor to the sound challenge. Create a new stream each time the method is called.
     *
     * @return an AudioInputStream
     */
    public final AudioInputStream getSoundChallenge() {

        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new ByteArrayInputStream(this.challenge));
            hasChallengeBeenCalled = Boolean.TRUE;
            return audioStream;
        } catch (UnsupportedAudioFileException e) {
            throw new CaptchaException("unable to deserialize input stream", e);
        } catch (IOException e) {
            throw new CaptchaException("unable to deserialize input stream", e);
        }
    }

    /*
     * public Boolean validateResponse(Object response) { return null; }
     */

    /**
     * this method is to clean the challenge.
     */
    public void disposeChallenge() {
        this.challenge = null;
    }

    public Boolean hasGetChalengeBeenCalled() {
        return hasChallengeBeenCalled;
    }
}