package ua.home.finances.logic.services.api;

import ua.home.finances.logic.services.dtos.PurchaseListDto;

import java.util.List;

public interface PurchaseListCrudService extends CrudService<PurchaseListDto, Long> {

    List<PurchaseListDto> findAllPurchaseLists(long userId);

}
