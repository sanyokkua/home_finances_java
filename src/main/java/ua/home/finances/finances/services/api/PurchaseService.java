package ua.home.finances.finances.services.api;

import ua.home.finances.finances.services.dtos.PurchaseDto;
import ua.home.finances.finances.services.dtos.PurchaseListDto;

import java.util.List;

public interface PurchaseService {
    void createPurchaseList(long userId, String name);

    void updatePurchaseList(long userId, long purchaseListId, String name);

    void deletePurchaseList(long userId, long purchaseListId);

    List<PurchaseListDto> findAllPurchaseLists(long userId);

    void createPurchase(long purchaseListId, PurchaseDto purchase);

    void updatePurchase(long purchaseListId, PurchaseDto purchase);

    void deletePurchase(long purchaseListId, long purchaseId);

    PurchaseDto getPurchase(long purchaseListId, long purchaseId);

    List<PurchaseDto> getAllForList(long purchaseListId);

    List<PurchaseDto> getAll(long userId);

    List<String> getAllNames(long userId);
}
