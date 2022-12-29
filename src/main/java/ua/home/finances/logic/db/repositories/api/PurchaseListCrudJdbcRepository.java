package ua.home.finances.logic.db.repositories.api;

import ua.home.finances.logic.db.models.PurchaseList;

import java.util.List;

public interface PurchaseListCrudJdbcRepository extends BasicCrudJdbcRepository<PurchaseList, Long> {
    List<PurchaseList> findAll(long userId);
}
