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

package com.octo.captcha.engine.image.utils;

import com.octo.captcha.engine.image.ImageCaptchaEngine;
import com.octo.captcha.engine.image.fisheye.SimpleFishEyeEngine;
import com.octo.captcha.engine.image.gimpy.*;
import com.octo.captcha.image.ImageCaptcha;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * This utility class lets you create JPEG files with a particular
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @author <a href="mailto:travis.winfrey@gmail.com">Travis Winfrey</a>
 * @version 1.0
 */
public class ImageCaptchaToJPEG {

    private static boolean SHOULD_DELETE_OLD_JPEGS_FIRST = true;

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Usage : engineClassName outputDir iterations");
            System.out.println("If engineClassName is 'all', then several Gimpy Engines are used");
            System.exit(1);
        }


        String className = args[0];
        File outputDir = new File(args[1]);
        String iterationsString = args[2];
        int iterations = Integer.parseInt(iterationsString);

        System.out.println("args : " +
                "image captcha engine class='" + className + "'" +
                ", output dir='" + outputDir + "'" +
                ",iterations='" + iterationsString + "'");

        clearOutputDirectory(outputDir);

        ImageCaptchaEngine pixCapchaEngine = null;
        if (className.equals("all")) {
            ImageCaptchaEngine[] engines = {
                    new BasicListGimpyEngine(),
                    new BaffleListGimpyEngine(),
                    new DefaultGimpyEngine(),
                    new DeformedBaffleListGimpyEngine(),
                    new DoubleRandomListGimpyEngine(),
                    new SimpleListImageCaptchaEngine(),
                    new SimpleFishEyeEngine()
            };
            for (int i = 0; i < engines.length; i++) {
                pixCapchaEngine = engines[i];
                System.out.println("Beginning generation with " + pixCapchaEngine.getClass().getName());
                try {
                    generate(iterations, pixCapchaEngine, outputDir);
                }
                catch (Exception e) {
                    System.out.println("Errors with class " + pixCapchaEngine.getClass().getName());
                }
            }
        } else {

            try {
                pixCapchaEngine = (ImageCaptchaEngine) Class.forName(className).newInstance();
            }
            catch (Exception e) {
                System.out.println("Couldn't initialize '" + className + "', trying a likely package prefix");
                String defaultClassPrefix = "com.octo.captcha.engine.image.gimpy.";
                try {
                    pixCapchaEngine = (ImageCaptchaEngine) Class.forName(defaultClassPrefix + className).newInstance();
                }
                catch (Exception e2) {
                    System.out.println("Couldn't initialize '" + className + " -- specify a fully attributed name");
                    System.exit(1);
                }
            }

            generate(iterations, pixCapchaEngine, outputDir);
        }

        System.exit(0);
    }

    private static void clearOutputDirectory(File outputDir) {
        if (SHOULD_DELETE_OLD_JPEGS_FIRST) {
            File[] files = outputDir.listFiles();
            if (files == null) {
                return;
            }
            if (files.length > 2) {
                // skip ., .. entries
                System.out.println("Deleting about " + (files.length - 2) + " jpeg files");
            }
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                if (f.isFile() && f.getName().endsWith("jpg")) {
                    f.delete();
                }
            }
        }
    }

    private static void generate(int iterations, ImageCaptchaEngine pixCaptchaEngine, File outputDir) throws IOException {

        outputDir.mkdirs();
        String className = pixCaptchaEngine.getClass().getName().substring(pixCaptchaEngine.getClass().getPackage().getName().length() + 1);

        System.out.println("Starting on " + className);

        long sumImageCreation = 0;
        long sumFileCreation = 0;
        int i = 0;
        try {
            for (i = 0; i < iterations; i++) {
                long t = System.currentTimeMillis();
                ImageCaptcha captcha = pixCaptchaEngine.getNextImageCaptcha();
                sumImageCreation += System.currentTimeMillis() - t;
                t = System.currentTimeMillis();
                File outputFile = new File(outputDir, File.separator + className + "Captcha_" + i + ".jpg");
                ImageToFile.serialize(captcha.getImageChallenge(), outputFile);
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
                    " avg image creation = " + df.format(sumImageCreation / iterations) + " milliseconds/image," +
                    " avg file creation = " + df.format(sumFileCreation / iterations) + " milliseconds/file");
        }
    }

}
