package ua.home.finances.finances.db.repositories.api;

import ua.home.finances.finances.db.models.Purchase;

import java.util.List;
import java.util.Optional;

public interface CrudPurchaseApi {

    boolean createPurchase(Purchase purchase);

    boolean updatePurchase(Purchase purchase);

    boolean deletePurchase(long purchaseListId, long purchaseId);

    Optional<Purchase> getPurchase(long purchaseListId, long purchaseId);

    List<Purchase> getAll(long purchaseListId);

    List<String> getAllNames(long purchaseListId);
}