package ua.home.finances.finances.common.exceptions;

public class PurchaseIsNotFoundException extends IllegalArgumentException {
    public PurchaseIsNotFoundException(String message) {
        super(message);
    }
}
