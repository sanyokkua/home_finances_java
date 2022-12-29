package ua.home.finances.logic.common.exceptions;

public class ParamValidationException extends IllegalArgumentException {

    public ParamValidationException(String message) {
        super(message);
    }
}
