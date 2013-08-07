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

package com.octo.captcha.engine.bufferedengine;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import EDU.oswego.cs.dl.util.concurrent.ClockDaemon;

import com.octo.captcha.engine.CaptchaEngine;
import com.octo.captcha.engine.bufferedengine.buffer.CaptchaBuffer;

/**
 * Simple implmentation of the BufferedEngineContainer with ClockDaemon
 *
 * @author Benoit Doumas
 * @author marc-antoine garrigue
 */
public class SimpleBufferedEngineContainer extends BufferedEngineContainer {
    private static final Log log = LogFactory.getLog(SimpleBufferedEngineContainer.class.getName());

    private ClockDaemon clockDaemonFeed;
    private ClockDaemon clockDaemonSwap;


    private Long feedPeriod = new Long(10000);

    private Long swapPeriod = new Long(1000);

    //protected BeanFactory factory = null;


    public SimpleBufferedEngineContainer(CaptchaEngine engine, CaptchaBuffer memoryBuffer,
                                         CaptchaBuffer diskBuffer, ContainerConfiguration containerConfiguration, int feedPeriod,
                                         int swapPeriod) {
        super(engine, memoryBuffer, diskBuffer, containerConfiguration);

        this.swapPeriod = new Long(swapPeriod);
        this.feedPeriod = new Long(feedPeriod);
        startScheduler();
    }

    /**
     * @see com.octo.captcha.engine.bufferedengine.BufferedEngineContainer#startScheduler()
     */
    protected void startScheduler() {
        clockDaemonFeed = new ClockDaemon();
        clockDaemonSwap = new ClockDaemon();
        log.debug("daemons initialized");
        startDaemon();

    }

    protected void stopDaemon() {
        this.clockDaemonFeed.shutDown();
        this.clockDaemonSwap.shutDown();
    }

    protected void startDaemon() {
        clockDaemonFeed.executePeriodically(feedPeriod.longValue(), new SimpleDiskFeeder(), true);


        clockDaemonSwap.executePeriodically(swapPeriod.longValue(), new SimpleDiskToMemory(), true);

    }

    public class SimpleDiskFeeder implements Runnable {

        public void feedDisk() {
            feedPersistentBuffer();
        }

        public void run() {
            this.feedDisk();
        }

    }

    public class SimpleDiskToMemory implements Runnable {


        public void diskToMemory() {
            swapCaptchasFromPersistentToVolatileMemory();
        }

        public void run() {
            this.diskToMemory();
        }

    }

}
