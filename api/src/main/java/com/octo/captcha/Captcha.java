/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha;

import java.io.Serializable;

/**
 * This interface represent a CAPTCHA.
 * <p/>
 * A CAPTCHA is a program that can generate and grade tests that: <ul> <li>Most humans can pass.</li> <li>Current
 * computer programs can't pass</li> </ul> see http://www.captcha.net/ for sample, articles, and definitions.
 * <p/>
 * A captcha is basically a test composed of : <ul> <li>A question about :</li> <li>a challenge (can be an image for
 * image captchas or a sound, or wathever)</li> <li>a validation routine a of a given response</li> </ul>
 * <p/>
 * This is a container for the CAPTCHA challenge which is also able to validate the answer. Class implementing this
 * interface must follow the rules : <ul> <li>As all 'components' of this project, all contructed objects should be
 * abble to word (ie no need to set other properties to obtain a fully fonctional object). constructor</li> <li>It must
 * not build the challenge! use instead the CaptchaFactory</li> <li>It must know how to validate the answer</li> <li>It
 * must not expose the answer</li> <li>It must dispose the challenge when the getChallenge method is called(The
 * challenge must be showed only once ) </li> </ul> ;
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public interface Captcha extends Serializable {

    /**
     * Accessor captcha question.
     *
     * @return the question
     */
    String getQuestion();

    /**
     * Accerssor for the questionned challenge.
     *
     * @return the challenge (may be an image for image captcha...
     */
    Object getChallenge();

    /**
     * Validation routine for the response.
     *
     * @param response to the question concerning the chalenge
     *
     * @return true if the answer is correct, false otherwise.
     */
    Boolean validateResponse(Object response);

    /**
     * Dispose the challenge, once this method is call the getChallenge method will return null.<br> It has been added
     * for technical reasons : a captcha is always used in a two step fashion<br> First submit the challenge, and then
     * wait until the response arrives.<br> It had been asked to have a method to dispose the challenge that is no
     * longer used after being dipslayed. So here it is!
     */
    void disposeChallenge();

    /**
     * This method should return true if the getChalenge method has been called (has been added in order to properly
     * manage the captcha state.
     *
     * @return true if getChallenge has been called false otherwise.
     */
    Boolean hasGetChalengeBeenCalled();

}
