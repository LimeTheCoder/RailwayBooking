package controller.util.validator;


import java.util.regex.Pattern;

public class RegexValidator extends AbstractValidator<String> {
    private int maxLength;
    private Pattern pattern;

    public RegexValidator(String regex, int maxLength, String errorMessage) {
        super(errorMessage);
        this.maxLength = maxLength;
        this.pattern = Pattern.compile(regex);
    }

    @Override
    public boolean isValid(String str) {
        resetErrorStatus();

        if(str == null || str.length() >= maxLength) {
            setErrorStatus(true);
            return false;
        }

        boolean isMatches = pattern.matcher(str).matches();

        if(!isMatches) {
            setErrorStatus(true);
        }

        return isMatches;
    }
}
