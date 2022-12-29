package ua.home.finances.logic.services;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import ua.home.finances.logic.common.exceptions.PurchaseIsNotFoundException;
import ua.home.finances.logic.db.repositories.api.PurchaseCrudJdbcRepository;
import ua.home.finances.logic.db.repositories.api.PurchaseListCrudJdbcRepository;
import ua.home.finances.logic.services.api.PurchaseCrudService;
import ua.home.finances.logic.services.api.Result;
import ua.home.finances.logic.services.dtos.PurchaseDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseCrudServiceImpl implements PurchaseCrudService {
    private final PurchaseCrudJdbcRepository purchaseCrudJdbcRepository;
    private final PurchaseListCrudJdbcRepository purchaseListCrudJdbcRepository;

    @Override
    public PurchaseDto create(PurchaseDto entity) {
        val created = purchaseCrudJdbcRepository.create(PurchaseDto.fromDto(entity));
        return PurchaseDto.fromModel(created);
    }

    @Override
    public PurchaseDto update(PurchaseDto entity) {
        val updated = purchaseCrudJdbcRepository.update(PurchaseDto.fromDto(entity));
        return PurchaseDto.fromModel(updated);
    }

    @Override
    public Result delete(Long entityId) {
        val res = purchaseCrudJdbcRepository.delete(entityId);
        return res ? Result.SUCCESS : Result.ERROR;

    }

    @Override
    public PurchaseDto findById(Long entityId) {
        val foundPurchase = purchaseCrudJdbcRepository.findById(entityId);

        if (foundPurchase.isEmpty()) {
            throw new PurchaseIsNotFoundException("Purchase is not found.");
        }

        val purchaseList = foundPurchase.get();
        return PurchaseDto.fromModel(purchaseList);
    }

    @Override
    public List<PurchaseDto> findAllInList(long purchaseListId) {
        val foundPurchase = purchaseCrudJdbcRepository.findAll(purchaseListId);
        return foundPurchase.stream().map(PurchaseDto::fromModel).toList();
    }

    @Override
    public List<PurchaseDto> findAllInUser(long userId) {
        return purchaseListCrudJdbcRepository.findAll(userId)
                .stream()
                .map(pl -> findAllInList(pl.getListId()))
                .flatMap(List::stream)
                .toList();
    }

    @Override
    public List<String> findAllPurchaseNamesInUser(long userId) {
        return purchaseListCrudJdbcRepository.findAll(userId)
                .stream()
                .map(pl -> purchaseCrudJdbcRepository.findAllNames(pl.getListId()))
                .flatMap(List::stream)
                .distinct()
                .toList();
    }
}
