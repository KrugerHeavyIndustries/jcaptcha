/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.component.image.deformation;

import java.awt.image.BufferedImage;

/**
 * An image deformation takes an image, deforms it, and returns it.
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public interface ImageDeformation {

    /**
     * Deforms an image
     *
     * @param image the image to be deformed
     *
     * @return the deformed image
     */
    BufferedImage deformImage(BufferedImage image);

}
