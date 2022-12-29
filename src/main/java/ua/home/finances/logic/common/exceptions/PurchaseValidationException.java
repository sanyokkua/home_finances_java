package ua.home.finances.logic.common.exceptions;

public class PurchaseValidationException extends IllegalArgumentException {
    public PurchaseValidationException(String message) {
        super(message);
    }
}
