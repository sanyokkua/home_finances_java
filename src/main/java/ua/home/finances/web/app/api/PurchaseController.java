package ua.home.finances.web.app.api;

import org.springframework.http.ResponseEntity;
import ua.home.finances.web.app.dtos.DeleteResults;
import ua.home.finances.web.app.dtos.ReqBodyPurchaseDto;
import ua.home.finances.web.app.dtos.RespListItems;
import ua.home.finances.web.app.dtos.RespPurchaseDto;

import java.security.Principal;

public interface PurchaseController {
    ResponseEntity<RespPurchaseDto> createPurchase(Principal principal, long listId, ReqBodyPurchaseDto purchase);

    ResponseEntity<RespPurchaseDto> updatePurchase(Principal principal, long listId, long purchaseId,
                                                   ReqBodyPurchaseDto purchase);

    ResponseEntity<DeleteResults> deletePurchase(Principal principal, long listId, long purchaseId);

    ResponseEntity<RespPurchaseDto> findPurchaseById(Principal principal, long listId, long purchaseId);

    ResponseEntity<RespListItems<RespPurchaseDto>> findPurchasesInList(Principal principal, long listId);

    ResponseEntity<RespListItems<RespPurchaseDto>> findPurchasesForUser(Principal principal);

    ResponseEntity<RespListItems<String>> findPurchasesNamesForUser(Principal principal);
}
