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
package com.octo.captcha.engine.bufferedengine.manager;

import java.util.Locale;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.octo.captcha.CaptchaException;
import com.octo.captcha.engine.bufferedengine.BufferedEngineContainer;

/**
 * Unit test the QuartzBufferedEngineManager
 *
 * @author Benoit Doumas
 */
public class QuartzBufferedEngineManagerTest extends TestCase {
    private static final Log log = LogFactory.getLog(QuartzBufferedEngineManagerTest.class
            .getName());

    // loader init by default
    //protected Class loader = DefaultEngineLoadTestHelper.class;

    BufferedEngineContainer container = null;

    QuartzBufferedEngineManager manager;

    Object scheduler = null;

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        Resource ressource = new ClassPathResource("testQuartzBufferedEngine.xml");
        ConfigurableBeanFactory bf = new XmlBeanFactory(ressource);
        container = (BufferedEngineContainer) bf.getBean("container");
        scheduler = bf.getBean("quartz");

        manager = (QuartzBufferedEngineManager) bf.getBean("manager");
    }

    public void testStartStopToFeedPersistentBuffer() {
        String cronFeed = "0/3 * * * * ?";

        //there should be swap in order to have feeds (so persistent buffer is not full)
        String cronSwap = "0/2 * * * * ?";

        manager.setFeedSize(10);
        manager.setFeedCronExpr(cronFeed);

        manager.setSwapSize(10);
        manager.setSwapCronExpr(cronSwap);

        manager.stopToFeedPersistentBuffer();

        //get some captcha
        for (int i = 0; i < 10; i++)
            container.getNextCaptcha();

        int size = manager.getPersistentBufferSize();

        //wait to see if there is some feed
        try {
            Thread.sleep(3500);
        }
        catch (InterruptedException e) {
            throw new CaptchaException(e);
        }

        //is ti still the same?
        assertEquals(size, manager.getPersistentBufferSize());

        //take the time to have some swap
        try {
            Thread.sleep(3500);
        }
        catch (InterruptedException e) {
            throw new CaptchaException(e);
        }

        //stop to swap
        manager.stopToSwapFromPersistentToVolatileMemory();

        //now start
        manager.startToFeedPersistantBuffer();

        //wait, now shoult be some action
        try {
            Thread.sleep(3500);
        }
        catch (InterruptedException e) {
            throw new CaptchaException(e);
        }

        //the size shoult have increase
        assertTrue(size < manager.getPersistentBufferSize());

        manager.startToSwapFromPersistentToVolatileMemory();
    }


    public void testStartStopToSwapFromPersistentToVolatileMemory() {
        //there should be feeds in order to have swap (so persistent buffer contains some stuff)
        String cronFeed = "0/2 * * * * ?";


        String cronSwap = "0/3 * * * * ?";

        manager.setFeedSize(10);
        manager.setFeedCronExpr(cronFeed);

        manager.setSwapSize(10);
        manager.setSwapCronExpr(cronSwap);

        manager.stopToSwapFromPersistentToVolatileMemory();

        //get some captcha
        for (int i = 0; i < 10; i++)
            container.getNextCaptcha();

        int size = manager.getVolatileBufferSize();

        //wait to see if there is some swaps
        try {
            Thread.sleep(4000);
        }
        catch (InterruptedException e) {
            throw new CaptchaException(e);
        }

        //is ti still the same?
        assertEquals(size, manager.getVolatileBufferSize());

        //now start
        manager.startToSwapFromPersistentToVolatileMemory();

        //wait, now shoult be some action
        try {
            Thread.sleep(4000);
        }
        catch (InterruptedException e) {
            throw new CaptchaException(e);
        }

        //the size shoult have increase
        assertTrue(size < manager.getVolatileBufferSize());
    }

    public void testSetFeedCronExpr() {
        String cron = "0/23 * * * * ?";

        manager.setFeedCronExpr(cron);

        assertEquals(cron, manager.getFeedCronExpr());
    }

    public void testSetSwapCronExpr() {
        String cron = "0/23 * * * * ?";

        manager.setSwapCronExpr(cron);

        assertEquals(cron, manager.getSwapCronExpr());
    }

    public void testPauseResume() {
        manager.pause();
        manager.resume();
    }


    public void testSetFeedSize() {
        int size = 10;
        manager.setFeedSize(size);

        assertEquals(size, manager.getFeedSize());
    }

    public void testSetLocaleRatio() {
        manager.setLocaleRatio(Locale.GERMANY.toString(), 0.2);

        Map map = manager.getLocaleRatio();

        assertEquals(new Double(0.2), (Double) map.get(Locale.GERMANY));
    }

    public void testRemoveLocaleRatio() {
        manager.setLocaleRatio(Locale.GERMANY.getDisplayName(), 0.2);

        manager.removeLocaleRatio(Locale.GERMANY.getDisplayName());

        assertEquals(null, manager.getLocaleRatio().get(Locale.GERMANY.getDisplayName()));
    }

    public void testSetMaxPersistentMemorySize() {
        int size = 100;
        manager.setMaxPersistentMemorySize(size);

        assertEquals(size, manager.getMaxPersistentMemorySize());
    }

    public void testSetMaxVolatileMemorySize() {
        int size = 100;
        manager.setMaxVolatileMemorySize(size);

        assertEquals(size, manager.getMaxVolatileMemorySize());
    }

    public void testSetSwapSize() {
        int size = 10;
        manager.setSwapSize(size);

        assertEquals(size, manager.getSwapSize());
    }

    public void testClearVolatileBuffer() {
        manager.pause();

        manager.clearVolatileBuffer();

        assertEquals(0, manager.getVolatileBufferSize());

        manager.resume();
    }

    public void testClearPersistentBuffer() {
        manager.pause();

        manager.clearPersistentBuffer();

        assertEquals(0, manager.getPersistentBufferSize());

        manager.resume();
    }

}
