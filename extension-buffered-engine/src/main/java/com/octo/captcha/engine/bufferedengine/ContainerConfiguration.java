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

import com.octo.captcha.engine.CaptchaEngineException;
import org.apache.commons.collections.map.HashedMap;

import java.util.Locale;
import java.util.Map;

/**
 * Class that contains informations to configure the BufferedEngineContainer.
 *
 * @author Benoit Doumas
 */
public class ContainerConfiguration {
    private Integer feedSize;

    private Integer swapSize;

    private Integer maxVolatileMemorySize;

    private Integer maxPersistentMemorySize;

    private HashedMap localeRatio = new HashedMap();

    private boolean serveOnlyConfiguredLocales = false;

    private Locale defaultLocale = Locale.getDefault();

    private Integer feedBatchSize;

    /**
     * Contructs a ContainerConfiguration with custom feed size and sawp size
     *
     * @param localeRatio             locales and their ratio that should be feeded
     * @param maxVolatileMemorySize   in memory captcha buffer max size
     * @param maxPersistentMemorySize persistent captcha buffer max size
     * @param swapSize                number of captchas that will be swapped from persistent to memory buffer each
     *                                time
     * @param feedSize                number of captchas that will be generated and stored to persistent buffer each
     *                                time
     */
    public ContainerConfiguration(Map localeRatio, int maxVolatileMemorySize, int maxPersistentMemorySize,
                                  int swapSize, int feedSize) {
        this.localeRatio.putAll(localeRatio);
        this.maxVolatileMemorySize = new Integer(maxVolatileMemorySize);
        this.maxPersistentMemorySize = new Integer(maxPersistentMemorySize);
        this.feedSize = new Integer(feedSize);
        this.swapSize = new Integer(swapSize);
        this.feedBatchSize = new Integer(feedSize);
    }


    /**
     * Contructs a ContainerConfiguration with custom feed size and sawp size
     *
     * @param localeRatio                locales and their ratio that should be feeded
     * @param maxVolatileMemorySize      in memory captcha buffer max size
     * @param maxPersistentMemorySize    persistent captcha buffer max size
     * @param swapSize                   number of captchas that will be swapped from persistent to memory buffer each
     *                                   time
     * @param feedSize                   number of captchas that will be generated and stored to persistent buffer each
     *                                   time
     * @param serveOnlyConfiguredLocales if set to true, serve only locales that are in configuration (only by
     *                                   language)
     * @param defaultLocale              the default locale used by this engineContainer.
     */
    public ContainerConfiguration(Map localeRatio, int maxVolatileMemorySize, int maxPersistentMemorySize,
                                  int swapSize, int feedSize, boolean serveOnlyConfiguredLocales, Locale defaultLocale) {
        this(localeRatio, maxVolatileMemorySize, maxPersistentMemorySize, swapSize, feedSize);
        this.serveOnlyConfiguredLocales = serveOnlyConfiguredLocales;
        this.defaultLocale = defaultLocale != null ? defaultLocale : this.defaultLocale;
        validateDefaultLocale(serveOnlyConfiguredLocales, defaultLocale);

    }

    /**
     * Contructs a ContainerConfiguration with custom feed size and sawp size
     *
     * @param localeRatio                locales and their ratio that should be feeded
     * @param maxVolatileMemorySize      in memory captcha buffer max size
     * @param maxPersistentMemorySize    persistent captcha buffer max size
     * @param swapSize                   number of captchas that will be swapped from persistent to memory buffer each
     *                                   time
     * @param feedSize                   number of captchas that will be generated and stored to persistent buffer each
     *                                   time
     * @param feedBatchSize              max number of batch captchas to build when feeding, in order to preserve
     *                                   memory
     * @param serveOnlyConfiguredLocales if set to true, serve only locales that are in configuration (only by
     *                                   language)
     * @param defaultLocale              the default locale used by this engineContainer.
     */
    public ContainerConfiguration(Map localeRatio, int maxVolatileMemorySize, int maxPersistentMemorySize,
                                  int swapSize, int feedSize, int feedBatchSize, boolean serveOnlyConfiguredLocales, Locale defaultLocale) {
        this(localeRatio, maxVolatileMemorySize, maxPersistentMemorySize, swapSize, feedSize, serveOnlyConfiguredLocales, defaultLocale);
        this.feedBatchSize = new Integer(feedBatchSize);
    }


    public Integer getFeedBatchSize() {
        return feedBatchSize;
    }

    public void setFeedBatchSize(Integer feedBatchSize) {
        this.feedBatchSize = feedBatchSize;
    }

    public boolean isServeOnlyConfiguredLocales() {
        return serveOnlyConfiguredLocales;
    }

    public void setServeOnlyConfiguredLocales(boolean serveOnlyConfiguredLocales) {
        validateDefaultLocale(serveOnlyConfiguredLocales, defaultLocale);
        this.serveOnlyConfiguredLocales = serveOnlyConfiguredLocales;
    }

    public Locale getDefaultLocale() {
        return defaultLocale;
    }

    public void setDefaultLocale(Locale defaultLocale) {
        validateDefaultLocale(this.serveOnlyConfiguredLocales, defaultLocale);

        this.defaultLocale = defaultLocale;
    }

    private void validateDefaultLocale(boolean serveOnlyConfiguredLocales, Locale defaultLocale) {
        if ((!this.getLocaleRatio().containsKey(defaultLocale)) && serveOnlyConfiguredLocales) {
            throw new CaptchaEngineException("impossible build a ContainerConfiguration with a default locale that" +
                    " is not in its localeRatio and that serve only configured locales : locale " + defaultLocale);
        }
    }

    /**
     * @return
     */
    public Integer getFeedSize() {
        return feedSize;
    }

    /**
     * @param feedSize
     */
    public void setFeedSize(Integer feedSize) {
        this.feedSize = feedSize;
    }

    /**
     * @return Map of the ratio of locales
     */
    public HashedMap getLocaleRatio() {
        return localeRatio;
    }

    /**
     * @param localeRatio Map of the ratio of locales, to produce captcha and to swap captchas
     */
    public void setLocaleRatio(Map localeRatio) {
        this.localeRatio = new HashedMap(localeRatio.size());
        this.localeRatio.putAll(localeRatio);
    }

    /**
     * @return Get maximum size for the disk buffer
     */
    public Integer getMaxPersistentMemorySize() {
        return maxPersistentMemorySize;
    }

    /**
     * @param maxPersistentMemorySize Set maximum size for the disk buffer
     */
    public void setMaxPersistentMemorySize(Integer maxPersistentMemorySize) {
        this.maxPersistentMemorySize = maxPersistentMemorySize;
    }

    /**
     * @return Get maximun size for the volatile buffer
     */
    public Integer getMaxVolatileMemorySize() {
        return maxVolatileMemorySize;
    }

    /**
     * @param maxVolatileMemorySize Set maximun size for the volatile buffer
     */
    public void setMaxVolatileMemorySize(Integer maxVolatileMemorySize) {
        this.maxVolatileMemorySize = maxVolatileMemorySize;
    }

    /**
     * @return Get number of captchas to swap between the volatil buffer and the disk buffer
     */
    public Integer getSwapSize() {
        return swapSize;
    }

    /**
     * @param swapSize Set number of captchas to swap between the volatil buffer and the disk buffer
     */
    public void setSwapSize(Integer swapSize) {
        this.swapSize = swapSize;
    }
}
