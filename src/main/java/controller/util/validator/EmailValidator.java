package controller.util.validator;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator extends AbstractValidator<String>{
    /**
     * Error message key, that will shown in case
     * when {@link #isError} is {@code true}
     */
    private final static String INVALID_EMAIL_KEY = "invalid.email";

    /**
     * Regex and appropriate pattern.
     * Used to perform validation of data.
     */
    private static final String EMAIL_PATTERN = "^(.+\\@.+\\..+)$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    public EmailValidator() {
        super(INVALID_EMAIL_KEY);
    }

    @Override
    public boolean isValid(String email) {
        resetErrorStatus();

        if(email == null) {
            setErrorStatus(true);
            return false;
        }

        Matcher matcher = pattern.matcher(email);
        boolean isMatches = matcher.matches();

        if(!isMatches) {
            setErrorStatus(true);
        }

        return isMatches;
    }
}
