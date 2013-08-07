/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.module.config;

import com.octo.captcha.module.CaptchaModuleException;

import java.util.ResourceBundle;

/**
 * Configuration base class for modules.
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public class CaptchaModuleConfig {


    private static CaptchaModuleConfig instance = new CaptchaModuleConfig();

    public static CaptchaModuleConfig getInstance() {
        return instance;
    }

    /**
     * Set the message type to be a bundle
     */
    public static final String MESSAGE_TYPE_BUNDLE = "bundle";

    /**
     * Set the id to be generated
     */
    public static final String ID_GENERATED = "generated";
    /**
     * Set the message type to be a text
     */
    public static final String MESSAGE_TYPE_TEXT = "text";
    /**
     * Set the id to be retrieved from session id
     */
    public static final String ID_SESSION = "session";
    /**
     * Set the default JMX registration name
     */
    public static final String JMX_REGISTERING_NAME =
            "com.octo.captcha.module.struts:object=CaptchaServicePlugin";


    private CaptchaModuleConfig() {
    }

    private Boolean registerToMbean = Boolean.FALSE;

    private String responseKey = "jcaptcha_response";

    private String serviceClass = "com.octo.captcha.service.image.DefaultManageableImageCaptchaService";

    private String messageType = com.octo.captcha.module.config.CaptchaModuleConfig.MESSAGE_TYPE_TEXT;

    private String messageValue = "You failed the jcaptcha test";

    private String messageKey = "jcaptcha_fail";

    private String idType = com.octo.captcha.module.config.CaptchaModuleConfig.ID_SESSION;

    private String idKey = "jcaptcha_id";

    /**
     * @return The key parameter name, default jcatpcha_id
     */
    public String getIdKey() {
        return idKey;
    }

    public void setIdKey(String idKey) {
        this.idKey = idKey;
    }

    /**
     * @return The message type parameter value, default text
     */

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    /**
     * @return The message parameter value, default "you failed the captcha test"
     */
    public String getMessageValue() {
        return messageValue;
    }

    public void setMessageValue(String messageValue) {
        this.messageValue = messageValue;
    }

    /**
     * @return The message parameter key, default "jcaptcha_fail"
     */
    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }

    /**
     * @return The id generation type, default "session"
     */
    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    /**
     * @return The jcaptcha service class name, default "jcaptcha_fail"
     */
    public String getServiceClass() {
        return serviceClass;
    }

    public void setServiceClass(String serviceClass) {
        this.serviceClass = serviceClass;
    }

    public String getResponseKey() {
        return responseKey;
    }

    public void setResponseKey(String responseKey) {
        this.responseKey = responseKey;
    }

    public Boolean getRegisterToMbean() {
        return registerToMbean;
    }

    public void setRegisterToMbean(Boolean registerToMbean) {
        this.registerToMbean = registerToMbean;
    }


    public void validate() {

        //verify values
        if (!(com.octo.captcha.module.config.CaptchaModuleConfig.MESSAGE_TYPE_TEXT.equals(messageType) || com.octo.captcha.module.config.CaptchaModuleConfig.MESSAGE_TYPE_BUNDLE.equals(messageType)))
            throw new com.octo.captcha.service.CaptchaServiceException("messageType can " +
                    "only be set to '" + com.octo.captcha.module.config.CaptchaModuleConfig.MESSAGE_TYPE_TEXT + "' or '" + com.octo.captcha.module.config.CaptchaModuleConfig.MESSAGE_TYPE_BUNDLE + "'");

        if (!(com.octo.captcha.module.config.CaptchaModuleConfig.ID_SESSION.equals(idType) || com.octo.captcha.module.config.CaptchaModuleConfig.ID_GENERATED.equals(idType)))
            throw new com.octo.captcha.service.CaptchaServiceException("idType can " +
                    "only be set to '" + com.octo.captcha.module.config.CaptchaModuleConfig.ID_SESSION + "' or '" + com.octo.captcha.module.config.CaptchaModuleConfig.ID_GENERATED + "'");

        if (messageValue == null) throw new CaptchaModuleException("messageValue cannot be null");

        if (messageKey == null || "".equals(messageKey))
            throw new CaptchaModuleException("messageKey cannot be null or empty");

        if (responseKey == null || "".equals(responseKey))
            throw new CaptchaModuleException("responseKey cannot be null or empty");

        if ((idType.equals(com.octo.captcha.module.config.CaptchaModuleConfig.ID_GENERATED)) && (idKey == null || "".equals(idKey)))
            throw new com.octo.captcha.service.CaptchaServiceException("idKey cannot be null or empty when id is generated (ie idType='" + com.octo.captcha.module.config.CaptchaModuleConfig.ID_GENERATED + "'");

        //if message is in a bundle, try to load it
        if (this.messageType.equals(com.octo.captcha.module.config.CaptchaModuleConfig.MESSAGE_TYPE_BUNDLE)) {
            ResourceBundle bundle = ResourceBundle.getBundle(getMessageValue());
            if (bundle == null) {
                throw new CaptchaModuleException("can't initialize module config with a unfound bundle : "
                        + "resource bundle " + getMessageValue() + " has  not been found");
            } else {
                if (bundle.getString(getMessageKey()) == null) {
                    throw new CaptchaModuleException("can't initialize module config with a unfound message : "
                            + "resource bundle " + getMessageValue() + " has  no key named :" + getMessageKey());
                }
            }

        }

        // try to create the CaptchaService
        try {
            Class.forName(serviceClass).newInstance();
        } catch (Throwable e) {
            e.printStackTrace();
            throw new CaptchaModuleException("Error during Service Class initialization", e);
        }


    }
}
