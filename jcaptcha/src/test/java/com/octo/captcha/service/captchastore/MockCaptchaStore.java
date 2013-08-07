package com.octo.captcha.service.captchastore;

import java.util.Collection;
import java.util.Locale;

import com.octo.captcha.Captcha;
import com.octo.captcha.service.CaptchaServiceException;

public class MockCaptchaStore implements CaptchaStore {
	
	private boolean isInitCalled = false;

	public void cleanAndShutdown() {
		// TODO Auto-generated method stub

	}

	public void empty() {
		// TODO Auto-generated method stub

	}

	public Captcha getCaptcha(String id) throws CaptchaServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection getKeys() {
		// TODO Auto-generated method stub
		return null;
	}

	public Locale getLocale(String id) throws CaptchaServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean hasCaptcha(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	public void initAndStart() {
		isInitCalled = true;
	}

	public boolean removeCaptcha(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	public void storeCaptcha(String id, Captcha captcha)
			throws CaptchaServiceException {
		// TODO Auto-generated method stub

	}

	public void storeCaptcha(String id, Captcha captcha, Locale locale)
			throws CaptchaServiceException {
		// TODO Auto-generated method stub

	}

	/**
	 * @return the isInitCalled
	 */
	public boolean isInitCalled() {
		return isInitCalled;
	}

}
