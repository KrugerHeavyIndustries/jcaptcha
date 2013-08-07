/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.component.sound.soundconfigurator;

/**
 * Provide configuration of a sound
 *
 * @author Benoit Doumas
 * @version 1.0
 */
public interface SoundConfigurator {
    /**
     * @return the sound volume
     */
    float getVolume();

    /**
     * @return the sound level (hight or deep), pitch
     */
    float getPitch();

    /**
     * @return the sound name
     */
    String getName();

    /**
     * @return the sound speed rate
     */
    float getRate();

    /**
     * @return the location of the sound (package, jar, etc...)
     */
    String getLocation();
}
