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

import com.octo.captcha.Captcha;
import com.octo.captcha.CaptchaException;
import com.octo.captcha.CaptchaFactory;
import com.octo.captcha.engine.CaptchaEngine;
import com.octo.captcha.engine.CaptchaEngineException;
import com.octo.captcha.engine.bufferedengine.buffer.CaptchaBuffer;
import org.apache.commons.collections.MapIterator;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

/**
 * Abstact class that encapsulate a CaptchaEngine to allow buffering. A BufferedEngineContainer has mainly one function
 * : to provide cached captchas to increase performances. This is done through two embedded buffers : a disk buffer and
 * a memory buffer. When captchas are requested, the bufferedEngine take them either from the memory buffer if not empty
 * or directly from the engine. Some good periods are defined with a scheduler to feed the disk buffer with captchas and
 * some others to swap captchas from the disk buffer to the memory buffer.
 *
 * @author Benoit Doumas
 */
public abstract class BufferedEngineContainer implements CaptchaEngine {

    private static final Log log = LogFactory.getLog(BufferedEngineContainer.class.getName());

    protected CaptchaBuffer persistentBuffer = null;

    protected CaptchaBuffer volatileBuffer = null;

    protected CaptchaEngine engine = null;

    protected ContainerConfiguration config = null;

    protected int volatileMemoryHits = 0;

    protected int persistentMemoryHits = 0;

    protected int persistentToVolatileSwaps = 0;

    protected int persistentFeedings = 0;

    private boolean shutdownCalled = false;


    /**
     * Construct an BufferedEngineContainer with and Captcha engine, a memory buffer, a diskBuffer and a
     * ContainerConfiguration.
     *
     * @param engine                 engine to generate captcha for buffers
     * @param volatileBuffer         the memory buffer, which store captcha and provide a fast access to them
     * @param persistentBuffer       the disk buffer which store captchas not in a volatil and memory consuming way
     * @param containerConfiguration the container configuration
     */
    public BufferedEngineContainer(CaptchaEngine engine, CaptchaBuffer volatileBuffer,
                                   CaptchaBuffer persistentBuffer, ContainerConfiguration containerConfiguration) {
        this.engine = engine;
        if (engine == null) {
            throw new CaptchaEngineException("impossible to build a BufferedEngineContainer with a null engine");
        }
        this.volatileBuffer = volatileBuffer;
        if (persistentBuffer == null) {
            throw new CaptchaEngineException("impossible to build a BufferedEngineContainer with a null volatileBuffer");
        }
        this.persistentBuffer = persistentBuffer;
        if (persistentBuffer == null) {
            throw new CaptchaEngineException("impossible to build a BufferedEngineContainer with a null persistentBuffer");
        }
        this.config = containerConfiguration;
        if (config == null) {
            throw new CaptchaEngineException("impossible to build a BufferedEngineContainer with a null configuration");
        }
        //define hook when JVM is shutdown
        Shutdown sh = new Shutdown();
        Runtime.getRuntime().addShutdownHook(sh);
    }


    /**
     * @see com.octo.captcha.engine.CaptchaEngine#getNextCaptcha()
     */
    public Captcha getNextCaptcha() {
        log.debug("entering getNextCaptcha()");
        return getNextCaptcha(config.getDefaultLocale());
    }

    /**
     * @see com.octo.captcha.engine.CaptchaEngine#getNextCaptcha(java.util.Locale)
     */
    public Captcha getNextCaptcha(Locale locale) {
        log.debug("entering getNextCaptcha(Locale locale)");
        Captcha captcha = null;
        locale = resolveLocale(locale);
        try {
            captcha = volatileBuffer.removeCaptcha(locale);
        } catch (NoSuchElementException e) {
            log.debug("no captcha under this locale", e);
        }


        if (captcha == null) {
            //get from engine directly
            captcha = engine.getNextCaptcha(locale);
            log.debug("get captcha from engine");

            if (config.isServeOnlyConfiguredLocales()) {
                log.warn("captcha is directly built from engine, try to increase the swap frequency or the persistant buffer size");
            }
        } else {
            log.debug("get captcha from memory");
            //stats
            volatileMemoryHits++;
        }
        return captcha;
    }


    /**
     * @return captcha factories used by this engine
     */
    public CaptchaFactory[] getFactories() {
        return this.engine.getFactories();
    }

    /**
     * @param factories new captcha factories for this engine
     */
    public void setFactories(CaptchaFactory[] factories) {
        this.engine.setFactories(factories);
    }


    /**
     * Helper for locale
     */
    private Locale resolveLocale(Locale locale) {
        if (!config.isServeOnlyConfiguredLocales()) {

            return locale;
        } else {
            if (this.config.getLocaleRatio().containsKey(locale)) {
                return locale;
                //try to resolve by language
            } else if (this.config.getLocaleRatio().containsKey(locale.getLanguage())) {
                return new Locale(locale.getLanguage());
            } else {
                return config.getDefaultLocale();
            }
        }

    }


