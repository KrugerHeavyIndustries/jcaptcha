/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.component.sound.wordtosound;

import com.octo.captcha.CaptchaException;
import com.octo.captcha.component.sound.soundconfigurator.FreeTTSSoundConfigurator;
import com.octo.captcha.component.sound.soundconfigurator.SoundConfigurator;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import com.sun.speech.freetts.audio.AudioPlayer;
import com.sun.speech.freetts.util.Utilities;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.Locale;
import java.util.Vector;

/**
 * WordToSound implementation with FreeTTS an openSource Text To Speech implementation.
 *
 * @author Benoit
 * @version 1.0
 */
public abstract class AbstractFreeTTSWordToSound implements WordToSound {
    public static String defaultVoiceName = "kevin16";

    public static String defaultVoicePackage = "com.sun.speech.freetts.en.us.cmu_time_awb.AlanVoiceDirectory,com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory";

    private static String FREETTS_PROPERTIES_KEY = "freetts.voices";

    private int maxAcceptedWordLength;

    private int minAcceptedWordLength;

    private SoundConfigurator configurator = null;

    private Voice voice = null;

    /**
     * default Voice, allocated at instanciation
     */
    private Voice defaultVoice = null;

    private Locale locale = null;

    private VoiceManager voiceManager = null;

    private boolean isInitiated = false;

    /**
     * Constructor
     *
     * @deprecated
     */
    public AbstractFreeTTSWordToSound() {
        configurator = new FreeTTSSoundConfigurator(AbstractFreeTTSWordToSound.defaultVoiceName,
                AbstractFreeTTSWordToSound.defaultVoicePackage, 1.0f, 100, 100);

        minAcceptedWordLength = 4;
        maxAcceptedWordLength = 6;
        init();
    }

    /**
     * Constructor for a FreeTTS implmentation of WordToSound. This constructor imply that WordToSound only use one
     * voice define by voiceName, with its own locale
     *
     * @param configurator          Voice configuration
     * @param minAcceptedWordLength Length Minimal of generated words
     * @param maxAcceptedWordLength Length Maximal of generated words
     */
    public AbstractFreeTTSWordToSound(SoundConfigurator configurator, int minAcceptedWordLength,
                                      int maxAcceptedWordLength) {
        this.configurator = configurator;
        this.minAcceptedWordLength = minAcceptedWordLength;
        this.maxAcceptedWordLength = maxAcceptedWordLength;
        init();
    }

    /**
     * @see com.octo.captcha.component.sound.wordtosound.WordToSound#getSound(java.lang.String)
     */
    public AudioInputStream getSound(String word) throws CaptchaException {
        //return a sound generated with the default voice.
        voice = defaultVoice;
        return addEffects(stringToSound(word));
    }

    /**
     * @see WordToSound#getSound(String, java.util.Locale)
     */
    public AudioInputStream getSound(String word, Locale locale) throws CaptchaException {

        Voice[] voices = voiceManager.getVoices();
        Voice selectedVoice = null;

        //if the default voice is corresponding
        if (defaultVoice.getLocale().equals(locale)) {
            voice = defaultVoice;
        } else {
            //try to find a voice corresponding to the locale
            for (int i = 0; i < voices.length; i++) {
                if (voices[i].getLocale().equals(locale)) {
                    selectedVoice = voices[i];
                }
            }

            if (selectedVoice != null) {
                selectedVoice.allocate();
                voice = selectedVoice;
                configureVoice(voice);
            } else {
                throw new CaptchaException("No voice corresponding to the Locale");
            }
        }

        return addEffects(stringToSound(word));
    }

    public int getMaxAcceptedWordLength() {
        return maxAcceptedWordLength;
    }

    public int getMinAcceptedWordLength() {
        return minAcceptedWordLength;
    }

