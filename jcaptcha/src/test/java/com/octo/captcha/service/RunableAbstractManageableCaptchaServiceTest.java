/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.service;

import com.octo.captcha.Captcha;
import com.octo.captcha.engine.MockCaptchaEngine;
import com.octo.captcha.service.captchastore.CaptchaStore;
import com.octo.captcha.service.captchastore.MapCaptchaStore;

import java.util.Locale;

public class RunableAbstractManageableCaptchaServiceTest extends RunableAbstractCaptchaServiceTest {
    public static int MIN_GUARANTED_STORAGE_DELAY_IN_SECONDS = 3;
    public static int CAPTCHA_STORE_LOAD_BEFORE_GARBAGE_COLLECTION = 2 * SIZE;
    public static int MAX_CAPTCHA_STORE_SIZE = 3 * SIZE;


    protected void setUp() throws Exception {
        super.setUp();
        //redefines service
        this.service = new MockedManageableCaptchaService(new MapCaptchaStore(), new MockCaptchaEngine(),
                MIN_GUARANTED_STORAGE_DELAY_IN_SECONDS,
                MAX_CAPTCHA_STORE_SIZE, CAPTCHA_STORE_LOAD_BEFORE_GARBAGE_COLLECTION);
    }

    //down casts
    protected ManageableCaptchaService getMService() {
        return (ManageableCaptchaService) service;
    }

    public void testIsCaptchaStoreFull() {
        
        AbstractManageableCaptchaService mockedService = new MockedManageableCaptchaService(
                new MapCaptchaStore(), new MockCaptchaEngine(),
                MIN_GUARANTED_STORAGE_DELAY_IN_SECONDS, 0, 0);

        assertFalse(mockedService.isCaptchaStoreFull());
    }


    public void testAbstractManageableCaptchaService() throws Exception {
        try {
            new MockedManageableCaptchaService(new MapCaptchaStore(), new MockCaptchaEngine(), 0, 10, 100);
            fail("should have thrown an exception");
        } catch (Exception e) {
            assertTrue("IllegalArgumentException attended", e instanceof IllegalArgumentException);
        }
    }

    public void testGetCaptchaEngineClass() throws Exception {
        assertEquals("Sould be the mockEngine...", MockCaptchaEngine.class.getName(),
                getMService().getCaptchaEngineClass());
    }

    public void testSetCaptchaEngineClass() throws Exception {
        try {
            getMService().setCaptchaEngineClass("unknown");
            fail("shoul have thown an exception");
        } catch (IllegalArgumentException e) {
            assertEquals("Sould still be the mockEngine...",
                    MockCaptchaEngine.class.getName(),
                    getMService().getCaptchaEngineClass());
        }
        
        try {
            getMService().setCaptchaEngineClass("java.lang.String");
            fail("shoul have thown an exception");
        } catch (IllegalArgumentException e) {
            assertEquals("Sould still be the mockEngine...",
                    MockCaptchaEngine.class.getName(),
                    getMService().getCaptchaEngineClass());
        }
        
        assertEquals("Sould still be the mockEngine...", MockCaptchaEngine.class.getName(),
                getMService().getCaptchaEngineClass());

        try {
            getMService().setCaptchaEngineClass(SecondMockCaptchaEngine.class.getName());
            assertEquals("Sould be the SecondmockEngine...", SecondMockCaptchaEngine.class.getName(),
                    getMService().getCaptchaEngineClass());
        } catch (IllegalArgumentException e) {
            fail("should be ok " + e);
            assertEquals("Sould be the mockEngine...",
                    MockCaptchaEngine.class.getName(),
                    getMService().getCaptchaEngineClass());
        }
    }

    public void testEmptyCaptchaStore() throws Exception {
        for (int i = 0; i < SIZE; i++) {
            String id = String.valueOf(myRandom.nextInt());
            service.generateAndStoreCaptcha(Locale.getDefault(), id);
        }
        getMService().emptyCaptchaStore();
        assertTrue("it shoud be emtpy", getMService().getCaptchaStoreSize() == 0);
    }

