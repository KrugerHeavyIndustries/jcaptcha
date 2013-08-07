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
package com.octo.captcha.engine.bufferedengine.buffer;

import java.util.ArrayList;
import java.util.Collection;

public class DiskCaptchaBufferTest extends CaptchaBufferTestAbstract {

    protected String dirName = System.getProperty("java.io.tmpdir") + "captchaBuffer";

    /**
     * @see com.octo.captcha.engine.bufferedengine.buffer.CaptchaBufferTestAbstract#getBuffer()
     */
    public CaptchaBuffer getBuffer() {
        return new DiskCaptchaBuffer(dirName, false);
    }

    /**
     * Special test for the disk buffer, which can load captchas from disk.
     */
    public void testPesistant() throws Exception {

        this.buffer = getBuffer();
        ArrayList listToStore = new ArrayList(1);

        for (int i = 0; i < SIZE; ++i) {
            listToStore.add(engine.getNextCaptcha());
        }
        this.buffer.putAllCaptcha(listToStore);

        this.buffer.dispose();

        DiskCaptchaBuffer diskStore2 = new DiskCaptchaBuffer(dirName, true);

        Collection c2 = diskStore2.removeCaptcha(5);
        assertEquals(5, c2.size());
    }

}
