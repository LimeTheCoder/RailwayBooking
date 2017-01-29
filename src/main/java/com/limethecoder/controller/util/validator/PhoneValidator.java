package com.limethecoder.controller.util.validator;


public class PhoneValidator extends RegexValidator {
    private final static int MAX_LENGTH = 15;
    private final static String PHONE_REGEX = "^(\\+?[0-9]+){11,14}$";
    private final static String INVALID_PHONE = "invalid.phone";

    public PhoneValidator() {
        super(PHONE_REGEX, MAX_LENGTH, INVALID_PHONE);
    }
}
