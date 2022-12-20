package ua.home.finances.finances.services;

import ua.home.finances.finances.db.models.Purchase;

import java.util.List;
import java.util.function.Predicate;

public interface PurchaseServiceApi {
    Purchase createPurchase(Purchase purchase);

    Purchase updatePurchase(Purchase purchase);

    boolean deletePurchase(long id);

    Purchase getPurchase(long id);

    List<Purchase> getAll();

    List<String> getAllNames();

    List<Purchase> getAllFilteredBy(Predicate<Purchase> predicate);
}
