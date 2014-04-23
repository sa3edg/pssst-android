package com.qafeerlabs.pssst.exception;

public class HttpRequestException extends RuntimeException {
    static final long serialVersionUID = 1;

    /**
     * Constructs a new HttpRequestException.
     */
    public HttpRequestException() {
        super();
    }

    /**
     * Constructs a new HttpRequestException.
     * 
     * @param message
     *            the detail message of this exception
     */
    public HttpRequestException(String message) {
        super(message);
    }

    /**
     * Constructs a new HttpRequestException.
     * 
     * @param message
     *            the detail message of this exception
     * @param throwable
     *            the cause of this exception
     */
    public HttpRequestException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     * Constructs a new HttpRequestException.
     * 
     * @param throwable
     *            the cause of this exception
     */
    public HttpRequestException(Throwable throwable) {
        super(throwable);
    }
}
