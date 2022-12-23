package ua.home.finances.finances.db.repositories.api;

import ua.home.finances.finances.db.models.PurchaseList;

import java.util.List;
import java.util.Optional;

public interface CrudPurchaseListApi {
    boolean createPurchaseList(PurchaseList purchaseList);

    boolean updatePurchaseList(PurchaseList purchaseList);

    boolean deletePurchaseList(long userId, long listId);

    Optional<PurchaseList> findById(long userId, long listId);

    Optional<PurchaseList> findByName(long userId, String name);

    List<PurchaseList> findAll(long userId);
}
