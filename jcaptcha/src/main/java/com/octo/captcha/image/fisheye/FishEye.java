/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.image.fisheye;

import com.octo.captcha.image.ImageCaptcha;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.StringTokenizer;

/**
 * FishEye is an ImageCaptcha <ul> <li>Challenge type : image</li> <li>Response type : a point position, in pixels from
 * the bottom left, can be : a String with two numbers separated with a comma or a java.awt.Point</li> <li>Description :
 * An image of a distorded picture. User have to point the center of the deformation and to submit it.</li> </ul>
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public class FishEye extends ImageCaptcha {

    private Point deformationCenter;
    private Integer tolerance;

    /**
     * @param question          the question
     * @param challenge         the imageChallenge
     * @param deformationCenter the center of the deformation that has been applied to the image in order to validate
     *                          the answer
     * @param tolerance         the max distance to the center of the deformation accepted by the validation routine in
     *                          pixels.
     */
    protected FishEye(String question, BufferedImage challenge,
                      Point deformationCenter, Integer tolerance) {
        super(question, challenge);
        this.deformationCenter = deformationCenter;
        this.tolerance = tolerance;
    }

    /**
     * Validation routine for the response.
     *
     * @param response to the question concerning the chalenge
     *
     * @return true if the answer is correct, false otherwise.
     */
    public Boolean validateResponse(Object response) {
        //if a point go
        if (response instanceof Point) {
            Point point = (Point) response;
            return validateResponse(point);
            //else if string response
        } else if (response instanceof String) {
            String s = (String) response;
            //ty to parse it
            try {

                //String[] coordonates = new String[2];
                StringTokenizer token = new StringTokenizer(s, ",");

                Point point = new Point(Integer.parseInt(token.nextToken()),
                        Integer.parseInt(token.nextToken()));
                return validateResponse(point);
            } catch (Throwable e) {
                //catch all and return false
                return Boolean.FALSE;
            }
        } else {
            return Boolean.FALSE;
        }

    }

    /**
     * Real validation
     *
     * @param point the given point
     *
     * @return true if distance from the given point and the deformation center is less than tolerance, false otherwise
     */
    private Boolean validateResponse(Point point) {

        if (point.distance(deformationCenter) <= tolerance.doubleValue()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;

    }

}
