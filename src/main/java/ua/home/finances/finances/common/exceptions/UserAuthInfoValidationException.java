package ua.home.finances.finances.common.exceptions;

public class UserAuthInfoValidationException extends IllegalArgumentException {
    public UserAuthInfoValidationException(String message) {
        super(message);
    }
}
