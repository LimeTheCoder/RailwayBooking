package controller.util.validator;


public abstract class AbstractValidator<T> implements Validator<T> {
    /**
     * Default error message, that will shown in case
     * when {@link #isError} equals {@code true}
     */
    private final String ERROR_MESSAGE;
    /**
     * Indicates if error occurs in validation process
     */
    private boolean isError;

    public AbstractValidator(String errorMessage) {
        this.ERROR_MESSAGE = errorMessage;
    }

    @Override
    public String getErrorKey() {
        return isError ? ERROR_MESSAGE : null;
    }

    @Override
    public void resetErrorStatus() {
        isError = false;
    }

    public void setErrorStatus(boolean status) {
        isError = status;
    }
}