    public void testGarbageCollectCaptchaStore() throws Exception {
        for (int i = 0; i < CAPTCHA_STORE_LOAD_BEFORE_GARBAGE_COLLECTION; i++) {
            String id = String.valueOf(myRandom.nextInt());
            service.generateAndStoreCaptcha(Locale.getDefault(), id);
        }

        getMService().garbageCollectCaptchaStore();
        assertTrue("store size should be the same(this test may fail if time to load the store is > min guaranted...)",
                getMService().getCaptchaStoreSize() == CAPTCHA_STORE_LOAD_BEFORE_GARBAGE_COLLECTION);
        //wait,  and collect
        Thread.sleep(MIN_GUARANTED_STORAGE_DELAY_IN_SECONDS * 1000 + 100);
        getMService().garbageCollectCaptchaStore();
        assertTrue("store should be empty",
                getMService().getCaptchaStoreSize() == 0);
    }

    public void testGetCaptchaStoreMaxSize() throws Exception {
        assertEquals("initial size", MAX_CAPTCHA_STORE_SIZE, getMService().getCaptchaStoreMaxSize());
    }

    public void testSetCaptchaStoreMaxSize() throws Exception {
        try {
            getMService().setCaptchaStoreMaxSize(CAPTCHA_STORE_LOAD_BEFORE_GARBAGE_COLLECTION - 1);
            fail("should have thrown an exception");
        } catch (Exception e) {
            assertTrue("IllegalArgumentException attended", e instanceof IllegalArgumentException);
        }
        getMService().setCaptchaStoreMaxSize(CAPTCHA_STORE_LOAD_BEFORE_GARBAGE_COLLECTION);
        assertEquals("modified size",
                CAPTCHA_STORE_LOAD_BEFORE_GARBAGE_COLLECTION, getMService().getCaptchaStoreMaxSize());
    }

    public void testSetCaptchaStoreSizeBeforeGarbageCollection() throws Exception {
        try {
            getMService().setCaptchaStoreSizeBeforeGarbageCollection(MAX_CAPTCHA_STORE_SIZE + 1);
            fail("should have thrown an exception");
        } catch (Exception e) {
            assertTrue("IllegalArgumentException attended", e instanceof IllegalArgumentException);
        }
        getMService().setCaptchaStoreSizeBeforeGarbageCollection(MAX_CAPTCHA_STORE_SIZE);
        assertEquals("modified size",
                MAX_CAPTCHA_STORE_SIZE, getMService().getCaptchaStoreSizeBeforeGarbageCollection());
    }

    public void testGetCaptchaStoreSizeBeforeGarbageCollection() throws Exception {
        assertEquals("initial value", CAPTCHA_STORE_LOAD_BEFORE_GARBAGE_COLLECTION,
                getMService().getCaptchaStoreSizeBeforeGarbageCollection());
    }

    public void testGetNumberOfGarbageCollectedCaptcha() throws Exception {
        assertEquals("inital value", 0, getMService().getNumberOfGarbageCollectedCaptcha());
        loadAndWait();
        getMService().garbageCollectCaptchaStore();
        assertEquals("all should have been collected",
                CAPTCHA_STORE_LOAD_BEFORE_GARBAGE_COLLECTION,
                getMService().getNumberOfGarbageCollectedCaptcha());
        //try with empty
        loadAndWait();
        getMService().emptyCaptchaStore();
        getMService().garbageCollectCaptchaStore();
        assertEquals("none have been collected",
                CAPTCHA_STORE_LOAD_BEFORE_GARBAGE_COLLECTION,
                getMService().getNumberOfGarbageCollectedCaptcha());

        loadAndWait();
        getMService().validateResponseForID(String.valueOf(0), "true");
        getMService().garbageCollectCaptchaStore();
        assertEquals("all but one should have been collected",
                2 * CAPTCHA_STORE_LOAD_BEFORE_GARBAGE_COLLECTION - 1,
                getMService().getNumberOfGarbageCollectedCaptcha());
    }


