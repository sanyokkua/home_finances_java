package ua.home.finances.finances.services;

import ua.home.finances.finances.db.models.PurchaseList;

import java.util.List;

public interface PurchaseListService {
    boolean createPurchaseList(PurchaseList purchaseList);

    boolean updatePurchaseList(PurchaseList purchaseList);

    boolean deletePurchaseList(long userId, long listId);

    List<PurchaseList> findAll(long userId);
}
