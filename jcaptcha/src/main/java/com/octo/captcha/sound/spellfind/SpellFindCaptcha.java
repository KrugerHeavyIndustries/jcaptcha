/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.sound.spellfind;

import com.octo.captcha.sound.SoundCaptcha;

import javax.sound.sampled.AudioInputStream;

/**
 * <p><ul><li></li></ul></p>
 *
 * @author <a href="mailto:marc.antoine.garrigue@gmail.com">Marc-Antoine Garrigue</a>
 * @version $Id: SpellFindCaptcha.java 471 2008-02-11 21:40:12Z antoineveret $
 */
public class SpellFindCaptcha extends SoundCaptcha {

     private String response;

      public SpellFindCaptcha(String thequestion,
                          AudioInputStream thechallenge, String theresponse) {
          super(thequestion, thechallenge);
          this.response = theresponse;
      }

      public Boolean validateResponse(Object theresponse) {
          if ((theresponse != null) && (theresponse instanceof String)) {
              return this.validateResponse((String) theresponse);
          } else {
              return Boolean.FALSE;
          }
      }

      public Boolean validateResponse(String theresponse) {
          return Boolean.valueOf(this.response.equalsIgnoreCase(theresponse));
      }

}
