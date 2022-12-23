package ua.home.finances.finances.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.home.finances.finances.api.dto.UpdatePurchaseDto;
import ua.home.finances.finances.db.models.Purchase;

@RestController
@RequestMapping("/api/v1/purchases")
@RequiredArgsConstructor
public class RestPurchaseController {

//    private final PurchaseServiceApi purchaseServiceApi;

    @PostMapping("/")
    public ResponseEntity<Purchase> create(@RequestBody UpdatePurchaseDto purchaseDto) {
        return null;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Purchase> update(@RequestBody UpdatePurchaseDto purchaseDto) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Purchase> delete(@PathVariable Long id) {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Purchase> get(@PathVariable Long id) {
        return null;
    }

    @GetMapping("/")
    public ResponseEntity<Purchase> getAll() {
        return null;
    }

    @GetMapping(value = "/", params = "names")
    public ResponseEntity<Purchase> getAllNames() {
        return null;
    }
}
