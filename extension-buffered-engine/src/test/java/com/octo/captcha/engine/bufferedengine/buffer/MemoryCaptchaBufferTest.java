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

import java.util.NoSuchElementException;

import org.apache.commons.collections.buffer.UnboundedFifoBuffer;


public class MemoryCaptchaBufferTest extends CaptchaBufferTestAbstract {

    /**
     * @see com.octo.captcha.engine.bufferedengine.buffer.CaptchaBufferTestAbstract#getBuffer()
     */
    public CaptchaBuffer getBuffer() {
        return new MemoryCaptchaBuffer();
    }

    public void testRemoveEmptyBuffer() {
    	UnboundedFifoBuffer fifoBuffer = new UnboundedFifoBuffer();
    	try {
			fifoBuffer.remove();
			fail("should throw an Exception");
		} catch (NoSuchElementException e) {
		}
    }
}
