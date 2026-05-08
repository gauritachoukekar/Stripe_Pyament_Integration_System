package com.hulkhiretech.payments.exception;

import org.springframework.http.HttpStatus;

/**
 * Custom exception for HMAC validation failures.
 * Used when:
 *  - Missing HMAC header
 *  - Tampered data
 *  - Invalid signature
 */
public class HmacValidationException extends RuntimeException {

    private final String errorCode;
    private final String errorMessage;
    private final HttpStatus httpStatus;

    /**
     * Constructor for HMAC validation errors.
     * @param message custom message describing the error
     */
    public HmacValidationException(String message) {
        super(message);
        this.errorCode = "HMAC_INVALID";          // constant error code
        this.errorMessage = message;              // detailed message
        this.httpStatus = HttpStatus.BAD_REQUEST; // HTTP 400
    }

    // Getters
    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