    public void testGetNumberOfGarbageCollectableCaptchas() throws Exception {
        assertEquals("inital value", 0, getMService().getNumberOfGarbageCollectableCaptchas());
        loadAndWait();

        assertEquals("all should be collectable",
                CAPTCHA_STORE_LOAD_BEFORE_GARBAGE_COLLECTION,
                getMService().getNumberOfGarbageCollectableCaptchas());
        getMService().garbageCollectCaptchaStore();
        
        //try with empty
        assertEquals("none should be collectable", 0, getMService().getNumberOfGarbageCollectableCaptchas());
        loadAndWait();
        assertEquals("all should be collectable",
                CAPTCHA_STORE_LOAD_BEFORE_GARBAGE_COLLECTION,
                getMService().getNumberOfGarbageCollectableCaptchas());
        getMService().emptyCaptchaStore();
        assertEquals("none should be collectable", 0, getMService().getNumberOfGarbageCollectableCaptchas());
        //try with validate
        loadAndWait();
        getMService().validateResponseForID(String.valueOf(0), "true");
        assertEquals("all but one should be collectable",
                CAPTCHA_STORE_LOAD_BEFORE_GARBAGE_COLLECTION - 1,
                getMService().getNumberOfGarbageCollectableCaptchas());

        getMService().getChallengeForID("newCaptcha");
        assertEquals("all but one should be collectable",
                CAPTCHA_STORE_LOAD_BEFORE_GARBAGE_COLLECTION - 1,
                getMService().getNumberOfGarbageCollectableCaptchas());

        Thread.sleep(MIN_GUARANTED_STORAGE_DELAY_IN_SECONDS * 1000 + 100);
        assertEquals("all should be collectable",
                CAPTCHA_STORE_LOAD_BEFORE_GARBAGE_COLLECTION,
                getMService().getNumberOfGarbageCollectableCaptchas());

    }

    public void testGetCaptchaStoreSize() throws Exception {
        assertEquals("initial size", 0, getMService().getCaptchaStoreSize());
        for (int i = 0; i < SIZE; i++) {
            String id = String.valueOf(i);
            service.generateAndStoreCaptcha(Locale.getDefault(), id);
            assertEquals("size", i + 1, getMService().getCaptchaStoreSize());
        }
        getMService().validateResponseForID(String.valueOf(0), "true");
        assertEquals("size", SIZE - 1,
                getMService().getCaptchaStoreSize());
        getMService().getChallengeForID("newCaptcha");
        assertEquals("size", SIZE
                , getMService().getCaptchaStoreSize());
        getMService().getChallengeForID("newCaptcha");
        assertEquals("size", SIZE
                , getMService().getCaptchaStoreSize());
        getMService().getQuestionForID("newCaptcha");
        assertEquals("size", SIZE
                , getMService().getCaptchaStoreSize());
        getMService().getQuestionForID("newCaptcha", Locale.getDefault());
        assertEquals("size", SIZE
                , getMService().getCaptchaStoreSize());
        getMService().getChallengeForID("newCaptcha", Locale.getDefault());
        assertEquals("size", SIZE
                , getMService().getCaptchaStoreSize());

    }

    public void testGetNumberOfUncorrectResponses() throws Exception {
        assertEquals("initial size", 0, getMService().getNumberOfUncorrectResponses());

        for (int i = 0; i < CAPTCHA_STORE_LOAD_BEFORE_GARBAGE_COLLECTION; i++) {
            String id = String.valueOf(i);
            service.generateAndStoreCaptcha(Locale.getDefault(), id);
            service.validateResponseForID(id, "false");
            assertEquals("size", i + 1, getMService().getNumberOfUncorrectResponses());
        }

        for (int i = 0; i < CAPTCHA_STORE_LOAD_BEFORE_GARBAGE_COLLECTION; i++) {
            String id = String.valueOf(i);
            service.generateAndStoreCaptcha(Locale.getDefault(), id);
            service.validateResponseForID(id, "true");
            assertEquals("should not have been incremented", CAPTCHA_STORE_LOAD_BEFORE_GARBAGE_COLLECTION,
                    getMService().getNumberOfUncorrectResponses());
        }

        for (int i = 0; i < CAPTCHA_STORE_LOAD_BEFORE_GARBAGE_COLLECTION; i++) {
            String id = String.valueOf(i);
            service.generateAndStoreCaptcha(Locale.getDefault(), id);
            try {
                service.validateResponseForID("unknown", "false");
                fail("should have thrown an exception");
            } catch (CaptchaServiceException e) {
            	assertNotNull(e.getMessage());
            }
            assertEquals("should not have been incremented", CAPTCHA_STORE_LOAD_BEFORE_GARBAGE_COLLECTION,
                    getMService().getNumberOfUncorrectResponses());
        }


    }

