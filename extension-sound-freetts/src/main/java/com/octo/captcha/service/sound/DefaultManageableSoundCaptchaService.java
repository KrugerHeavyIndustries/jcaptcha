/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.service.sound;

import com.octo.captcha.engine.CaptchaEngine;
import com.octo.captcha.engine.sound.speller.SpellerSoundCaptchaEngine;
import com.octo.captcha.service.captchastore.CaptchaStore;
import com.octo.captcha.service.captchastore.FastHashMapCaptchaStore;

/**
 * <p>Default service implementation : use a {@link FastHashMapCaptchaStore} as captcha store, and a {@link SpellerSoundCaptchaEngine}</p> It is initialized
 * with thoses default values : <ul> <li>min guaranted delay : 180s </li> <li>max store size : 100000 captchas </li>
 * <li>max store size before garbage collection : 75000 </li> </ul>
 *
 * @author <a href="mailto:marc.antoine.garrigue@gmail.com">Marc-Antoine Garrigue</a>
 * @version $Id: DefaultManageableSoundCaptchaService.java 388 2007-04-20 15:38:24Z antoineveret $
 */
public class DefaultManageableSoundCaptchaService extends AbstractManageableSoundCaptchaService implements SoundCaptchaService {

    /**
     * Construct a new SoundCaptchaService with a {@link FastHashMapCaptchaStore} and a {@link SpellerSoundCaptchaEngine}
     *
     * @param minGuarantedStorageDelayInSeconds
     *
     * @param maxCaptchaStoreSize
     * @param captchaStoreLoadBeforeGarbageCollection
     *
     */
    public DefaultManageableSoundCaptchaService(int minGuarantedStorageDelayInSeconds, int maxCaptchaStoreSize, int captchaStoreLoadBeforeGarbageCollection) {
        super(new FastHashMapCaptchaStore(), new SpellerSoundCaptchaEngine(), minGuarantedStorageDelayInSeconds, maxCaptchaStoreSize, captchaStoreLoadBeforeGarbageCollection);
    }


    /**
     * Construct a new SoundCaptchaService with a {@link FastHashMapCaptchaStore} and a {@link SpellerSoundCaptchaEngine}
     * minGuarantedStorageDelayInSeconds 180s
     * maxCaptchaStoreSize 100000
     * captchaStoreLoadBeforeGarbageCollection 75000
     */
    public DefaultManageableSoundCaptchaService() {
        this(180, 100000, 75000);
    }


    public DefaultManageableSoundCaptchaService(CaptchaStore captchaStore, CaptchaEngine captchaEngine, int minGuarantedStorageDelayInSeconds, int maxCaptchaStoreSize, int captchaStoreLoadBeforeGarbageCollection) {
        super(captchaStore, captchaEngine, minGuarantedStorageDelayInSeconds, maxCaptchaStoreSize, captchaStoreLoadBeforeGarbageCollection);
    }
}
