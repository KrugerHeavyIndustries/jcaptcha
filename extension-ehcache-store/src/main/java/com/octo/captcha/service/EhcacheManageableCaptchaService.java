/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.service;

import com.octo.captcha.Captcha;
import com.octo.captcha.service.captchastore.EhcacheCaptchaStore;
import com.octo.captcha.service.captchastore.MapCaptchaStore;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;

/**
 * This class provides an implementation for the ehcache enhanced management interface. It uses the self managed cache
 * ehcache as CaptchaStore
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 * @deprecated
 */
public abstract class EhcacheManageableCaptchaService
        extends AbstractCaptchaService
        implements EhcacheManageableCaptchaServiceMBean {

    private static Log log = LogFactory.getLog(EhcacheManageableCaptchaService.class);
    private CacheManager cacheManager;
    private int minGuarantedStorageDelayInSeconds;

    private int captchaStoreMaxSize;


    private int numberOfGeneratedCaptchas = 0;
    private int numberOfCorrectResponse = 0;
    private int numberOfUncorrectResponse = 0;
    public static final String CACHE_NAME_PREFIX = "jcaptcha.store.";
    public static final String DEFAULT_CACHE_NAME = "default";

    public String captchaStoreCacheName;


    protected EhcacheManageableCaptchaService(com.octo.captcha.engine.CaptchaEngine captchaEngine,
                                              int minGuarantedStorageDelayInSeconds, int maxCaptchaStoreSize) {
        this(captchaEngine, minGuarantedStorageDelayInSeconds, maxCaptchaStoreSize, DEFAULT_CACHE_NAME);
    }


    protected EhcacheManageableCaptchaService(com.octo.captcha.engine.CaptchaEngine captchaEngine,
                                              int minGuarantedStorageDelayInSeconds, int maxCaptchaStoreSize,
                                              String cacheName) {
        //just to compile : call super first
        super(new MapCaptchaStore(), captchaEngine);

        //if name is null, use the default one
        if (cacheName == null || "".equals(cacheName)) {
            cacheName = DEFAULT_CACHE_NAME;
        }
        //set the cache name
        captchaStoreCacheName = CACHE_NAME_PREFIX + cacheName;

        //creates the manager
        try {
            this.cacheManager = CacheManager.getInstance();
        } catch (CacheException e) {
            log.error(e);
        }
        // create a cache with overflow on disk,
        Cache cache = new Cache(captchaStoreCacheName, maxCaptchaStoreSize, true, false, minGuarantedStorageDelayInSeconds,
                minGuarantedStorageDelayInSeconds);
        //store the cache

        try {
            if (cacheManager.cacheExists(captchaStoreCacheName)) {
                cacheManager.removeCache(captchaStoreCacheName);
            }
            cacheManager.addCache(cache);
        } catch (CacheException e) {
            log.error(e);
        }
        //change the super store
        super.store = new EhcacheCaptchaStore(cache);

        this.captchaStoreMaxSize = maxCaptchaStoreSize;
        this.minGuarantedStorageDelayInSeconds = minGuarantedStorageDelayInSeconds;

    }


    /**
     * Get the fully qualified class name of the concrete CaptchaEngine used by the service.
     *
     * @return the fully qualified class name of the concrete CaptchaEngine used by the service.
     */
    public String getCaptchaEngineClass() {
        return this.engine.getClass().getName();
    }

    /**
     * Set the fully qualified class name of the concrete CaptchaEngine used by the service
     *
     * @param theClassName the fully qualified class name of the CaptchaEngine used by the service
     *
     * @throws IllegalArgumentException if className can't be used as the service CaptchaEngine, either because it can't
     *                                  be instanciated by the service or it is not a ImageCaptchaEngine concrete
     *                                  class.
     */
    public void setCaptchaEngineClass(String theClassName)
            throws IllegalArgumentException {
        try {
            Object engine = Class.forName(theClassName).newInstance();
            if (engine instanceof com.octo.captcha.engine.CaptchaEngine) {
                this.engine = (com.octo.captcha.engine.CaptchaEngine) engine;
            } else {
                throw new IllegalArgumentException("Class is not instance of CaptchaEngine! "
                        + theClassName);
            }
        } catch (InstantiationException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (RuntimeException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * Get the minimum delay (in seconds) a client can be assured that a captcha generated by the service can be
     * retrieved and a response to its challenge tested
     *
     * @return the maximum delay in seconds
     */
    public int getMinGuarantedStorageDelayInSeconds() {
        return minGuarantedStorageDelayInSeconds;
    }

    /**
     * set the minimum delay (in seconds)a client can be assured that a captcha generated by the service can be
     * retrieved and a response to its challenge tested
     *
     * @param theMinGuarantedStorageDelayInSeconds
     *         the minimum guaranted delay
     */
    public void setMinGuarantedStorageDelayInSeconds(int theMinGuarantedStorageDelayInSeconds) {
        this.minGuarantedStorageDelayInSeconds = theMinGuarantedStorageDelayInSeconds;

        updateCache();
    }


    /**
     * Get the number of captcha generated since the service is up WARNING : this value won't be significant if the real
     * number is > Long.MAX_VALUE
     *
     * @return the number of captcha generated since the service is up
     */
    public long getNumberOfGeneratedCaptchas() {
        return numberOfGeneratedCaptchas;
    }

    /**
     * Get the number of correct responses to captcha challenges since the service is up. WARNING : this value won't be
     * significant if the real number is > Long.MAX_VALUE
     *
     * @return the number of correct responses since the service is up
     */
    public long getNumberOfCorrectResponses() {
        return numberOfCorrectResponse;
    }

    /**
     * Get the number of uncorrect responses to captcha challenges since the service is up. WARNING : this value won't
     * be significant if the real number is > Long.MAX_VALUE
     *
     * @return the number of uncorrect responses since the service is up
     */
    public long getNumberOfUncorrectResponses() {
        return numberOfUncorrectResponse;
    }

    /**
     * Get the curent size of the captcha store
     *
     * @return the size of the captcha store
     */
    public int getCaptchaStoreSize() {
        return this.store.getSize();
    }

    /**
     * Get the number of captchas that can be garbage collected in the captcha store
     *
     * @return the number of captchas that can be garbage collected in the captcha store
     */
    public int getNumberOfGarbageCollectableCaptchas() {
        return 0;
    }


    /**
     * Get the number of captcha garbage collected since the service is up WARNING : this value won't be significant if
     * the real number is > Long.MAX_VALUE
     *
     * @return the number of captcha garbage collected since the service is up
     */
    public long getNumberOfGarbageCollectedCaptcha() {
        return 0;
    }

    /**
     * @return the max captchaStore load before garbage collection of the store
     */
    public int getCaptchaStoreSizeBeforeGarbageCollection() {
        return 0;
    }

    /**
     * max captchaStore size before garbage collection of the store
     */
    public void setCaptchaStoreSizeBeforeGarbageCollection(int captchaStoreSizeBeforeGarbageCollection) {

        throw new UnsupportedOperationException("the max store size is useless for eh cache implementation");


    }

    /**
     * This max size is used by the service : it will throw a CaptchaServiceException if the store is full when a client
     * ask for a captcha.
     */
    public void setCaptchaStoreMaxSize(int size) {
        this.captchaStoreMaxSize = size;

        updateCache();

    }


    /**
     * @return the desired max size of the captcha store
     */
    public int getCaptchaStoreMaxSize() {
        return this.captchaStoreMaxSize;

    }

    /**
     * Garbage collect the captcha store, means all old capthca (captcha in the store wich has been stored more than the
     * MinGuarantedStorageDelayInSecond
     */
    public void garbageCollectCaptchaStore() {
        //to garbage collect, wait 5 minutes or get : see ehcache doco
        Iterator it = null;
        try {
            it = cacheManager.getCache(captchaStoreCacheName).getKeys().iterator();
        } catch (CacheException e) {
            log.error(e);
        }
        while (it.hasNext()) {
            try {
                cacheManager.getCache(captchaStoreCacheName).get(it.next().toString());
            } catch (CacheException e) {
                log.error(e);
            }
        }
    }


    /**
     * Empty the Store
     */
    public void emptyCaptchaStore() {
        //empty the store
        this.store.empty();
    }


    private void updateCache() {

        Cache cache = new Cache(captchaStoreCacheName, captchaStoreMaxSize, true, false, minGuarantedStorageDelayInSeconds,
                minGuarantedStorageDelayInSeconds);
        Iterator it = null;
        try {
            it = copyCacheContent().iterator();
        } catch (CacheException e) {
            log.error(e);
        }

        try {
            cacheManager.removeCache(captchaStoreCacheName);
            cacheManager.addCache(cache);
            this.store = new EhcacheCaptchaStore(cache);
            Cache myCache = cacheManager.getCache(captchaStoreCacheName);
            long now = System.currentTimeMillis();
            while (it.hasNext()) {
                Element el = (Element) it.next();
                if ((now - el.getCreationTime()) < cache.getTimeToLiveSeconds() * 1000) {
                    myCache.put(el);
                }
            }
        } catch (CacheException e) {
            log.error(e);
        }

    }


    private Collection copyCacheContent() throws CacheException {
        Cache currentcache = cacheManager.getCache(captchaStoreCacheName);
        Iterator it = store.getKeys().iterator();
        Collection els = new HashSet();
        while (it.hasNext()) {
                Element el = currentcache.get(it.next().toString());
                if (el != null) {
                    els.add(el);
                }
        }
        return els;


    }

    //*******
    ///Overriding business methods to add some stats and store management hooks
    ///****

    protected Captcha generateAndStoreCaptcha(Locale locale, String ID) {
            if (store.getSize() >= this.captchaStoreMaxSize) {
                //impossible ! has to wait
                throw new CaptchaServiceException("Store is full," +
                        " try to increase CaptchaStore Size or " +
                        "to decrease time out");

            }

        Captcha captcha = this.engine.getNextCaptcha(locale);
        this.numberOfGeneratedCaptchas++;
        store.storeCaptcha(ID, captcha, locale);
        return captcha;
    }




    /**
     * Method to validate a response to the challenge corresponding to the given ticket and remove the coresponding
     * captcha from the store.
     *
     * @param ID the ticket provided by the buildCaptchaAndGetID method
     *
     * @return true if the response is correct, false otherwise.
     *
     * @throws CaptchaServiceException if the ticket is invalid
     */
    public Boolean validateResponseForID(String ID, Object response) throws CaptchaServiceException {

        Boolean valid = super.validateResponseForID(ID, response);

        if (valid.booleanValue()) {
            numberOfCorrectResponse++;
        } else {
            numberOfUncorrectResponse++;
        }
        return valid;
    }


}
