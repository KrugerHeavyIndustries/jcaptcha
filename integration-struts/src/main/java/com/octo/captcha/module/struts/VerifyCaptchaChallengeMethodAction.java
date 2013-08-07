/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.module.struts;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.StringTokenizer;

/**
 * Action that extends the {@link VerifyCaptchaChallengeAction} in order to enable method filtering. Gets the method
 * parameter name from parameter in actionMapping Gets the methods values to filter from attribute in actionMapping (may
 * be multiple separed by a ";").
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public class VerifyCaptchaChallengeMethodAction extends VerifyCaptchaChallengeAction {
    public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        //retrieve the method parameter name
        String parameter = actionMapping.getParameter();
        //retrieve the applicable method names
        String applicables = actionMapping.getAttribute();
        StringTokenizer token = new StringTokenizer(applicables, ";");
        //if found: process
        String methodValue = httpServletRequest.getParameter(parameter);
        if (methodValue != null) {

            while (token.hasMoreTokens()) {

                if (methodValue.equals(token.nextToken())) {
                    return super.execute(actionMapping, actionForm, httpServletRequest, httpServletResponse);
                }
            }

        }
        //else forward to success
        return actionMapping.findForward("success");
    }
}
