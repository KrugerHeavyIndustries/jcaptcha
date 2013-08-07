/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.image.gimpy;

import com.octo.captcha.image.ImageCaptcha;

import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * <p>A Gimpy is an ImageCaptcha. It is also the most common captcha.</p> <ul> <li>Challenge type : image</li>
 * <li>Response type : String</li> <li>Description : An image of a distorded word is shown. User have to recognize the
 * word and to submit it.</li> </ul>
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public class Gimpy extends ImageCaptcha implements Serializable {

	private String response;

    Gimpy(String question, BufferedImage challenge, String response) {
        super(question, challenge);
        this.response = response;
    }

    /**
     * Validation routine from the CAPTCHA interface. this methods verify if the response is not null and a String and
     * then compares the given response to the internal string.
     *
     * @return true if the given response equals the internal response, false otherwise.
     */
    public final Boolean validateResponse(final Object response) {
        return (null != response && response instanceof String)
                ? validateResponse((String) response) : Boolean.FALSE;
    }

    /**
     * Very simple validation routine that compares the given response to the internal string.
     *
     * @return true if the given response equals the internal response, false otherwise.
     */
    private final Boolean validateResponse(final String response) {
        return Boolean.valueOf(response.equals(this.response));
    }

}
