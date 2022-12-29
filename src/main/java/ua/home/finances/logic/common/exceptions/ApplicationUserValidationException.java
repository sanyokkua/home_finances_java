package ua.home.finances.logic.common.exceptions;

public class ApplicationUserValidationException extends IllegalArgumentException {
    public ApplicationUserValidationException(String message) {
        super(message);
    }
}
