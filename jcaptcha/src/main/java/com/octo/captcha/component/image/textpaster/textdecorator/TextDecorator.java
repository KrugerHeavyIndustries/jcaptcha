/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.component.image.textpaster.textdecorator;

import com.octo.captcha.component.image.textpaster.ChangeableAttributedString;

import java.awt.*;
import java.text.AttributedString;

/**
 * <p><ul><li></li></ul></p>
 *
 * @author <a href="mailto:marc.antoine.garrigue@gmail.com">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public interface TextDecorator {
    void decorateAttributedString(Graphics2D g2, AttributedString attributedWord, ChangeableAttributedString newAttrString);
}
