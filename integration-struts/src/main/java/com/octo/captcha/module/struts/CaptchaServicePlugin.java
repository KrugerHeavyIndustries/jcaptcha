/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.module.struts;

import com.octo.captcha.module.config.CaptchaModuleConfig;
import com.octo.captcha.module.jmx.JMXRegistrationHelper;
import com.octo.captcha.service.CaptchaService;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.ManageableCaptchaService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

import javax.servlet.ServletException;

/**
 * Struts plugin, uses the module config.
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 * @see com.octo.captcha.module.config.CaptchaModuleConfig
 */
public class CaptchaServicePlugin implements PlugIn {

    private static CaptchaServicePlugin instance;

    public static CaptchaServicePlugin getInstance() {
        return instance;
    }

    private static CaptchaService service;
    private Log log = LogFactory.getLog(CaptchaServicePlugin.class);
    private ActionServlet servlet;


    public CaptchaService getService() {
        return service;
    }

    private CaptchaModuleConfig captchaModuleConfig;

    //~ Methods ================================================================

    public CaptchaServicePlugin() {
        captchaModuleConfig = CaptchaModuleConfig.getInstance();
    }

    public void init(ActionServlet servlet, ModuleConfig config)
            throws ServletException {
        instance = this;

        if (log.isDebugEnabled()) {
            log.debug("Starting struts-captcha plugin initialization");
        }

        this.servlet = servlet;

        //validate configuration
        captchaModuleConfig.validate();

        // create the CaptchaService
        try {
            service = (CaptchaService)
                    Class.forName(captchaModuleConfig.getServiceClass()).newInstance();
        } catch (InstantiationException e) {
            log.error("Error during Service Class initialization", e);

            throw new CaptchaServiceException(e);
        } catch (IllegalAccessException e) {
            log.error("Error during Service Class initialization", e);

            throw new CaptchaServiceException(e);
        } catch (ClassNotFoundException e) {
            log.error("Error during Service Class initialization", e);
            throw new CaptchaServiceException(e);
        }

        // register the CaptchaService to an MBean server if specified
        if (captchaModuleConfig.getRegisterToMbean().booleanValue()
                && service instanceof ManageableCaptchaService) {
            ManageableCaptchaService manageable =
                    (ManageableCaptchaService) service;
            JMXRegistrationHelper.registerToMBeanServer(manageable,
                    CaptchaModuleConfig.JMX_REGISTERING_NAME);
        }
        if (log.isDebugEnabled()) {
            log.debug("struts-captcha plugin initialization successfull");
        }
    }

    public void destroy() {
        if (service instanceof ManageableCaptchaService &&
                captchaModuleConfig.getRegisterToMbean().booleanValue()) {
            JMXRegistrationHelper.unregisterFromMBeanServer(
                    CaptchaModuleConfig.JMX_REGISTERING_NAME);
        }

    }

    //*******
    //delegate to module config
    //*****

    public String getIdKey() {
        return captchaModuleConfig.getIdKey();
    }

    public void setIdKey(String idKey) {
        captchaModuleConfig.setIdKey(idKey);
    }

    public String getMessageType() {
        return captchaModuleConfig.getMessageType();
    }

    public void setMessageType(String messageType) {
        captchaModuleConfig.setMessageType(messageType);
    }

    public String getMessageValue() {
        return captchaModuleConfig.getMessageValue();
    }

    public void setMessageValue(String messageValue) {
        captchaModuleConfig.setMessageValue(messageValue);
    }

    public String getMessageKey() {
        return captchaModuleConfig.getMessageKey();
    }

    public void setMessageKey(String messageKey) {
        captchaModuleConfig.setMessageKey(messageKey);
    }

    public String getIdType() {
        return captchaModuleConfig.getIdType();
    }

    public void setIdType(String idType) {
        captchaModuleConfig.setIdType(idType);
    }

    public String getServiceClass() {
        return captchaModuleConfig.getServiceClass();
    }

    public void setServiceClass(String serviceClass) {
        captchaModuleConfig.setServiceClass(serviceClass);
    }

    public String getResponseKey() {
        return captchaModuleConfig.getResponseKey();
    }

    public void setResponseKey(String responseKey) {
        captchaModuleConfig.setResponseKey(responseKey);
    }

    public Boolean getRegisterToMbean() {
        return captchaModuleConfig.getRegisterToMbean();
    }

    public void setRegisterToMbean(Boolean registerToMbean) {
        captchaModuleConfig.setRegisterToMbean(registerToMbean);
    }


}
