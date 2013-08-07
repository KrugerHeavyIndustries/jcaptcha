/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.service;

/**
 * <p>To be thrown when a service user is doing an illegal operation (typically wrang operation flow errors).</p>
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public class CaptchaServiceException extends RuntimeException {

    private Throwable cause;

    /**
     * Constructs a new exception with the specified detail message.  The cause is not initialized
     *
     * @param message the detail message. The detail message is saved for later retrieval by the {@link #getMessage()}
     *                method.
     */
    public CaptchaServiceException(final String message) {
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
    public CaptchaServiceException(final String message, final Throwable cause) {
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
    public CaptchaServiceException(final Throwable cause) {
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