    /**
     * Method launch by a scheduler to swap captcha from disk buffer to the memory buffer. The ratio of swaping for each
     * locale is defined in the configuration component.
     */
    public void swapCaptchasFromPersistentToVolatileMemory() {

        log.debug("entering swapCaptchasFromDiskBufferToMemoryBuffer()");

        MapIterator it = config.getLocaleRatio().mapIterator();

        //construct the map of swap size by locales;
        HashedMap captchasRatios = new HashedMap();
        while (it.hasNext()) {

            Locale locale = (Locale) it.next();
            double ratio = ((Double) it.getValue()).doubleValue();
            int ratioCount = (int) Math.round(config.getSwapSize().intValue() * ratio);

            //get the reminding size corresponding to the ratio
            int diff = (int) Math.round((config.getMaxVolatileMemorySize().intValue() - this.volatileBuffer
                    .size()) * ratio);

            diff = diff < 0 ? 0 : diff;
            int toSwap = (diff < ratioCount) ? diff : ratioCount;

            captchasRatios.put(locale, new Integer(toSwap));
        }
        //get them from persistent buffer
        Iterator captchasRatiosit = captchasRatios.mapIterator();

        while (captchasRatiosit.hasNext() && !shutdownCalled) {
            Locale locale = (Locale) captchasRatiosit.next();
            int swap = ((Integer) captchasRatios.get(locale)).intValue();
            if (log.isDebugEnabled()) {
                log.debug("try to swap  " + swap + " Captchas from persistent to volatile memory with locale : "
                        + locale.toString());
            }

            Collection temp = this.persistentBuffer.removeCaptcha(swap, locale);

            this.volatileBuffer.putAllCaptcha(temp, locale);
            if (log.isDebugEnabled()) {
                log.debug("swaped  " + temp.size() + " Captchas from persistent to volatile memory with locale : "
                        + locale.toString());
            }
            //stats
            persistentMemoryHits += temp.size();
        }

        if (log.isDebugEnabled()) {
            log.debug("new volatil Buffer size : " + volatileBuffer.size());
        }
        // stats
        persistentToVolatileSwaps++;
    }


    /**
     * Method launch by a scheduler to feed the disk buffer with captcha. The ratio of feeding for each locale is
     * defined in the configuration component.
     */
    public void feedPersistentBuffer() {

        log.debug("entering feedPersistentBuffer()");
        //evaluate the total feed size
        int freePersistentBufferSize = config.getMaxPersistentMemorySize().intValue() - persistentBuffer.size();
        freePersistentBufferSize = freePersistentBufferSize > 0 ? freePersistentBufferSize : 0;
        int totalFeedsize = freePersistentBufferSize > config.getFeedSize().intValue() ? config.getFeedSize().intValue() : freePersistentBufferSize;

        log.info("Starting feed. Total feed size = " + totalFeedsize);

        //feed the buffer for each locale
        MapIterator it = config.getLocaleRatio().mapIterator();
        while (it.hasNext() && !shutdownCalled) {
            Locale locale = (Locale) it.next();
            double ratio = ((Double) it.getValue()).doubleValue();
            int ratioCount = (int) Math.round(totalFeedsize * ratio);


            if (log.isDebugEnabled()) {
                log.debug("construct " + ratioCount + " captchas for locale " + locale.toString());
            }
            //batch build and store captchas
            int toBuild = ratioCount;
            while (toBuild > 0 && !shutdownCalled) {
                int batch = toBuild > config.getFeedBatchSize().intValue() ? config.getFeedBatchSize().intValue() : toBuild;
                ArrayList captchas = new ArrayList(batch);
                //build captchas, batch sized
                int builded = 0;
                for (int i = 0; i < batch; i++) {
                    try {
                        captchas.add(engine.getNextCaptcha(locale));
                        builded++;
                    } catch (CaptchaException e) {
                        log.warn("Error during captcha construction, skip this one : ", e);
                    }
                }
                //persist
                persistentBuffer.putAllCaptcha(captchas, locale);
                if (log.isInfoEnabled()) {
                    log.info("feeded persistent buffer with  " + builded + " captchas for locale " + locale);
                }
                toBuild -= builded;
            }

        }
        log.info("Stopping feed : feeded persitentBuffer with : " + totalFeedsize + " captchas");
        log.info("Stopping feed : resulting persitentBuffer size : " + persistentBuffer.size());
        persistentFeedings++;
    }

    public ContainerConfiguration getConfig() {
        return config;
    }

    public CaptchaBuffer getPersistentBuffer() {
        return persistentBuffer;
    }

    public Integer getPersistentFeedings() {
        return new Integer(persistentFeedings);
    }

    public Integer getPersistentMemoryHits() {
        return new Integer(persistentMemoryHits);
    }

    public Integer getPersistentToVolatileSwaps() {
        return new Integer(persistentToVolatileSwaps);
    }

    public CaptchaBuffer getVolatileBuffer() {
        return volatileBuffer;
    }

    public Integer getVolatileMemoryHits() {
        return new Integer(volatileMemoryHits);
    }

    class Shutdown extends Thread {
        public Shutdown() {
            super();
        }

        public void run() {
            log.info("Buffered engine shutdown thread started");
            shutdownCalled = true;
            try {
                closeBuffers();
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
    }

    public void closeBuffers() {
        this.persistentBuffer.dispose();
        this.volatileBuffer.dispose();
        log.info("Buffers disposed");
    }
}
