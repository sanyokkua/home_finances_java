package ua.home.finances.logic.common.exceptions;

public class UserIsNotFoundException extends IllegalArgumentException {
    public UserIsNotFoundException(String message) {
        super(message);
    }
}
