/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.service.captchastore;

import java.util.Collection;
import java.util.Locale;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.octo.captcha.Captcha;
import com.octo.captcha.service.CaptchaServiceException;

/**
 * Ehcache implementation of the captcha store
 *
 * @author <a href="mailto:marc.antoine.garrigue@gmail.com">Marc-Antoine Garrigue</a>
 * @version 1.0
 * @deprecated
 */
public class EhcacheCaptchaStore implements CaptchaStore {

	private static Log log = LogFactory.getLog(EhcacheCaptchaStore.class);
	private String cacheName;
    private Cache cache;

    public EhcacheCaptchaStore() {
    	this("JCaptcha_cache");
    }
    
    public EhcacheCaptchaStore(String cacheName) {
    	this.cacheName = cacheName;
    }
    
    /**
     * @deprecated prefer the initAndStart() method in order to initialize the cache. 
     * @param cache cache already initialized in the EhcacheManageableCaptchaService
     */
    public EhcacheCaptchaStore(Cache cache) {
    	this.cache = cache;
    }
    
    /**
     * Check if a captcha is stored for this id
     *
     * @return true if a captcha for this id is stored, false otherwise
     */
    public boolean hasCaptcha(String id) {
        try {
            Element el = this.cache.get(id);
            return el != null && el.getValue() != null;
        } catch (CacheException e) {
            log.error(e);
            return false;
        }
    }

    /**
     * Store the captcha with the provided id as key. The key is assumed to be unique, so if the same key is used twice
     * to store a captcha, the store will return an exception
     *
     * @param id      the key
     * @param captcha the captcha
     *
     * @throws com.octo.captcha.service.CaptchaServiceException
     *          if the captcha already exists, or if an error occurs during storing routine.
     */
    public void storeCaptcha(String id, Captcha captcha) throws CaptchaServiceException {
        cache.put(new Element(id, new CaptchaAndLocale(captcha)));
    }

    /**
     * Store the captcha with the provided id as key. The key is assumed to be unique, so if the same key is used twice
     * to store a captcha, the store will return an exception
     *
     * @param id      the key
     * @param captcha the captcha
     * @param locale  the locale used that triggers the captcha generation
     * @throws com.octo.captcha.service.CaptchaServiceException
     *          if the captcha already exists, or if an error occurs during storing routine.
     */
    public void storeCaptcha(String id, Captcha captcha, Locale locale) throws CaptchaServiceException {
        cache.put(new Element(id, new CaptchaAndLocale(captcha,locale)));
    }

    /**
     * Retrieve the captcha for this key from the store.
     *
     * @return the captcha for this id
     *
     * @throws CaptchaServiceException if an error occurs during retrieving routine.
     */
    public Captcha getCaptcha(String id) throws CaptchaServiceException {

        try {
            Element el = cache.get(id);
            if (el != null) {
                return ((CaptchaAndLocale) el.getValue()).getCaptcha();
            } else {
                return null;
            }
        } catch (CacheException e) {
            log.error(e);
            return null;
        }
    }


    /**
     * Retrieve the locale for this key from the store.
     *
     * @return the locale for this id, null if not found
     * @throws com.octo.captcha.service.CaptchaServiceException
     *          if an error occurs during retrieving routine.
     */
    public Locale getLocale(String id) throws CaptchaServiceException {

        try {
            Element el = cache.get(id);
            if (el != null) {
                return ((CaptchaAndLocale) el.getValue()).getLocale();
            } else {
                return null;
            }
        } catch (CacheException e) {
            log.error(e);
            return null;
        }
    }

    /**
     * Remove the captcha with the provided id as key.
     *
     * @param id the key
     *
     * @return true if found, false otherwise
     *
     * @throws CaptchaServiceException if an error occurs during remove routine
     */
    public boolean removeCaptcha(String id) {
        return cache.remove(id);
    }

    /**
     * get the size of this store
     */
    public int getSize() {
        return (int) cache.getMemoryStoreSize() + cache.getDiskStoreSize();

    }

    /**
     * Return all the contained keys
     */
    public Collection getKeys() {
        try {
            return cache.getKeys();
        } catch (CacheException e) {
            log.error(e);
            return null;
        }
    }

    /**
     * Empty the store
     */
    public void empty() {
        try {
            cache.removeAll();
        } catch (Exception e) {
            log.error(e);
        }
    }

    /* (non-Javadoc)
	 * @see com.octo.captcha.service.captchastore.CaptchaStore#initAndStart()
	 */
	public void initAndStart() {
		if (cache == null) {
			CacheManager.getInstance().addCache(this.cacheName);
			this.cache = CacheManager.getInstance().getCache(this.cacheName);
		}
	}

	/* (non-Javadoc)
	 * @see com.octo.captcha.service.captchastore.CaptchaStore#shutdownAndClean()
	 */
	public void cleanAndShutdown() {
		this.empty();
		CacheManager.getInstance().removeCache(this.cacheName);
		this.cache = null;
	}

}
