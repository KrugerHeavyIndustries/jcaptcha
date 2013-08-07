/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.component.image.deformation;

import com.octo.captcha.component.image.utils.ToolkitFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;

/**
 * Use an array of java.awt.image.ImageFilter to deform an image
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue </a>
 * @version 1.0
 */
public class ImageDeformationByFilters implements ImageDeformation {

    /**
     * Filters to defrom the image
     */
    private ImageFilter[] filters;

    /**
     * Constructor with an array of ImageFilter
     *
     * @param filters Filters to defrom the image
     */
    public ImageDeformationByFilters(ImageFilter[] filters) {
        super();
        this.filters = filters;
    }

    /**
     * Deforms an image
     *
     * @param image the image to be deformed
     *
     * @return the deformed image
     */
    public BufferedImage deformImage(BufferedImage image) {
        if (filters != null&&filters.length>0) {
            BufferedImage clone = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
            //clone.getGraphics().drawImage(image, 0, 0, null, null);
            FilteredImageSource filtered;
            Image temp=null;

            for (int i = 0; i < filters.length; i++) {
                ImageFilter filter = filters[i];
                filtered = new FilteredImageSource(image.getSource(), filter);
                temp = ToolkitFactory.getToolkit().createImage(filtered);
            }
            clone.getGraphics().drawImage(temp, 0, 0, null);
            clone.getGraphics().dispose();
            return clone;
        } else {
            return image;
        }
    }
}
