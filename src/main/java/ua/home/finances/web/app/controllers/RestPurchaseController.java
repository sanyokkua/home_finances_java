package ua.home.finances.web.app.controllers;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.home.finances.logic.common.exceptions.PurchaseListIsNotFoundException;
import ua.home.finances.logic.services.api.PurchaseCrudService;
import ua.home.finances.logic.services.api.PurchaseListCrudService;
import ua.home.finances.logic.services.api.Result;
import ua.home.finances.logic.services.api.UserCrudService;
import ua.home.finances.web.app.api.PurchaseController;
import ua.home.finances.web.app.dtos.DeleteResults;
import ua.home.finances.web.app.dtos.ReqBodyPurchaseDto;
import ua.home.finances.web.app.dtos.RespListItems;
import ua.home.finances.web.app.dtos.RespPurchaseDto;

import java.security.Principal;

@RestController("/api/v1/purchasesList")
@RequiredArgsConstructor
public class RestPurchaseController implements PurchaseController {
    private final PurchaseCrudService purchaseCrudService;
    private final PurchaseListCrudService purchaseListCrudService;
    private final UserCrudService userCrudService;

    private void checkThatUserHasProvidedPurchaseList(Principal principal, long listId) {
        val userEmail = principal.getName();
        val user = userCrudService.findUserByEmail(userEmail);
        val userId = user.getUserId();
        purchaseListCrudService.findAllPurchaseLists(userId)
                .stream()
                .filter(l -> listId == l.getListId())
                .findAny()
                .orElseThrow(
                        () -> new PurchaseListIsNotFoundException("Purchase list by provided ID is not found in user"));
    }

    @PostMapping("/{listId}/purchases/")
    @Override
    public ResponseEntity<RespPurchaseDto> createPurchase(Principal principal, long listId,
                                                          ReqBodyPurchaseDto purchase) {
        checkThatUserHasProvidedPurchaseList(principal, listId);

        val purchaseDto = ReqBodyPurchaseDto.fromRequest(purchase);
        purchaseDto.setPurchaseListId(listId);

        val result = purchaseCrudService.create(purchaseDto);

        val responseEntity = RespPurchaseDto.fromPurchaseDto(result);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseEntity);
    }

    @PatchMapping("/{listId}/purchases/{purchaseId}")
    @Override
    public ResponseEntity<RespPurchaseDto> updatePurchase(Principal principal, long listId, long purchaseId,
                                                          ReqBodyPurchaseDto purchase) {
        checkThatUserHasProvidedPurchaseList(principal, listId);

        val purchaseDto = ReqBodyPurchaseDto.fromRequest(purchase);
        purchaseDto.setPurchaseListId(listId);

        val result = purchaseCrudService.update(purchaseDto);

        val responseEntity = RespPurchaseDto.fromPurchaseDto(result);
        return ResponseEntity.status(HttpStatus.OK).body(responseEntity);
    }

    @DeleteMapping("/{listId}/purchases/{purchaseId}")
    @Override
    public ResponseEntity<DeleteResults> deletePurchase(Principal principal, long listId, long purchaseId) {
        checkThatUserHasProvidedPurchaseList(principal, listId);

        val result = purchaseCrudService.delete(purchaseId);

        val deleteResult = Result.SUCCESS.equals(result) ? DeleteResults.DELETED : DeleteResults.NOT_DELETED;
        return ResponseEntity.status(HttpStatus.OK).body(deleteResult);
    }

    @GetMapping("/{listId}/purchases/{purchaseId}")
    @Override
    public ResponseEntity<RespPurchaseDto> findPurchaseById(Principal principal, long listId, long purchaseId) {
        checkThatUserHasProvidedPurchaseList(principal, listId);

        val result = purchaseCrudService.findById(purchaseId);

        val responseEntity = RespPurchaseDto.fromPurchaseDto(result);
        return ResponseEntity.status(HttpStatus.OK).body(responseEntity);
    }

    @GetMapping("/{listId}/purchases/")
    @Override
    public ResponseEntity<RespListItems<RespPurchaseDto>> findPurchasesInList(Principal principal, long listId) {
        checkThatUserHasProvidedPurchaseList(principal, listId);

        val result = purchaseCrudService.findAllInList(listId);

        val purchasesRespItems = result.stream().map(RespPurchaseDto::fromPurchaseDto).toList();
        val responseEntity = new RespListItems<>(purchasesRespItems);
        return ResponseEntity.status(HttpStatus.OK).body(responseEntity);
    }

    @GetMapping(value = "/", params = "allPurchases")
    @Override
    public ResponseEntity<RespListItems<RespPurchaseDto>> findPurchasesForUser(Principal principal) {
        val userEmail = principal.getName();
        val user = userCrudService.findUserByEmail(userEmail);
        val userId = user.getUserId();

        val result = purchaseCrudService.findAllInUser(userId);

        val purchasesRespItems = result.stream().map(RespPurchaseDto::fromPurchaseDto).toList();
        val responseEntity = new RespListItems<>(purchasesRespItems);
        return ResponseEntity.status(HttpStatus.OK).body(responseEntity);
    }

    @GetMapping(value = "/", params = "allNames")
    @Override
    public ResponseEntity<RespListItems<String>> findPurchasesNamesForUser(Principal principal) {
        val userEmail = principal.getName();
        val user = userCrudService.findUserByEmail(userEmail);
        val userId = user.getUserId();

        val result = purchaseCrudService.findAllPurchaseNamesInUser(userId);

        val responseEntity = new RespListItems<>(result);
        return ResponseEntity.status(HttpStatus.OK).body(responseEntity);
    }
}
