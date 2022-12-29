package ua.home.finances.logic.db.repositories.api;

import ua.home.finances.logic.db.models.Purchase;

import java.util.List;

public interface PurchaseCrudJdbcRepository extends BasicCrudJdbcRepository<Purchase, Long> {
    List<Purchase> findAll(long purchaseListId);

    List<String> findAllNames(long purchaseListId);
}
