/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.service;

import junit.framework.TestCase;
import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;
import net.sourceforge.groboutils.junit.v1.TestRunnable;

/**
 * Base Load test for service implementation
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public abstract class ServiceLoadTestAbstract extends TestCase {

    protected abstract void setUp() throws Exception;

    protected CaptchaService service;

    static class ServiceUserNominalHelper extends TestRunnable {

        private CaptchaService service;
        private int count;
        private int sleepTime;


        public ServiceUserNominalHelper(CaptchaService service, int count,
                                        int delay) {
            this.service = service;
            this.count = count;
            this.sleepTime = delay;
        }

        public void runTest() throws Throwable {
            for (int i = 0; i < this.count; ++i) {

                String question = service.getQuestionForID(this.toString());
                Thread.sleep(this.sleepTime);
                assertNotNull(question);
                assertTrue("should not be empty", !"".equals(question));
                Object challenge = service.getChallengeForID(this.toString());
                Thread.sleep(this.sleepTime);
                assertNotNull(challenge);
                Boolean valid = service.validateResponseForID(this.toString(), "");
                Thread.sleep(this.sleepTime);
                assertNotNull(valid);
                Thread.sleep(this.sleepTime);
            }
        }
    }


    static class ServiceUserSpamHelper extends TestRunnable {

        private CaptchaService service;
        private int count;
        private int sleepTime;


        public ServiceUserSpamHelper(CaptchaService service, int count,
                                     int delay) {
            this.service = service;
            this.count = count;
            this.sleepTime = delay;
        }

        public void runTest() throws Throwable {
            for (int i = 0; i < this.count; ++i) {
                Object challenge = service.getChallengeForID(this.toString());
                assertNotNull(challenge);
                Boolean valid = service.validateResponseForID(this.toString(), "");
                Thread.sleep(this.sleepTime);
            }
        }
    }

    public void testNominal_100It_0Del_1Us_2min() throws Throwable {
        int count_nominal = 100;
        int delay_nominal = 0;
        int users_nominal = 1;

        int count_spam = 0;
        int delay_spam = 0;
        int users_spam = 0;
        int max_time = 2 * 60 * 1000;
        load(users_nominal, count_nominal, delay_nominal, users_spam, count_spam, delay_spam, max_time);


    }

    public void testNominal_1It_0Del_100Us_2min() throws Throwable {

        int count_nominal = 1;
        int delay_nominal = 0;
        int users_nominal = 100;

        int count_spam = 0;
        int delay_spam = 0;
        int users_spam = 0;
        int max_time = 2 * 60 * 1000;
        load(users_nominal, count_nominal, delay_nominal, users_spam, count_spam, delay_spam, max_time);

    }


    public void testNominal_10It_10Del_10Us_Spam_100It_10Del_5Us_2min() throws Throwable {
        int count_nominal = 10;
        int delay_nominal = 10;
        int users_nominal = 10;

        int count_spam = 100;
        int delay_spam = 10;
        int users_spam = 5;
        int max_time = 2 * 60 * 1000;
        load(users_nominal, count_nominal, delay_nominal, users_spam, count_spam, delay_spam, max_time);

    }


    public void testNominal_10It_100Del_10Us_Spam_100It_10Del_5Us_2min() throws Throwable {
        int count_nominal = 10;
        int delay_nominal = 100;
        int users_nominal = 10;

        int count_spam = 100;
        int delay_spam = 10;
        int users_spam = 5;
        int max_time = 2 * 60 * 1000;
        load(users_nominal, count_nominal, delay_nominal, users_spam, count_spam, delay_spam, max_time);

    }


    public void testNominal_2It_100Del_500Us_Spam_100It_10Del_5Us_5min() throws Throwable {
        int count_nominal = 2;
        int delay_nominal = 100;
        int users_nominal = 500;

        int count_spam = 100;
        int delay_spam = 10;
        int users_spam = 5;
        int max_time = 5 * 60 * 1000;
        load(users_nominal, count_nominal, delay_nominal, users_spam, count_spam, delay_spam, max_time);

    }


    private void load(int users_nominal, int count_nominal, int delay_nominal, int users_spam, int count_spam, int delay_spam, int max_time)
            throws Throwable {
        TestRunnable[] tcs = new TestRunnable[users_nominal + users_spam];
        for (int i = 0; i < users_nominal; i++) {
            tcs[i] = new ServiceUserNominalHelper(this.service, count_nominal, delay_nominal);
        }
        for (int i = 0; i < users_spam; i++) {
            tcs[i + users_nominal] = new ServiceUserSpamHelper(this.service, count_spam, delay_spam);
        }
        MultiThreadedTestRunner mttr =
                new MultiThreadedTestRunner(tcs);
        mttr.runTestRunnables(max_time);
    }

}