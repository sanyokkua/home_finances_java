package ua.home.finances.web.app.api;

import org.springframework.http.ResponseEntity;
import ua.home.finances.web.app.dtos.DeleteResults;
import ua.home.finances.web.app.dtos.ReqBodyPurchaseListDto;
import ua.home.finances.web.app.dtos.RespListItems;
import ua.home.finances.web.app.dtos.RespPurchaseListDto;

import java.security.Principal;

public interface PurchaseListController {
    ResponseEntity<RespPurchaseListDto> createList(Principal principal, ReqBodyPurchaseListDto purchaseDto);

    ResponseEntity<RespPurchaseListDto> updateList(Principal principal, long listId,
                                                   ReqBodyPurchaseListDto purchaseDto);

    ResponseEntity<DeleteResults> deleteList(Principal principal, long listId);

    ResponseEntity<RespPurchaseListDto> findListById(Principal principal, long listId);

    ResponseEntity<RespListItems<RespPurchaseListDto>> findLists(Principal principal);
}
