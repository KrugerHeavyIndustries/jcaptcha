/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.component.word;

import java.util.Locale;

/**
 * <p><ul><li></li></ul></p>
 *
 * @author <a href="mailto:marc.antoine.garrigue@gmail.com">Marc-Antoine Garrigue</a>
 * @version $Id: SizeSortedWordList.java 322 2007-03-26 17:45:25Z antoineveret $
 */
public interface SizeSortedWordList {
    Locale getLocale();

    void addWord(String word);

    Integer getMinWord();

    Integer getMaxWord();

    String getNextWord(Integer length);
}
