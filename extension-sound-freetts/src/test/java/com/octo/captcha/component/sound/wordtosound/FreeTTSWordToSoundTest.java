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
package com.octo.captcha.component.sound.wordtosound;

import junit.framework.TestCase;
import com.octo.captcha.component.sound.soundconfigurator.FreeTTSSoundConfigurator;

/**
 * <p/>
 * Description: </p>
 *
 * @author Benoit Doumas
 * @version 1.0
 */
public class FreeTTSWordToSoundTest extends TestCase {

    private FreeTTSWordToSound cleanFreeTTSwordToSound;

    private String voiceName = "kevin16";

    private String voicePackage = "com.sun.speech.freetts.en.us.cmu_time_awb.AlanVoiceDirectory,com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory";

    /**
     * Constructor for SimpleWordToImageTest.
     */
    public FreeTTSWordToSoundTest(String name) {
        super(name);
    }

    public void setUp() {
        this.cleanFreeTTSwordToSound = new FreeTTSWordToSound(new FreeTTSSoundConfigurator(voiceName, voicePackage, 1.0f, 100, 100), 3, 6);

    }

    public void testConfiguration() {
        this.cleanFreeTTSwordToSound.getSound("test");
    }


    public void testGetMaxAcceptedWordLength() {
        assertEquals(this.cleanFreeTTSwordToSound.getMaxAcceptedWordLength(), 6);
    }

    public void testGetMinAcceptedWordLength() {
        assertEquals(this.cleanFreeTTSwordToSound.getMinAcceptedWordLength(), 3);
    }
}
