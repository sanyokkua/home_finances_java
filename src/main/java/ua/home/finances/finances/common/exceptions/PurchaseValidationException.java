package ua.home.finances.finances.common.exceptions;

public class PurchaseValidationException extends IllegalArgumentException {
    public PurchaseValidationException(String message) {
        super(message);
    }
}
