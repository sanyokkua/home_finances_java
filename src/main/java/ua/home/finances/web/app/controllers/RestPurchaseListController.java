package ua.home.finances.web.app.controllers;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.home.finances.logic.common.exceptions.PurchaseListIsNotFoundException;
import ua.home.finances.logic.services.api.PurchaseListCrudService;
import ua.home.finances.logic.services.api.Result;
import ua.home.finances.logic.services.api.UserCrudService;
import ua.home.finances.web.app.api.PurchaseListController;
import ua.home.finances.web.app.dtos.DeleteResults;
import ua.home.finances.web.app.dtos.ReqBodyPurchaseListDto;
import ua.home.finances.web.app.dtos.RespListItems;
import ua.home.finances.web.app.dtos.RespPurchaseListDto;

import java.security.Principal;

@RestController("/api/v1/purchasesList/")
@RequiredArgsConstructor
public class RestPurchaseListController implements PurchaseListController {
    private final PurchaseListCrudService purchaseListCrudService;
    private final UserCrudService userCrudService;

    private long getCurrentUserId(Principal principal) {
        val userEmail = principal.getName();
        val user = userCrudService.findUserByEmail(userEmail);
        return user.getUserId();
    }

    private void checkThatUserHasProvidedPurchaseList(long userId, long listId) {
        purchaseListCrudService.findAllPurchaseLists(userId)
                .stream()
                .filter(l -> listId == l.getListId())
                .findAny()
                .orElseThrow(
                        () -> new PurchaseListIsNotFoundException("Purchase list by provided ID is not found in user"));
    }

    @PostMapping("/")
    @Override
    public ResponseEntity<RespPurchaseListDto> createList(Principal principal,
                                                          ReqBodyPurchaseListDto purchaseReqListDto) {
        val userId = getCurrentUserId(principal);
        val purchaseListDto = ReqBodyPurchaseListDto.fromRequest(purchaseReqListDto);
        purchaseListDto.setUserId(userId);

        val result = purchaseListCrudService.create(purchaseListDto);
        val responseEntity = RespPurchaseListDto.fromPurchaseListDto(result);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseEntity);
    }

    @PatchMapping("/{listId}")
    @Override
    public ResponseEntity<RespPurchaseListDto> updateList(Principal principal, long listId,
                                                          ReqBodyPurchaseListDto purchaseReqListDto) {
        val userId = getCurrentUserId(principal);
        checkThatUserHasProvidedPurchaseList(userId, listId);
        val purchaseListDto = ReqBodyPurchaseListDto.fromRequest(purchaseReqListDto);
        purchaseListDto.setUserId(userId);

        val result = purchaseListCrudService.update(purchaseListDto);
        val responseEntity = RespPurchaseListDto.fromPurchaseListDto(result);
        return ResponseEntity.status(HttpStatus.OK).body(responseEntity);
    }

    @DeleteMapping("/{listId}")
    @Override
    public ResponseEntity<DeleteResults> deleteList(Principal principal, long listId) {
        val userId = getCurrentUserId(principal);
        checkThatUserHasProvidedPurchaseList(userId, listId);

        val result = purchaseListCrudService.delete(listId);

        val deleteResult = Result.SUCCESS.equals(result) ? DeleteResults.DELETED : DeleteResults.NOT_DELETED;
        return ResponseEntity.status(HttpStatus.OK).body(deleteResult);
    }

    @GetMapping("/{listId}")
    @Override
    public ResponseEntity<RespPurchaseListDto> findListById(Principal principal, long listId) {
        val userId = getCurrentUserId(principal);
        checkThatUserHasProvidedPurchaseList(userId, listId);

        val result = purchaseListCrudService.findById(listId);

        val responseEntity = RespPurchaseListDto.fromPurchaseListDto(result);
        return ResponseEntity.status(HttpStatus.OK).body(responseEntity);
    }

    @GetMapping("/")
    @Override
    public ResponseEntity<RespListItems<RespPurchaseListDto>> findLists(Principal principal) {
        val userId = getCurrentUserId(principal);

        val result = purchaseListCrudService.findAllPurchaseLists(userId);

        val responseItems = result.stream().map(RespPurchaseListDto::fromPurchaseListDto).toList();
        val responseEntity = new RespListItems<>(responseItems);
        return ResponseEntity.status(HttpStatus.OK).body(responseEntity);
    }
}
