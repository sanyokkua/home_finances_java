package ua.home.finances.finances.services;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import ua.home.finances.finances.common.exceptions.EntityCreationException;
import ua.home.finances.finances.common.exceptions.EntityDeletionException;
import ua.home.finances.finances.common.exceptions.EntityUpdateException;
import ua.home.finances.finances.common.exceptions.PurchaseIsNotFoundException;
import ua.home.finances.finances.db.models.Purchase;
import ua.home.finances.finances.db.models.PurchaseList;
import ua.home.finances.finances.db.repositories.api.CrudPurchaseApi;
import ua.home.finances.finances.db.repositories.api.CrudPurchaseListApi;
import ua.home.finances.finances.services.api.PurchaseService;
import ua.home.finances.finances.services.dtos.PurchaseDto;
import ua.home.finances.finances.services.dtos.PurchaseListDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {
    private final CrudPurchaseListApi crudPurchaseListApi;
    private final CrudPurchaseApi crudPurchaseApi;

    @Override
    public void createPurchaseList(long userId, String name) {
        val purchaseList = PurchaseList.builder().listUserId(userId).name(name).build();
        val isCreated = crudPurchaseListApi.createPurchaseList(purchaseList);
        if (!isCreated) {
            throw new EntityCreationException("Purchase List is not created");
        }
    }

    @Override
    public void updatePurchaseList(long userId, long purchaseListId, String name) {
        val purchaseList = PurchaseList.builder().listUserId(userId).listId(purchaseListId).name(name).build();
        val isUpdated = crudPurchaseListApi.updatePurchaseList(purchaseList);
        if (!isUpdated) {
            throw new EntityUpdateException("Purchase List is not updated");
        }
    }

    @Override
    public void deletePurchaseList(long userId, long purchaseListId) {
        val isDeleted = crudPurchaseListApi.deletePurchaseList(userId, purchaseListId);
        if (!isDeleted) {
            throw new EntityDeletionException("Purchase List is not deleted");
        }
    }

    @Override
    public List<PurchaseListDto> findAllPurchaseLists(long userId) {
        val list = crudPurchaseListApi.findAll(userId);
        return list.stream()
                .map(pl -> PurchaseListDto.builder().listId(pl.getListId()).name(pl.getName()).build())
                .toList();
    }

    @Override
    public void createPurchase(long purchaseListId, PurchaseDto purchase) {
        val purchaseToCreate = Purchase.builder()
                .purchaseListId(purchaseListId)
                .name(purchase.getName())
                .currency(purchase.getCurrency())
                .coins(purchase.getCoins())
                .date(purchase.getDate())
                .category(purchase.getCategory())
                .build();
        val isCreated = crudPurchaseApi.createPurchase(purchaseToCreate);
        if (!isCreated) {
            throw new EntityCreationException("Purchase is not created");
        }
    }

    @Override
    public void updatePurchase(long purchaseListId, PurchaseDto purchase) {
        val purchaseToUpdate = Purchase.builder()
                .purchaseId(purchase.getPurchaseId())
                .purchaseListId(purchaseListId)
                .name(purchase.getName())
                .currency(purchase.getCurrency())
                .coins(purchase.getCoins())
                .date(purchase.getDate())
                .category(purchase.getCategory())
                .build();
        val isUpdated = crudPurchaseApi.updatePurchase(purchaseToUpdate);
        if (!isUpdated) {
            throw new EntityUpdateException("Purchase is not updated");
        }
    }

    @Override
    public void deletePurchase(long purchaseListId, long purchaseId) {
        val isDeleted = crudPurchaseApi.deletePurchase(purchaseListId, purchaseId);
        if (!isDeleted) {
            throw new EntityDeletionException("Purchase is not deleted");
        }
    }

    @Override
    public PurchaseDto getPurchase(long purchaseListId, long purchaseId) {
        val purchase = crudPurchaseApi.getPurchase(purchaseListId, purchaseId);
        if (purchase.isEmpty()) {
            throw new PurchaseIsNotFoundException("Purchase is not found");
        }

        return PurchaseDto.builder()
                .purchaseId(purchase.get().getPurchaseId())
                .name(purchase.get().getName())
                .coins(purchase.get().getCoins())
                .currency(purchase.get().getCurrency())
                .date(purchase.get().getDate())
                .category(purchase.get().getCategory())
                .build();
    }

    @Override
    public List<PurchaseDto> getAllForList(long purchaseListId) {
        val all = crudPurchaseApi.getAll(purchaseListId);
        return all.stream()
                .map(purchase -> PurchaseDto.builder()
                        .purchaseId(purchase.getPurchaseId())
                        .name(purchase.getName())
                        .coins(purchase.getCoins())
                        .currency(purchase.getCurrency())
                        .date(purchase.getDate())
                        .category(purchase.getCategory())
                        .build())
                .toList();
    }

    @Override
    public List<PurchaseDto> getAll(long userId) {
        val allLists = crudPurchaseListApi.findAll(userId);
        return allLists.stream()
                .flatMap(list -> crudPurchaseApi.getAll(list.getListId()).stream())
                .map(purchase -> PurchaseDto.builder()
                        .purchaseId(purchase.getPurchaseId())
                        .name(purchase.getName())
                        .coins(purchase.getCoins())
                        .currency(purchase.getCurrency())
                        .date(purchase.getDate())
                        .category(purchase.getCategory())
                        .build())
                .toList();
    }

    @Override
    public List<String> getAllNames(long userId) {
        val allLists = crudPurchaseListApi.findAll(userId);
        return allLists.stream()
                .flatMap(list -> crudPurchaseApi.getAllNames(list.getListId()).stream())
                .distinct()
                .toList();
    }
}
