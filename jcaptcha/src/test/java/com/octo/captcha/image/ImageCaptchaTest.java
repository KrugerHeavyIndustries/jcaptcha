/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

/*
 * jcaptcha, the open source java framework for captcha definition and integration
 * copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

/*
 * jcaptcha, the open source java framework for captcha definition and integration
 * copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */
package com.octo.captcha.image;

import com.octo.captcha.Captcha;
import com.octo.captcha.component.image.wordtoimage.SimpleWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.wordgenerator.DummyWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.image.gimpy.GimpyFactory;
import junit.framework.TestCase;

import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Field;

/**
 * <p>Description: </p>
 *
 * @author <a href="mailto:mga@octo.com">Mathieu Gandin</a>
 * @author <a href="mailto:antoine.veret@gmail.com">Antoine Véret</a>
 * @version 1.1
 */
public class ImageCaptchaTest extends TestCase {

    private ImageCaptcha pixCaptcha;

    /**
     * this method is for initialisation for all the test cases
     */
    public void setUp() throws Exception {
        super.setUp();
        WordGenerator words = new DummyWordGenerator("TESTING");
        WordToImage word2image = new SimpleWordToImage();
        ImageCaptchaFactory factory = new GimpyFactory(words, word2image);
        pixCaptcha = factory.getImageCaptcha();
    }

    /**
     * This test is for verifying if the question of the captcha is correctly instantiated.
     */
    public void testGetQuestion() {
        assertNotNull(pixCaptcha.getQuestion());
    }

    /**
     * This test is for verifying if the challenge of the captcha is correctly instantiated.
     */
    public void testGetChallenge() {
        assertNotNull(pixCaptcha.getChallenge());
        assertTrue("Captcha challenge is not a BufferedImage", pixCaptcha.getImageChallenge() instanceof BufferedImage);
    }

    public void testDisposeChallenge() {
        pixCaptcha.disposeChallenge();
        assertNull(pixCaptcha.getChallenge());
    }

    /**
     * This test is for verifying if the response of the captcha is valid.
     */
    public void testValidateResponse() throws Exception {
        
        assertFalse(pixCaptcha.validateResponse("dummyResponse").booleanValue());

        Field responseField = pixCaptcha.getClass().getDeclaredField("response");
        responseField.setAccessible(true);
        String response = (String) responseField.get(pixCaptcha);

        assertTrue(pixCaptcha.validateResponse(response).booleanValue());
    }


    public void testGetImageChallenge() throws Exception {
        assertFalse(pixCaptcha.hasGetChalengeBeenCalled().booleanValue());
        assertEquals(pixCaptcha.getImageChallenge(), pixCaptcha.getChallenge());
        assertTrue(pixCaptcha.hasGetChalengeBeenCalled().booleanValue());
    }

    public void testUnMarshalling() throws Exception {

        byte[] marshalledCaptcha = marshalCaptcha(pixCaptcha);
        Captcha captchaUnserialized = unmarshalCaptcha(marshalledCaptcha);

        assertNotNull(captchaUnserialized);
        assertEquals(pixCaptcha.getQuestion(), captchaUnserialized.getQuestion());
        assertFalse(captchaUnserialized.hasGetChalengeBeenCalled().booleanValue());
        assertTrue(captchaUnserialized.getChallenge() instanceof BufferedImage);
        assertTrue(captchaUnserialized.hasGetChalengeBeenCalled().booleanValue());        
    }

    public void testUnMarshallingWithGetChallenge() throws Exception {

        pixCaptcha.getChallenge(); // get the image challenge first

        byte[] marshalledCaptcha = marshalCaptcha(pixCaptcha);
        Captcha captchaUnserialized = unmarshalCaptcha(marshalledCaptcha);

        assertNotNull(captchaUnserialized);
        assertNotNull(captchaUnserialized.getChallenge());
        assertTrue(captchaUnserialized.hasGetChalengeBeenCalled().booleanValue());
    }

    public void testUnMarshallingWithDisposedChallenge() throws Exception {

        pixCaptcha.getChallenge(); // get the image challenge first
        pixCaptcha.disposeChallenge();

        byte[] marshalledCaptcha = marshalCaptcha(pixCaptcha);
        Captcha captchaUnserialized = unmarshalCaptcha(marshalledCaptcha);

        assertNotNull(captchaUnserialized);
        assertNull(captchaUnserialized.getChallenge());
        assertTrue(captchaUnserialized.hasGetChalengeBeenCalled().booleanValue());
    }

    private Captcha unmarshalCaptcha(byte[] marshalledCaptcha) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new
                ByteArrayInputStream(marshalledCaptcha));
        Captcha captchaUnserialized = (Captcha) in.readObject();
        return captchaUnserialized;
    }

    private byte[] marshalCaptcha(Captcha captcha) throws IOException {
        ByteArrayOutputStream arrayOutput = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(arrayOutput);
        out.writeObject(captcha);
        out.flush();
        arrayOutput.close();
        return arrayOutput.toByteArray();
    }
}
