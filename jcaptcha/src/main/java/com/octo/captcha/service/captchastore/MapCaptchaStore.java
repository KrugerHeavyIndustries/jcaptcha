/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.service.captchastore;

import com.octo.captcha.Captcha;
import com.octo.captcha.service.CaptchaServiceException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 * Simple store based on a HashMap
 */
public class MapCaptchaStore implements CaptchaStore {

    Map store;

    public MapCaptchaStore() {
        this.store = new HashMap();
    }

    /**
     * Check if a captcha is stored for this id
     *
     * @return true if a captcha for this id is stored, false otherwise
     */
    public boolean hasCaptcha(String id) {
        return store.containsKey(id);
    }

    /**
     * Store the captcha with the provided id as key. The key is assumed to be unique, so if the same key is used twice
     * to store a captcha, the store will return an exception
     *
     * @param id      the key
     * @param captcha the captcha
     *
     * @throws CaptchaServiceException if the captcha already exists, or if an error occurs during storing routine.
     */
    public void storeCaptcha(String id, Captcha captcha) throws CaptchaServiceException {
//        if (store.get(id) != null) {
//            throw new CaptchaServiceException("a captcha with this id already exist. This error must " +
//                    "not occurs, this is an implementation pb!");
//        }
        store.put(id, new CaptchaAndLocale(captcha));
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
        store.put(id, new CaptchaAndLocale(captcha,locale));
    }

	/**
     * Retrieve the captcha for this key from the store.
     *
     * @return the captcha for this id
     *
     * @throws CaptchaServiceException if a captcha for this key is not found or if an error occurs during retrieving
     *                                 routine.
     */
    public Captcha getCaptcha(String id) throws CaptchaServiceException {
        Object captchaAndLocale = store.get(id);
        return captchaAndLocale!=null?((CaptchaAndLocale) captchaAndLocale).getCaptcha():null;
    }

    /**
     * Retrieve the locale for this key from the store.
     *
     * @return the locale for this id, null if not found
     * @throws com.octo.captcha.service.CaptchaServiceException
     *          if an error occurs during retrieving routine.
     */
    public Locale getLocale(String id) throws CaptchaServiceException {
        Object captchaAndLocale = store.get(id);
        return captchaAndLocale!=null?((CaptchaAndLocale) captchaAndLocale).getLocale():null;
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
        if (store.get(id) != null) {
            store.remove(id);
            return true;
        }
        return false;
    }

    /**
     * get the size of this store
     */
    public int getSize() {
        return store.size();
    }

    /**
     * Return all the contained keys
     */
    public Collection getKeys() {
        return store.keySet();
    }

    /**
     * Empty the store
     */
    public void empty() {
        this.store = new HashMap();
    }
    
    /* (non-Javadoc)
	 * @see com.octo.captcha.service.captchastore.CaptchaStore#initAndStart()
	 */
	public void initAndStart() {
		// Nothing to do with map implementations
	}

	/* (non-Javadoc)
	 * @see com.octo.captcha.service.captchastore.CaptchaStore#shutdownAndClean()
	 */
	public void cleanAndShutdown() {
		store.clear();
	}
}
