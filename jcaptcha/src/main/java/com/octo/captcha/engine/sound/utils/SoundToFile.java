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
package com.octo.captcha.engine.sound.utils;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.io.IOException;

/**
 * <p/>
 * Create a wave file from an AudioInputStream </p>
 *
 * @author Benoit
 * @version 1.0
 */
public class SoundToFile {
    /**
     * Create a wave file from an AudioInputStream
     *
     * @param pAudioInputStream Audio Steam to serialize
     * @param pFile             File to write to audio stream
     */
    public static void serialize(AudioInputStream pAudioInputStream, File pFile) throws IOException {
        pFile.createNewFile();
        AudioSystem.write(pAudioInputStream, AudioFileFormat.Type.WAVE, pFile);
    }
}