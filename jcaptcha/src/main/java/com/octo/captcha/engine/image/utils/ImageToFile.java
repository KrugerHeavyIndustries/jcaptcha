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

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * <p>Description: </p>
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public class ImageToFile {

    public ImageToFile() {
    }

    public static void serialize(BufferedImage image, File file)
            throws IOException {
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        encodeJPG(fos, image);
        fos.flush();
        fos.close();
    }

    public static void encodeJPG(OutputStream sos, BufferedImage image)
            throws IOException {
        JPEGImageEncoder encoder =
                JPEGCodec.createJPEGEncoder(sos);

        JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(image);
        //        param.setHorizontalSubsampling(0, 1);
        //        param.setHorizontalSubsampling(1, 1);
        //        param.setHorizontalSubsampling(2, 1);
        //        param.setVerticalSubsampling(0, 1);
        //        param.setVerticalSubsampling(1, 1);
        //        param.setVerticalSubsampling(2, 1);
        param.setQuality(1.0f, false);
        encoder.setJPEGEncodeParam(param);
        encoder.encode(image);
        encoder.getOutputStream().close();
    }

}

