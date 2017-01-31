package com.limethecoder.controller.validator;


/**
 * Interface that describes validator.
 * Validator interface provides methods to perform validation of data and
 * for getting error message key in case if input is not valid.
 *
 * @author Taras Sakharchuk
 */
public interface Validator<T> {
    /**
     * Method that get error status of validation process
     *
     * @return error message in case if input isn't valid,
     *         otherwise return {@code null}
     */
    String getErrorKey();

    /**
     * Check is input message is valid
     *
     * @param obj that need to check
     * @return {@code true} if input is valid
     *         {@code false} if input is not valid
     */
    boolean isValid(T obj);

    /**
     * Set error state to initial value.
     */
    void resetErrorStatus();
}
