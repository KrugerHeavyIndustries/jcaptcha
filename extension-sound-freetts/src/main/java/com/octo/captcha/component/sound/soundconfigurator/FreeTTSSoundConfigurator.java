/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.component.sound.soundconfigurator;

import com.octo.captcha.CaptchaException;

/**
 * Implmenentation for a FreeTTS configuration
 *
 * @author Benoit Doumas
 * @version 1.0
 */
public class FreeTTSSoundConfigurator implements SoundConfigurator {
    String name;

    String location;

    float volume;

    float pitch;

    float rate;

    /**
     * Contructor for a FreeTTS configuration
     *
     * @param name     Name of the sound
     * @param location Package containing the sound defined by name
     * @param volume   Between 0 and 1.0
     * @param pitch    Level of the sound (hetz), between 50 and 250, normal 100
     * @param rate     Words per minute, between 1 and 999, normal 150
     */
    public FreeTTSSoundConfigurator(String name, String location, float volume, float pitch,
                                    float rate) {
        this.name = name;

        this.location = location;

        if ((volume <= 1.0) && (volume >= 0)) {
            this.volume = volume;
        } else {
            throw new CaptchaException("Volume is between 0 and 1.0");
        }

        if ((pitch <= 250) && (pitch >= 50)) {
            this.pitch = pitch;
        } else {
            throw new CaptchaException("Pitch is between 50 and 250");
        }

        if ((rate < 1000) && (rate > 0)) {
            this.rate = rate;
        } else {
            throw new CaptchaException("Rate is between 1 and 999");
        }

    }

    /**
     * @see com.octo.captcha.component.sound.soundconfigurator.SoundConfigurator#getVolume()
     */
    public float getVolume() {
        return this.volume;
    }

    /**
     * @see com.octo.captcha.component.sound.soundconfigurator.SoundConfigurator#getPitch()
     */
    public float getPitch() {
        return this.pitch;
    }

    /**
     * @see com.octo.captcha.component.sound.soundconfigurator.SoundConfigurator#getName()
     */
    public String getName() {
        return this.name;
    }

    /**
     * @see com.octo.captcha.component.sound.soundconfigurator.SoundConfigurator#getRate()
     */
    public float getRate() {
        return this.rate;
    }

    /**
     * @see com.octo.captcha.component.sound.soundconfigurator.SoundConfigurator#getLocation()
     */
    public String getLocation() {
        return this.location;
    }

}