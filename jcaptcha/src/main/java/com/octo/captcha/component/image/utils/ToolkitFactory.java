/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.component.image.utils;

import java.awt.Toolkit;

import com.octo.captcha.CaptchaException;

/**
 * <p>Description: This Factory is used in order to switch from the java.awt.Toolkit component to other implementation
 * like <a href="http://www.eteks.com/pja/en/">PJA Toolkit</a>. By default this factory return the java.awt.Toolkit
 * object. But if the the parameter toolkit.implementation is fixed as a parameter of the virtual machine with the value
 * of the class name of another implementation of Toolkit, this factory return an implementation of this class. For
 * exemple if you set to your virtual machine -Dtoolkit.implementation=com.eteks.awt.PJAToolkit, the factory returns an
 * implementation of com.eteks.awt.PJAToolkit </p>
 * <p/>
 * see http://www.geocities.com/marcoschmidt.geo/java-image-faq.html#x for more info.
 *
 * @author <a href="mailto:mga@octo.com">Mathieu Gandin</a>
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @author <a href="antoine.veret@gmail.com">Antoine Veret</a>
 * @version 1.0
 */
public class ToolkitFactory {

    public static String TOOLKIT_IMPL = "toolkit.implementation";

    public static Toolkit getToolkit() {
        Toolkit defaultToolkit = null;

        try {
            String tempToolkitClass = System.getProperty(TOOLKIT_IMPL);
            if (tempToolkitClass != null) {
                defaultToolkit = (Toolkit)
                        Class.forName(tempToolkitClass).newInstance();
            } else {
                defaultToolkit = getDefaultToolkit();
            }

        } catch (Throwable e) {
            throw new CaptchaException("toolkit has not been abble to be "
                    + "initialized", e);

        }

        return defaultToolkit;

    }

    private static Toolkit getDefaultToolkit() {
        Toolkit defaultToolkit;
        defaultToolkit = Toolkit.getDefaultToolkit();
        return defaultToolkit;
    }

}
