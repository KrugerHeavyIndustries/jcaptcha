/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.service.captchastore;


/**
 * @author <a href="mailto:antoine.veret@gmail.com">Antoine Véret</a>
 * @version 1.0
 */
public class JBossCacheCaptchaStoreTest extends CaptchaStoreTestAbstract {

	/* (non-Javadoc)
	 * @see com.octo.captcha.service.captchastore.CaptchaStoreTestAbstract#getStore()
	 */
	public CaptchaStore getStore() {
		return new JBossCacheCaptchaStore();
	}
	
    public void setUp() throws Exception {
    	System.setProperty(JBossCacheCaptchaStore.JCAPTCHA_JBOSSCACHE_CONFIG, "captchaStoreJBossCache.xml");
    	super.setUp();                
    }
    
    public void tearDown() {
        super.tearDown();      
    }
    
    public void testSystemPropertyForConfig() throws Exception {
        System.clearProperty(JBossCacheCaptchaStore.JCAPTCHA_JBOSSCACHE_CONFIG);
        
        store.cleanAndShutdown();
        
        try {
            store.initAndStart();
            fail();
        } catch (RuntimeException e) {
            assertEquals("The system property " + JBossCacheCaptchaStore.JCAPTCHA_JBOSSCACHE_CONFIG +
                    " have to be set", e.getMessage());
        }
    }

}
