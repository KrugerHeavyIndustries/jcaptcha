/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.service.captchastore;

import com.octo.captcha.Captcha;
import com.octo.captcha.service.CaptchaServiceException;

import java.util.Collection;
import java.util.Locale;

/**
 * Provides a way to temporally store captchas with a unique key
 */
public interface CaptchaStore {
    /**
     * Check if a captcha is stored for this id
     *
     * @return true if a captcha for this id is stored, false otherwise
     */
    boolean hasCaptcha(String id);

    /**
     * Store the captcha with the provided id as key. The key is assumed to be unique, so if the same key is used twice
     * to store a captcha, the store will return an exception
     *
     * @param id      the key
     * @param captcha the captcha
     *
     * @throws com.octo.captcha.service.CaptchaServiceException
     *          if the captcha already exists, or if an error occurs during storing routine.
     *
     * @deprecated in order to implement the by locale generation, use the {@link #storeCaptcha(String, com.octo.captcha.Captcha, java.util.Locale)}
     */
    void storeCaptcha(String id, Captcha captcha) throws CaptchaServiceException;

    /**
     * Store the captcha with the provided id as key. The key is assumed to be unique, so if the same key is used twice
     * to store a captcha, the store will return an exception
     *
     * @param id      the key
     * @param captcha the captcha
     * @param locale the locale used that triggers the captcha generation
     *
     * @throws com.octo.captcha.service.CaptchaServiceException
     *          if the captcha already exists, or if an error occurs during storing routine.
     *
     */
    void storeCaptcha(String id, Captcha captcha, Locale locale) throws CaptchaServiceException;


    /**
     * Remove the captcha with the provided id as key.
     *
     * @param id the key
     *
     * @return true if found, false otherwise
     *
     * @throws CaptchaServiceException if an error occurs during remove routine
     */
    boolean removeCaptcha(String id);


    /**
     * Retrieve the captcha for this key from the store.
     *
     * @return the captcha for this id, null if not found
     *
     * @throws CaptchaServiceException if an error occurs during retrieving routine.
     */
    Captcha getCaptcha(String id) throws CaptchaServiceException;

    /**
     * Retrieve the locale for this key from the store.
     *
     * @return the locale for this id, null if not found
     *
     * @throws CaptchaServiceException if an error occurs during retrieving routine.
     */
    Locale getLocale(String id) throws CaptchaServiceException;


    /**
     * get the size of this store
     */
    int getSize();

    /**
     * Return all the contained keys
     */
    Collection getKeys();

    /**
     * Empty the store
     */
    void empty();
    
    /** 
     * Called by the service in order to initialize and start the Store.
     */
    void initAndStart();
    
    /**
     * Called by the service in order to clean and shutdown the store.
     */
    void cleanAndShutdown();
}
