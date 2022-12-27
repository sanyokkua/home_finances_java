package ua.home.finances.finances.api;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.home.finances.finances.api.dto.RequestPurchaseListDto;
import ua.home.finances.finances.api.dto.ResponsePurchaseDto;
import ua.home.finances.finances.api.dto.ResponsePurchaseListDto;
import ua.home.finances.finances.api.dto.ResponsePurchaseListsDto;
import ua.home.finances.finances.services.api.PurchaseService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users/{userId}/lists")
@RequiredArgsConstructor
public class RestPurchaseController {

    private final PurchaseService purchaseService;

    @PostMapping("/")
    public ResponseEntity<?> createList(@PathVariable long userId, @RequestBody RequestPurchaseListDto purchaseDto) {
        purchaseService.createPurchaseList(userId, purchaseDto.getName());
        return ResponseEntity.ok(null);
    }

    @PatchMapping("/{listId}")
    public ResponseEntity<?> updateList(@PathVariable long userId, @PathVariable long listId,
                                        @RequestBody RequestPurchaseListDto purchaseDto) {
        purchaseService.updatePurchaseList(userId, listId, purchaseDto.getName());
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{listId}")
    public ResponseEntity<?> deleteList(@PathVariable long userId, @PathVariable long listId) {
        purchaseService.deletePurchaseList(userId, listId);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/")
    public ResponseEntity<ResponsePurchaseListsDto> findAllLists(@PathVariable long userId) {
        val res = purchaseService.findAllPurchaseLists(userId);
        val responseList = res.stream()
                .map(p -> ResponsePurchaseListDto.builder().id(p.getListId()).name(p.getName()).build())
                .toList();
        val response = ResponsePurchaseListsDto.builder().purchaseLists(responseList).build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{listId}/purchases")
    public ResponseEntity<?> createPurchase(@PathVariable long userId, @PathVariable long listId,
                                            ResponsePurchaseDto purchase) {
        purchaseService.createPurchase(listId, ResponsePurchaseDto.from(purchase));
        return ResponseEntity.ok(null);
    }

    @PatchMapping("/{listId}/purchases/{purchaseId}")
    public ResponseEntity<?> updatePurchase(@PathVariable long userId, @PathVariable long listId,
                                            @PathVariable long purchaseListId, ResponsePurchaseDto purchase) {
        purchaseService.updatePurchase(purchaseListId, ResponsePurchaseDto.from(purchase));
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{listId}/purchases/{purchaseId}")
    public ResponseEntity<?> deletePurchase(@PathVariable long userId, @PathVariable long listId,
                                            @PathVariable long purchaseId) {
        purchaseService.deletePurchase(listId, purchaseId);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/{listId}/purchases/{purchaseId}")
    public ResponseEntity<ResponsePurchaseDto> getPurchase(@PathVariable long userId, @PathVariable long listId,
                                                           @PathVariable long purchaseId) {
        val purchase = purchaseService.getPurchase(listId, purchaseId);
        return ResponseEntity.ok(ResponsePurchaseDto.from(purchase));
    }

    @GetMapping("/{listId}/purchases/")
    public ResponseEntity<List<ResponsePurchaseDto>> getAllForList(@PathVariable long userId,
                                                                   @PathVariable long listId) {
        val res = purchaseService.getAllForList(listId);
        val list = res.stream().map(ResponsePurchaseDto::from).toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/", params = "all")
    public ResponseEntity<List<ResponsePurchaseDto>> getAll(@PathVariable long userId) {
        val res = purchaseService.getAll(userId);
        val list = res.stream().map(ResponsePurchaseDto::from).toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/", params = "names")
    public ResponseEntity<List<String>> getAllNames(@PathVariable long userId) {
        val res = purchaseService.getAllNames(userId);
        return ResponseEntity.ok(res);
    }
}
