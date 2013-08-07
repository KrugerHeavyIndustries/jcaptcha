/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.component.word;

import java.util.Locale;

/**
 * <p>Base interface used by the DictionaryWordGenerator to retrieve words.</p>
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public interface DictionaryReader {

    SizeSortedWordList getWordList();

    SizeSortedWordList getWordList(Locale locale);

}
