package controller.util.validator;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateValidator extends AbstractValidator<String> {
    private final static String INVALID_DATE_KEY = "invalid.date";
    private SimpleDateFormat formatter;
    private Date date;


    public DateValidator(String pattern) {
        super(INVALID_DATE_KEY);
        formatter = new SimpleDateFormat(pattern);
    }

    @Override
    public boolean isValid(String dateString) {
        resetErrorStatus();
        try {
            date = formatter.parse(dateString);
            return true;
        } catch (ParseException e) {
            setErrorStatus(true);
            return false;
        }
    }

    public Date getParsedDate() {
        return date;
    }
}
