package ua.home.finances.finances.common.exceptions;

public class UserWithEmailAlreadyExistsException extends IllegalArgumentException {
    public UserWithEmailAlreadyExistsException(String message) {
        super(message);
    }
}