    /**
     * @return the max word lenght accepted by this word2image service
     * @deprecated misspelled, use {@link #getMaxAcceptedWordLength()} instead
     */
    public int getMaxAcceptedWordLenght() {
        return maxAcceptedWordLength;
    }

    /**
     * @return the min word lenght accepted by this word2image service
     * @deprecated misspelled, use {@link #getMinAcceptedWordLength()} instead
     */
    public int getMinAcceptedWordLenght() {
        return minAcceptedWordLength;
    }

    protected abstract AudioInputStream addEffects(AudioInputStream sound);

    /**
     * Method to initialise FreeTTS
     */
    private void init() {
        if (!isInitiated) {
            //Voices use by freeTTS, we define where they are, currently in the java en_us.jar
            //add the package
            addToSystemesPropetites(this.configurator.getLocation());

            // The VoiceManager manages all the voices for FreeTTS.
            voiceManager = VoiceManager.getInstance();

            this.defaultVoice = voiceManager.getVoice(this.configurator.getName());

            configureVoice(this.defaultVoice);

            // Allocates the resources for the voice.
            this.defaultVoice.allocate();
            isInitiated = true;
        }
    }

    /**
     * Add the package to the system properties, will be used by FreeTTS to find all data for voices.
     */
    private static void addToSystemesPropetites(String soundPackage) {
        //get the prop, if not exist inti, else add to the prop
        String packages = System.getProperty(FREETTS_PROPERTIES_KEY);
        if (packages == null) {
            packages = soundPackage;
        } else if (packages.indexOf(soundPackage) == -1) {
            packages += "," + soundPackage;
        }

        System.getProperties().put(FREETTS_PROPERTIES_KEY, packages);
    }

    /**
     * Configue the voice with the SoundConfigurator
     */
    private void configureVoice(Voice voice) {
        voice.setPitch(configurator.getPitch());
        voice.setVolume(configurator.getVolume());
        voice.setRate(configurator.getRate());
    }

    /**
     * Main method for this service Return an image with the specified. Synchronisation is very important, for multi
     * threading execution
     *
     * @param sentence Written sentece to transform into speech
     *
     * @return the generated sound
     *
     * @throws com.octo.captcha.CaptchaException
     *          if word is invalid or an exception occurs during the sound generation
     */
    public synchronized AudioInputStream stringToSound(String sentence) throws CaptchaException {
        //use the custom (see inner class) InputStreamAudioPlayer, which provide interface to
        // Audio Stream
        InputStreamAudioPlayer audioPlayer = new InputStreamAudioPlayer();

        this.voice.setAudioPlayer(audioPlayer);

        // Synthesize speech.
        this.voice.speak(sentence);

        AudioInputStream ais = audioPlayer.getAudioInputStream();
        return ais;
    }

    /**
     * Implementation of freeTTS AudioPlayer interface, to produce an audioInputStream, this is not a very clean way
     * since it doesn't really play. But it is the only way to get a stream easily
     */
    private class InputStreamAudioPlayer implements AudioPlayer {
        private boolean debug = false;

        private AudioFormat currentFormat = null;

        private byte[] outputData;

        private int curIndex = 0;

        private int totBytes = 0;

        private Vector outputList;

        private AudioInputStream audioInputStream;

        /**
         * Constructs a InputStreamAudioPlayer
         *
         */
        public InputStreamAudioPlayer() {
            debug = Utilities.getBoolean("com.sun.speech.freetts.audio.AudioPlayer.debug");
            outputList = new Vector();
        }

        /**
         * Sets the audio format for this player
         *
         * @param format the audio format
         *
         * @throws UnsupportedOperationException if the line cannot be opened with the given format
         */
        public synchronized void setAudioFormat(AudioFormat format) {
            currentFormat = format;
        }

        /**
         * Gets the audio format for this player
         *
         * @return format the audio format
         */
        public AudioFormat getAudioFormat() {
            return currentFormat;
        }

        /**
         * Pauses audio output
         */
        public void pause() {
        }

