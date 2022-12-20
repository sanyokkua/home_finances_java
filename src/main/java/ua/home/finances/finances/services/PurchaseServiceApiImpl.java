package ua.home.finances.finances.services;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import ua.home.finances.finances.common.Utils;
import ua.home.finances.finances.db.models.Purchase;
import ua.home.finances.finances.db.repositories.CrudPurchaseApi;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseServiceApiImpl implements PurchaseServiceApi {
    private final CrudPurchaseApi crudPurchaseApi;

    @Override
    public Purchase createPurchase(final Purchase purchase) {
        Utils.validatePurchase(purchase);
        val res = crudPurchaseApi.createPurchase(purchase);
        return res ? purchase : null;
    }

    @Override
    public Purchase updatePurchase(final Purchase purchase) {
        Utils.validatePurchase(purchase);
        val res = crudPurchaseApi.updatePurchase(purchase);
        return res ? purchase : null;
    }

    @Override
    public boolean deletePurchase(final long id) {
        return crudPurchaseApi.deletePurchase(id);
    }

    @Override
    public Purchase getPurchase(final long id) {
        return crudPurchaseApi.getPurchase(id);
    }

    @Override
    public List<Purchase> getAll() {
        return crudPurchaseApi.getAll();
    }

    @Override
    public List<String> getAllNames() {
        return crudPurchaseApi.getAllNames()
                              .stream()
                              .distinct()
                              .collect(Collectors.toList());
    }

    @Override
    public List<Purchase> getAllFilteredBy(final Predicate<Purchase> predicate) {
        return crudPurchaseApi.getAll()
                              .stream()
                              .filter(predicate)
                              .collect(Collectors.toList());
    }
}
