/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.component.image.deformation;

import com.jhlabs.image.RotateFilter;
import com.octo.captcha.component.image.utils.ToolkitFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Deformation where the image is divided in little squares, moved individualty in a random position. Each move is
 * really light, in order to let the captcha readble.
 *
 * @author Benoit Doumas
 */
public class PuzzleImageDeformation implements ImageDeformation {

    /**
     * Number of colums to divide the image, max number if rows and colums are managed randomly
     */
    private int colNum = 6;

    /**
     * Number of rows to divide the image, max number if rows and colums are managed randomly
     */
    private int rowNum = 4;

    /**
     * Maximal angle of rotation for each square.
     */
    private double maxAngleRotation = 0.3;

    private Random random = new SecureRandom();

    /**
     * Conststruct a PuzzleImageDeformation, with the numbers of colums and rows. If manageRowAndColRandomly is set to
     * true, the numbers of rows and colums are choosed between 1 and colNum/rowNum
     *
     * @param colNum                  Number of colums to divide the image, max number if rows and colums are managed
     *                                randomly
     * @param rowNum                  Number of rows to divide the image, max number if rows and colums are managed
     *                                randomly
     * @param maxAngleRotation        Maximal angle of rotation for each square.
     */
    public PuzzleImageDeformation(int colNum, int rowNum, double maxAngleRotation) {
        super();
        this.colNum = colNum;
        this.rowNum = rowNum;
        this.maxAngleRotation = maxAngleRotation;
    }

    /*
     * @see com.octo.captcha.component.image.deformation.ImageDeformation#deformImage(java.awt.image.BufferedImage)
     */
    public BufferedImage deformImage(BufferedImage image) {

        int height = image.getHeight();
        int width = image.getWidth();

        int xd = width / colNum;
        int yd = height / rowNum;

        BufferedImage backround = new BufferedImage(width, height, image.getType());
        Graphics2D pie = (Graphics2D) backround.getGraphics();

        pie.setColor(Color.white);
        pie.setBackground(Color.white);
        pie.fillRect(0, 0, width, height);
        pie.dispose();

        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setBackground(Color.white);

        BufferedImage smallPart = new BufferedImage(xd, yd, image.getType());
        Graphics2D gSmall = smallPart.createGraphics();
        FilteredImageSource filtered;

        for (int i = 0; i < colNum; i++) {
            for (int j = 0; j < rowNum; j++) {
                gSmall.drawImage(image, 0, 0, xd, yd, xd * i, yd * j, xd * i + xd, yd * j + yd,
                        null);

                ImageFilter filter = new RotateFilter(maxAngleRotation * random.nextDouble()
                        * (random.nextBoolean() ? -1 : 1));

                filtered = new FilteredImageSource(smallPart.getSource(), filter);
                Image temp = ToolkitFactory.getToolkit().createImage(filtered);
                smallPart.getGraphics().drawImage(temp, 0, 0, new Color(0, 0, 0, 0), null);

                smallPart.getGraphics().dispose();

                g.drawImage(smallPart, xd * i, yd * j, null, null);
            }
        }

        return image;
    }

}
