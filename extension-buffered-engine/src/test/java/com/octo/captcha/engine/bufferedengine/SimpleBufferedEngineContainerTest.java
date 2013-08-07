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
package com.octo.captcha.engine.bufferedengine;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.Locale;

/**
 * @author NIDWMAG
 */
public class SimpleBufferedEngineContainerTest extends BufferedEngineContainerTestAbstract {

    public void testExecute() throws Exception {
        Resource ressource = new ClassPathResource("testSimpleBufferedEngine.xml");
        ConfigurableBeanFactory bf = new XmlBeanFactory(ressource);
        BufferedEngineContainer container = (BufferedEngineContainer) bf.getBean("container");

        Thread.sleep(8000);
        for (int i = 0; i < 30; i++) {
            assertNotNull(container.getNextCaptcha(Locale.US));

        }

        Thread.sleep(4000);

        ((SimpleBufferedEngineContainer) container).stopDaemon();
    }

    /**
     * @see com.octo.captcha.engine.bufferedengine.BufferedEngineContainerTestAbstract#getEngine()
     */
    public BufferedEngineContainer getEngine() {
        Resource ressource = new ClassPathResource("testSimpleBufferedEngine.xml");
        ConfigurableBeanFactory bf = new XmlBeanFactory(ressource);
        BufferedEngineContainer container = (BufferedEngineContainer) bf.getBean("container");
        return container;
    }

    /**
     * @see com.octo.captcha.engine.bufferedengine.BufferedEngineContainerTestAbstract#releaseEngine(com.octo.captcha.engine.bufferedengine.BufferedEngineContainer)
     */
    public void releaseEngine(BufferedEngineContainer engine) {
        ((SimpleBufferedEngineContainer) engine).stopDaemon();
    }
}
