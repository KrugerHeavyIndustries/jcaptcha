/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.module.config;

import javax.servlet.http.HttpServletRequest;
import java.util.ResourceBundle;

/**
 * Class that provides static utility method to use the Module configuration.
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public class CaptchaModuleConfigHelper {

    /**
     * method that get an id using the plugin configuration.<br/> It may be retrieved from the session via the getId()
     * native method if the idType is set to 'session' or retrieved from the request using the 'idKey' parameter if the
     * idType is set to 'generated';
     */
    public static String getId(HttpServletRequest httpServletRequest) {
        String captchaID;
        boolean generatedId = CaptchaModuleConfig.getInstance().getIdType().equals(CaptchaModuleConfig.ID_GENERATED) ?
                true : false;

        if (generatedId) {
            //get it from the request
            captchaID = httpServletRequest.getParameter(CaptchaModuleConfig.getInstance().getIdKey());

        } else {
            //get captcha ID from the session!!
            captchaID = httpServletRequest.getSession().getId();//theRequest.getParameter(captchaIDParameterName);
        }
        return captchaID;
    }

    /**
     * Method that return the fail or error message from the specified bundle or directly from the value specified
     */

    public static String getMessage(HttpServletRequest httpServletRequest) {
        String message = null;
        boolean messageBundle =
                CaptchaModuleConfig.getInstance().getMessageType().equals(CaptchaModuleConfig.MESSAGE_TYPE_BUNDLE) ?
                        true : false;
        //get it from the bundle with the specified locale
        if (messageBundle) {
            ResourceBundle bundle = ResourceBundle.getBundle(CaptchaModuleConfig.getInstance().getMessageValue(),
                    httpServletRequest.getLocale());
            if (bundle != null) {
                message = bundle.getString(CaptchaModuleConfig.getInstance().getMessageKey());
            }
            //get it with no locale if still null
            if (message == null) {
                bundle = ResourceBundle.getBundle(CaptchaModuleConfig.getInstance().getMessageValue());
                message = bundle.getString(CaptchaModuleConfig.getInstance().getMessageKey());
            }

        } else {
            //directly get the value
            message = CaptchaModuleConfig.getInstance().getMessageValue();
        }
        return message;
    }

}
