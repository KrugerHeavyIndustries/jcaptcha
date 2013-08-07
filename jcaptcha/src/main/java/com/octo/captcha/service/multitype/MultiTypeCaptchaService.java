/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.service.multitype;

import com.octo.captcha.service.image.ImageCaptchaService;
import com.octo.captcha.service.sound.SoundCaptchaService;
import com.octo.captcha.service.text.TextCaptchaService;

/**
 * <p/>
 * This class is designed to provide multiTypes captcha. It is the most general service. Implementation may provide a
 * multiple engine constructor in order to provide differents typed captchas. See implementations for details. It act as
 * a proxy for the real typed service implementations. The getQuestionForId should also reslove the case of a new
 *
 * @author <a href="mailto:marc.antoine.garrigue@gmail.com">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public interface MultiTypeCaptchaService extends ImageCaptchaService, SoundCaptchaService, TextCaptchaService {

}
