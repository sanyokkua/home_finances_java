package ua.home.finances.logic.common.exceptions;

public class PurchaseIsNotFoundException extends IllegalArgumentException {
    public PurchaseIsNotFoundException(String message) {
        super(message);
    }
}
