/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.sound.speller;

import com.octo.captcha.sound.SoundCaptcha;

import javax.sound.sampled.AudioInputStream;

/**
 * <p>SoundCaptcha with spelling</p>
 *
 * @author Benoit Doumas
 * @version 1.0
 */
public class SpellerSound extends SoundCaptcha {

	private String response;

    public SpellerSound(String thequestion,
                        AudioInputStream thechallenge, String theresponse) {
        super(thequestion, thechallenge);
        this.response = theresponse;
    }

    public Boolean validateResponse(Object theresponse) {
        if ((theresponse != null) && (theresponse instanceof String)) {
            return this.validateResponse((String) theresponse);
        } else {
            return Boolean.FALSE;
        }
    }

    public Boolean validateResponse(String theresponse) {
        return Boolean.valueOf(this.response.equalsIgnoreCase(theresponse));
    }

}
