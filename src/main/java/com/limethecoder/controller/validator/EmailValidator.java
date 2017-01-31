package com.limethecoder.controller.validator;


public class EmailValidator extends RegexValidator {
    /**
     * Error message key, that will shown in case
     * when {@link #isError} is {@code true}
     */
    private final static String INVALID_EMAIL_KEY = "invalid.email";

    private final static int MAX_LENGTH = 50;

    /**
     * Regex used to perform validation of data.
     */
    private static final String EMAIL_REGEX = "^(.+\\@.+\\..+)$";

    public EmailValidator() {
        super(EMAIL_REGEX, MAX_LENGTH, INVALID_EMAIL_KEY);
    }
}
