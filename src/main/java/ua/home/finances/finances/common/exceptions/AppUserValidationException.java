package ua.home.finances.finances.common.exceptions;

public class AppUserValidationException extends IllegalArgumentException {
    public AppUserValidationException(String message) {
        super(message);
    }
}
