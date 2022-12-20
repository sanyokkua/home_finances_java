package ua.home.finances.finances.db.repositories;

import ua.home.finances.finances.db.models.Purchase;

import java.util.List;

public interface CrudPurchaseApi {

    boolean createPurchase(Purchase purchase);

    boolean updatePurchase(Purchase purchase);

    boolean deletePurchase(long id);

    Purchase getPurchase(long id);

    List<Purchase> getAll();

    List<String> getAllNames();
}
