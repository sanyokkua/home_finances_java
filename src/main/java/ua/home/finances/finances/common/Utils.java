package ua.home.finances.finances.common;

import ua.home.finances.finances.common.exceptions.PurchaseValidationException;
import ua.home.finances.finances.db.models.Purchase;

import java.util.Objects;

public class Utils {

    public static void validatePurchase(Purchase purchase) {
        if (Objects.isNull(purchase)) {
            throw new PurchaseValidationException("Purchase is NULL");
        }
        if (Objects.isNull(purchase.getDate())) {
            throw new PurchaseValidationException("Purchase DATE is NULL");
        }
        if (Objects.isNull(purchase.getCurrency())) {
            throw new PurchaseValidationException("Purchase Currency is NULL");
        }
        if (purchase.getCoins() <= 0) {
            throw new PurchaseValidationException("Purchase COINS are 0 or below");
        }
    }
}
