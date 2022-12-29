package ua.home.finances.logic.services.api;

import ua.home.finances.logic.services.dtos.PurchaseDto;

import java.util.List;

public interface PurchaseCrudService extends CrudService<PurchaseDto, Long> {
    List<PurchaseDto> findAllInList(long purchaseListId);

    List<PurchaseDto> findAllInUser(long userId);

    List<String> findAllPurchaseNamesInUser(long userId);
}