    public void testGetNumberOfCorrectResponses() throws Exception {
        assertEquals("initial size", 0, getMService().getNumberOfCorrectResponses());

        for (int i = 0; i < CAPTCHA_STORE_LOAD_BEFORE_GARBAGE_COLLECTION; i++) {
            String id = String.valueOf(i);
            service.generateAndStoreCaptcha(Locale.getDefault(), id);
            service.validateResponseForID(id, "true");
            assertEquals("size", i + 1, getMService().getNumberOfCorrectResponses());
        }

        for (int i = 0; i < CAPTCHA_STORE_LOAD_BEFORE_GARBAGE_COLLECTION; i++) {
            String id = String.valueOf(i);
            service.generateAndStoreCaptcha(Locale.getDefault(), id);
            service.validateResponseForID(id, "false");
            assertEquals("should not have been incremented", CAPTCHA_STORE_LOAD_BEFORE_GARBAGE_COLLECTION,
                    getMService().getNumberOfCorrectResponses());
        }

        for (int i = 0; i < CAPTCHA_STORE_LOAD_BEFORE_GARBAGE_COLLECTION; i++) {
            String id = String.valueOf(i);
            service.generateAndStoreCaptcha(Locale.getDefault(), id);
            try {
                service.validateResponseForID("unknown", "false");
                fail("should have thrown an exception");
            } catch (CaptchaServiceException e) {
            	assertNotNull(e.getMessage());
            }
            assertEquals("should not have been incremented", CAPTCHA_STORE_LOAD_BEFORE_GARBAGE_COLLECTION,
                    getMService().getNumberOfCorrectResponses());
        }
    }

    public void testGetNumberOfGeneratedCaptchas() throws Exception {
        assertEquals("initial size", 0, getMService().getNumberOfGeneratedCaptchas());

        for (int i = 0; i < SIZE; i++) {
            String id = String.valueOf(i);
            service.getChallengeForID(id);
            assertEquals("size", 4 * i + 1, getMService().getNumberOfGeneratedCaptchas());
            service.getChallengeForID(id);
            assertEquals("size", 4 * i + 2, getMService().getNumberOfGeneratedCaptchas());
            service.getQuestionForID(id);
            assertEquals("size", 4 * i + 2, getMService().getNumberOfGeneratedCaptchas());
            service.getQuestionForID(id);
            assertEquals("size", 4 * i + 2, getMService().getNumberOfGeneratedCaptchas());
            service.getChallengeForID(id);
            assertEquals("size", 4 * i + 3, getMService().getNumberOfGeneratedCaptchas());
            service.getChallengeForID(id);
            assertEquals("size", 4 * i + 4, getMService().getNumberOfGeneratedCaptchas());
        }
        long generatedBefore = getMService().getNumberOfGeneratedCaptchas();
        getMService().emptyCaptchaStore();
        for (int i = 0; i < SIZE; i++) {
            String id = String.valueOf(i);
            service.getQuestionForID(id);
            assertEquals("size", 3 * i + 1 + generatedBefore, getMService().getNumberOfGeneratedCaptchas());
            service.getChallengeForID(id);
            assertEquals("size", 3 * i + 1 + generatedBefore, getMService().getNumberOfGeneratedCaptchas());
            service.getQuestionForID(id);
            assertEquals("size", 3 * i + 1 + generatedBefore, getMService().getNumberOfGeneratedCaptchas());
            service.getQuestionForID(id);
            assertEquals("size", 3 * i + 1 + generatedBefore, getMService().getNumberOfGeneratedCaptchas());
            service.getChallengeForID(id);
            assertEquals("size", 3 * i + 2 + generatedBefore, getMService().getNumberOfGeneratedCaptchas());
            service.getChallengeForID(id);
            assertEquals("size", 3 * i + 3 + generatedBefore, getMService().getNumberOfGeneratedCaptchas());
        }


    }

    public void testSetMinGuarantedStorageDelayInSeconds() throws Exception {
        assertEquals("initial", MIN_GUARANTED_STORAGE_DELAY_IN_SECONDS, getMService().getMinGuarantedStorageDelayInSeconds());
        getMService().setMinGuarantedStorageDelayInSeconds(80);
        assertEquals("changed", 80, getMService().getMinGuarantedStorageDelayInSeconds());

        getMService().setMinGuarantedStorageDelayInSeconds(100);
        fullLoad();

        assertEquals("none should be collected", 0, getMService().getNumberOfGarbageCollectedCaptcha());

    }

