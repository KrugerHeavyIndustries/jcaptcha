/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.module.roller;

import com.octo.captcha.module.config.CaptchaModuleConfigHelper;
import com.octo.captcha.module.struts.CaptchaServicePlugin;
import com.octo.captcha.service.CaptchaService;
import com.octo.captcha.service.CaptchaServiceException;
import org.roller.presentation.velocity.CommentAuthenticator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: mag Date: 17 oct. 2004 Time: 17:36:22
 */
public class JCaptchaCommentAuthenticator implements CommentAuthenticator {

    private static final String htmlheader =
            "<table cellspacing=\"0\" cellpadding=\"1\" border=\"0\" width=\"95%\"><tr><th width=\"116\">";

    private static final String htmlendheader =
            ":</th>";
    private static final String htmlinput =
            "<td>" +
                    "<input type=\"text\" name=\"";
    private static final String htmlendinput =
            "\" " + "size=\"50\" maxlength=\"255\" /></td></tr></table>";
    private static final String htmlChallenge =
            "<table cellspacing=\"0\" cellpadding=\"1\" border=\"0\" width=\"95%\"><tr><td><img src=\"";

    private static final String htmlendChallenge =
            "\"></td></tr>" +
                    "</table>";


    public String getHtml(org.apache.velocity.context.Context context, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String captchaID = CaptchaModuleConfigHelper.getId(httpServletRequest);
        String question = CaptchaServicePlugin.getInstance().getService().getQuestionForID(captchaID, httpServletRequest.getLocale());
        String challengeUrl = context.get("ctxPath") + "/jcaptcha.do";
        String responseKey = CaptchaServicePlugin.getInstance().getResponseKey();
        StringBuffer html = new StringBuffer();
        html.append(htmlheader);
        html.append(question);
        html.append(htmlendheader);
        html.append(htmlinput);
        html.append(responseKey);
        html.append(htmlendinput);
        html.append(htmlChallenge);
        html.append(challengeUrl);
        html.append(htmlendChallenge);
        return html.toString();
    }

    public boolean authenticate(org.roller.pojos.CommentData commentData, HttpServletRequest httpServletRequest) {

        CaptchaService service = CaptchaServicePlugin.getInstance().getService();

        String responseKey = CaptchaServicePlugin.getInstance().getResponseKey();

        String captchaID;

        captchaID = CaptchaModuleConfigHelper.getId(httpServletRequest);

        // get challenge response from the request
        String challengeResponse =
                httpServletRequest.getParameter(responseKey);

        //cleanning the request
        httpServletRequest.removeAttribute(responseKey);
        Boolean isResponseCorrect = Boolean.FALSE;
        if (challengeResponse != null) {
            // Call the Service method
            try {
                isResponseCorrect = service.validateResponseForID(captchaID,
                        challengeResponse);
            } catch (CaptchaServiceException e) {
                e.printStackTrace();
            }
        }
        // return
        return isResponseCorrect.booleanValue();
    }
}
