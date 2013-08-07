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
package com.octo.captcha.engine.sound.utils;

import com.octo.captcha.engine.sound.SoundCaptchaEngine;
import com.octo.captcha.engine.sound.gimpy.SimpleListSoundCaptchaEngine;
import com.octo.captcha.engine.sound.speller.SpellerSoundCaptchaEngine;
import com.octo.captcha.sound.SoundCaptcha;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * This utility class lets you create WAV files with a particular
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @author <a href="mailto:travis.winfrey@gmail.com">Travis Winfrey</a>
 * @version 1.0
 */
public class SoundCaptchaToWAV {

    private static boolean SHOULD_DELETE_OLD_WAVS_FIRST = true;

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Usage : engineClassName outputDir iterations");
            System.out.println("If engineClassName is 'all', then several sound Engines are used");
            System.exit(1);
        }


        String className = args[0];
        File outputDir = new File(args[1]);
        String iterationsString = args[2];
        int iterations = Integer.parseInt(iterationsString);

        System.out.println("args : " +
                "sound captcha engine class='" + className + "'" +
                ", output dir='" + outputDir + "'" +
                ",iterations='" + iterationsString + "'");

        SoundCaptchaToWAV.clearOutputDirectory(outputDir);

        SoundCaptchaEngine captchaEngine = null;
        if (className.equals("all")) {
            SoundCaptchaEngine[] engines = {
                    new SpellerSoundCaptchaEngine(),
                    new SimpleListSoundCaptchaEngine(),

            };
            for (int i = 0; i < engines.length; i++) {
                captchaEngine = engines[i];
                System.out.println("Beginning generation with " + captchaEngine.getClass().getName());
                try {
                    SoundCaptchaToWAV.generate(iterations, captchaEngine, outputDir);
                }
                catch (Exception e) {
                    System.out.println("Errors with class " + captchaEngine.getClass().getName());
                }
            }
        } else {

            try {
                captchaEngine = (SoundCaptchaEngine) Class.forName(className).newInstance();
            }
            catch (Exception e) {
                System.out.println("Couldn't initialize '" + className + "', trying a likely package prefix");
                String defaultClassPrefix = "com.octo.captcha.engine.sound.";
                try {
                    captchaEngine = (SoundCaptchaEngine) Class.forName(defaultClassPrefix + className).newInstance();
                }
                catch (Exception e2) {
                    System.out.println("Couldn't initialize '" + className + " -- specify a fully attributed name");
                    System.exit(1);
                }
            }

            SoundCaptchaToWAV.generate(iterations, captchaEngine, outputDir);
        }

        System.exit(0);
    }

    private static void clearOutputDirectory(File outputDir) {
        if (SoundCaptchaToWAV.SHOULD_DELETE_OLD_WAVS_FIRST) {
            File[] files = outputDir.listFiles();
            if (files == null) {
                return;
            }
            if (files.length > 2) {
                // skip ., .. entries
                System.out.println("Deleting about " + (files.length - 2) + " wave files");
            }
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                if (f.isFile() && f.getName().endsWith("wav")) {
                    f.delete();
                }
            }
        }
    }

    private static void generate(int iterations, SoundCaptchaEngine captchaEngine, File outputDir) throws IOException {

        outputDir.mkdirs();
        String className = captchaEngine.getClass().getName().substring(captchaEngine.getClass().getPackage().getName().length() + 1);

        System.out.println("Starting on " + className);

        long sumSoundCreation = 0;
        long sumFileCreation = 0;
        int i = 0;
        try {
            for (i = 0; i < iterations; i++) {
                long t = System.currentTimeMillis();
                SoundCaptcha captcha = captchaEngine.getNextSoundCaptcha();
                sumSoundCreation += System.currentTimeMillis() - t;
                t = System.currentTimeMillis();
                File outputFile = new File(outputDir, File.separator + className + "Captcha_" + i + ".wav");
                SoundToFile.serialize(captcha.getSoundChallenge(), outputFile);
                sumFileCreation += System.currentTimeMillis() - t;
                System.out.print(".");
                if (i % 100 == 99) {
                    System.out.println("");
                }
            }
        }
        finally {
            if (i < iterations) {
                System.out.println("exited early! i=" + i);
            } else {
                System.out.println("done");
            }
            DecimalFormat df = new DecimalFormat();
            System.out.println("Summary for " + className + ":" +
                    " avg sound creation = " + df.format(sumSoundCreation / iterations) + " milliseconds/sound," +
                    " avg file creation = " + df.format(sumFileCreation / iterations) + " milliseconds/file");
        }
    }

}
