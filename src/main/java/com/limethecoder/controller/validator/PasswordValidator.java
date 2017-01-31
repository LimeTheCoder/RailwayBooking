package com.limethecoder.controller.validator;


public class PasswordValidator extends AbstractValidator<String> {
    private final static String INVALID_PASSWORD = "invalid.password";
    private final static int PASSWORD_MIN_LENGTH = 6;
    private final static int PASSWORD_MAX_LENGTH = 50;

    public PasswordValidator() {
        super(INVALID_PASSWORD);
    }

    @Override
    public boolean isValid(String password) {
        resetErrorStatus();
        if(password == null || password.length() < PASSWORD_MIN_LENGTH ||
                password.length() > PASSWORD_MAX_LENGTH) {
            setErrorStatus(true);
            return false;
        }

        return true;
    }
}