        /**
         * Resumes audio output
         */
        public synchronized void resume() {
        }

        /**
         * Cancels currently playing audio
         */
        public synchronized void cancel() {
        }

        /**
         * Prepares for another batch of output. Larger groups of output (such as all output associated with a single
         * FreeTTSSpeakable) should be grouped between a reset/drain pair.
         */
        public synchronized void reset() {
        }

        /**
         * Starts the first sample timer
         */
        public void startFirstSampleTimer() {
        }

        /**
         * Closes this audio player
         */
        public synchronized void close() {
            try {
                audioInputStream.close();
            } catch (IOException ioe) {
                System.err.println("Problem while closing the audioInputSteam");
            }

        }

        public AudioInputStream getAudioInputStream() {
            InputStream tInputStream = new SequenceInputStream(outputList.elements());
            AudioInputStream tAudioInputStream = new AudioInputStream(tInputStream, currentFormat,
                    totBytes / currentFormat.getFrameSize());

            return tAudioInputStream;
        }

        /**
         * Returns the current volume.
         *
         * @return the current volume (between 0 and 1)
         */
        public float getVolume() {
            return 1.0f;
        }

        /**
         * Sets the current volume.
         *
         * @param volume the current volume (between 0 and 1)
         */
        public void setVolume(float volume) {
        }

        /**
         * Starts the output of a set of data. Audio data for a single utterance should be grouped between begin/end
         * pairs.
         *
         * @param size the size of data between now and the end
         */
        public void begin(int size) {
            outputData = new byte[size];
            curIndex = 0;
        }

        /**
         * Marks the end of a set of data. Audio data for a single utterance should be groupd between begin/end pairs.
         *
         * @return true if the audio was output properly, false if the output was cancelled or interrupted.
         */
        public boolean end() {
            outputList.add(new ByteArrayInputStream(outputData));
            totBytes += outputData.length;
            return true;
        }

        /**
         * Waits for all queued audio to be played
         *
         * @return true if the audio played to completion, false if the audio was stopped
         */
        public boolean drain() {
            return true;
        }

        /**
         * Gets the amount of played since the last mark
         *
         * @return the amount of audio in milliseconds
         */
        public synchronized long getTime() {
            return -1L;
        }

        /**
         * Resets the audio clock
         */
        public synchronized void resetTime() {
        }

        /**
         * Writes the given bytes to the audio stream
         *
         * @param audioData audio data to write to the device
         *
         * @return <code>true</code> of the write completed successfully, <code> false </code> if the write was
         *         cancelled.
         */
        public boolean write(byte[] audioData) {
            return write(audioData, 0, audioData.length);
        }

        /**
         * Writes the given bytes to the audio stream
         *
         * @param bytes  audio data to write to the device
         * @param offset the offset into the buffer
         * @param size   the size into the buffer
         *
         * @return <code>true</code> of the write completed successfully, <code> false </code> if the write was
         *         cancelled.
         */
        public boolean write(byte[] bytes, int offset, int size) {
            System.arraycopy(bytes, offset, outputData, curIndex, size);
            curIndex += size;
            return true;
        }

        /**
         * Waits for resume. If this audio player is paused waits for the player to be resumed. Returns if resumed,
         * cancelled or shutdown.
         *
         * @return true if the output has been resumed, false if the output has been cancelled or shutdown.
         */
        private synchronized boolean waitResume() {
            return true;
        }

        /**
         * Returns the name of this audioplayer
         *
         * @return the name of the audio player
         */
        public String toString() {
            return "AudioInputStreamAudioPlayer";
        }

        /**
         * Outputs a debug message if debugging is turned on
         *
         * @param msg the message to output
         */
        private void debugPrint(String msg) {
            if (debug) {
                System.out.println(toString() + ": " + msg);
            }
        }

        /**
         * Shows metrics for this audio player
         */
        public void showMetrics() {
        }
    }

}