    public void testGetMinGuarantedStorageDelayInSeconds() throws Exception {
        assertEquals("initial", MIN_GUARANTED_STORAGE_DELAY_IN_SECONDS, getMService().getMinGuarantedStorageDelayInSeconds());
    }


    public void testAutomaticGarbaging() throws Exception {
        loadAndWait();
        assertEquals("none should have been collected yet", 0, getMService().getNumberOfGarbageCollectedCaptcha());
        getMService().getChallengeForID("new");
        assertEquals("all should have been collected", CAPTCHA_STORE_LOAD_BEFORE_GARBAGE_COLLECTION,
                getMService().getNumberOfGarbageCollectedCaptcha());
        getMService().emptyCaptchaStore();
        assertEquals("we should not been able to garbage collect",
                0,
                getMService().getNumberOfGarbageCollectableCaptchas());
        getMService().setMinGuarantedStorageDelayInSeconds(5);
        assertEquals("we should not been able to garbage collect",
                0,
                getMService().getNumberOfGarbageCollectableCaptchas());
//        System.out.println("before "+System.currentTimeMillis());
        fullLoad();
//        System.out.println(System.currentTimeMillis());
        
        assertEquals("to be valid we should not been able to garbage collect",
                0,
                getMService().getNumberOfGarbageCollectableCaptchas());
        assertEquals("store size should be full",
                MAX_CAPTCHA_STORE_SIZE,
                getMService().getCaptchaStoreSize());
        Thread.sleep(5 * 1000 + 100);
        assertEquals("all should be collectable",
                MAX_CAPTCHA_STORE_SIZE,
                getMService().getNumberOfGarbageCollectableCaptchas());


    }


    public void testMaxStoreSizeConstraint() throws Exception {
        //getMService().setMinGuarantedStorageDelayInSeconds(10000);
        fullLoad();
        for (int i = 0; i < 100; i++) {
            try {
                getMService().getChallengeForID("new" + String.valueOf(i));
                fail("should have thrown a captcha store full exception");
            } catch (CaptchaServiceException e) {
                assertEquals("store should be of max size", MAX_CAPTCHA_STORE_SIZE, getMService().getCaptchaStoreSize());
            }
        }

        Thread.sleep(1000);
        getMService().setMinGuarantedStorageDelayInSeconds(1);
        Thread.sleep(1000);
        try {
            fullLoad();

        } catch (CaptchaServiceException e) {
            fail("should not have thrown a captcha store full exception");
        }

    }

    private void fullLoad() {
        int i = 0;
        try {
            for (i = 0; i < MAX_CAPTCHA_STORE_SIZE; i++) {
                String id = String.valueOf(i);
                service.generateAndStoreCaptcha(Locale.getDefault(), id);
            }
        } catch (CaptchaServiceException e) {
            System.out.println("i = " + i);
            e.printStackTrace();
            throw e;
        }
    }

    private void loadAndWait() throws InterruptedException {
        for (int i = 0; i < CAPTCHA_STORE_LOAD_BEFORE_GARBAGE_COLLECTION; i++) {
            String id = String.valueOf(i);
            service.generateAndStoreCaptcha(Locale.getDefault(), id);
        }
        Thread.sleep(MIN_GUARANTED_STORAGE_DELAY_IN_SECONDS * 1000 + 100);
    }


    protected class MockedManageableCaptchaService extends AbstractManageableCaptchaService {

        protected MockedManageableCaptchaService(CaptchaStore captchaStore, com.octo.captcha.engine.CaptchaEngine captchaEngine,
                                                 int minGuarantedStorageDelayInSeconds,
                                                 int maxCaptchaStoreSize,
                                                 int captchaStoreLoadBeforeGarbageCollection) {
            super(captchaStore, captchaEngine, minGuarantedStorageDelayInSeconds, maxCaptchaStoreSize,
                    captchaStoreLoadBeforeGarbageCollection);
        }

        /**
         * This method must be implemented by sublcasses and : Retrieve the challenge from the captcha Make and return a
         * clone of the challenge Return the clone It has be design in order to let the service dipose the challenge of
         * the captcha after rendering. It should be implemented for all captcha type (@see ImageCaptchaService
         * implementations for exemple)
         *
         * @return a Challenge Clone
         */
        protected Object getChallengeClone(Captcha captcha) {
            return new String(captcha.getChallenge().toString()) + MockedCaptchaService.CLONE_CHALLENGE;
        }

    }


}
