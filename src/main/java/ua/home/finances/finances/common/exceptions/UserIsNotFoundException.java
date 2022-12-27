package ua.home.finances.finances.common.exceptions;

public class UserIsNotFoundException extends IllegalArgumentException {
    public UserIsNotFoundException(String message) {
        super(message);
    }
}
