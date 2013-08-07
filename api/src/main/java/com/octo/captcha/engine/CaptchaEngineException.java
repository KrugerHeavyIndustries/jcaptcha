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
package com.octo.captcha.engine;

/**
 * Captcha Engine Exception
 *
 * @author <a href="mailto:marc.antoine.garrigue@gmail.com">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public class CaptchaEngineException extends RuntimeException {
    private Throwable cause;

    /**
     * Constructs a new exception with <code>null</code> as its detail message. The cause is not initialized.
     */
    public CaptchaEngineException() {
    }

    /**
     * Constructs a new exception with the specified detail message.  The cause is not initialized
     *
     * @param message the detail message. The detail message is saved for later retrieval by the {@link #getMessage()}
     *                method.
     */
    public CaptchaEngineException(final String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and cause. <p>Note that the detail message
     * associated with <code>cause</code> is <i>not</i> automatically incorporated in this exception's detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the {@link #getCause()} method).  (A
     *                <tt>null</tt> value is permitted, and indicates that the cause is nonexistent or unknown.)
     *
     * @since 1.4
     */
    public CaptchaEngineException(final String message, final Throwable cause) {
        super(message);
        this.cause = cause;
    }

    /**
     * Constructs a new exception with the specified cause and a detail message of <tt>(cause==null ? null :
     * cause.toString())</tt> (which typically contains the class and detail message of <tt>cause</tt>). This
     * constructor is useful for exceptions that are little more than wrappers for other throwables (for example, {@link
     * java.security.PrivilegedActionException}).
     *
     * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method).  (A <tt>null</tt>
     *              value is permitted, and indicates that the cause is nonexistent or unknown.)
     *
     * @since 1.4
     */
    public CaptchaEngineException(final Throwable cause) {
        super(cause.getMessage());
        this.cause = cause;
    }

    /**
     * @return the root thowable that construct this exception, null if none
     */
    public Throwable getCause() {
        return cause;
    }
}
