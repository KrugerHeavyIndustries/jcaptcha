/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.module.struts;

import com.octo.captcha.module.config.CaptchaModuleConfigHelper;
import com.octo.captcha.service.CaptchaService;
import com.octo.captcha.service.CaptchaServiceException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action that verify a captcha response using the response key to retrieve the response. Forwads to success if passed,
 * to failure otherwise if exists or to input page adding the message to the corresponding key
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 * @see com.octo.captcha.module.config.CaptchaModuleConfig for the key names
 */
public class VerifyCaptchaChallengeAction extends Action {

    private Log log = LogFactory.getLog(VerifyCaptchaChallengeAction.class);

    public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm,
                                 HttpServletRequest httpServletRequest,
                                 HttpServletResponse httpServletResponse)
            throws Exception {

        log.debug("enter captcha challenge verification");

        CaptchaService service = CaptchaServicePlugin.getInstance().getService();

        String responseKey = CaptchaServicePlugin.getInstance().getResponseKey();

        String captchaID;

        captchaID = CaptchaModuleConfigHelper.getId(httpServletRequest);

        // get challenge response from the request
        String challengeResponse =
                httpServletRequest.getParameter(responseKey);

        if (log.isDebugEnabled()) log.debug("response for id " + captchaID
                + " : " + challengeResponse);

        //cleanning the request
        httpServletRequest.removeAttribute(responseKey);

        Boolean isResponseCorrect = Boolean.FALSE;

        if (challengeResponse != null) {

            // Call the Service method
            try {
                isResponseCorrect = service.validateResponseForID(captchaID,
                        challengeResponse);
            } catch (CaptchaServiceException e) {

                log.debug("Error during challenge verification", e);
                // so the user will be redirected to the error page
                httpServletRequest.setAttribute(CaptchaServicePlugin.getInstance().getMessageKey(),
                        CaptchaModuleConfigHelper.getMessage(httpServletRequest));

                log.debug("forward to error with message : "
                        + CaptchaModuleConfigHelper.getMessage(httpServletRequest));

                return actionMapping.findForward("error");
            }
        }
        // forward user to the success URL or redirect it to the error URL
        if (isResponseCorrect.booleanValue()) {
            // clean the request and call the next action
            // (forward success)
            log.debug("correct : forward to success");
            return actionMapping.findForward("success");
        } else {
            if (log.isDebugEnabled()) {
                log.debug("false  : forward to failure with message : "
                        + CaptchaModuleConfigHelper.getMessage(httpServletRequest));
                log.debug("in request attribute key : "
                        + CaptchaServicePlugin.getInstance().getMessageKey());
            }
            // If the challenge response is not specified, forward failure
            httpServletRequest.setAttribute(CaptchaServicePlugin.getInstance().getMessageKey(),
                    CaptchaModuleConfigHelper.getMessage(httpServletRequest));
            return actionMapping.findForward("failure") != null ? actionMapping.findForward("failure") :
                    actionMapping.getInputForward();
        }

    }


}
