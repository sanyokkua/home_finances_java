package ua.home.finances.logic.services;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import ua.home.finances.logic.common.exceptions.PurchaseListIsNotFoundException;
import ua.home.finances.logic.db.repositories.api.PurchaseListCrudJdbcRepository;
import ua.home.finances.logic.services.api.PurchaseListCrudService;
import ua.home.finances.logic.services.api.Result;
import ua.home.finances.logic.services.dtos.PurchaseListDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseListCrudServiceImpl implements PurchaseListCrudService {
    private final PurchaseListCrudJdbcRepository purchaseListCrudJdbcRepository;

    @Override
    public PurchaseListDto create(PurchaseListDto entity) {
        val created = purchaseListCrudJdbcRepository.create(PurchaseListDto.fromDto(entity));
        return PurchaseListDto.fromModel(created);
    }

    @Override
    public PurchaseListDto update(PurchaseListDto entity) {
        val updated = purchaseListCrudJdbcRepository.update(PurchaseListDto.fromDto(entity));
        return PurchaseListDto.fromModel(updated);
    }

    @Override
    public Result delete(Long entityId) {
        val res = purchaseListCrudJdbcRepository.delete(entityId);
        return res ? Result.SUCCESS : Result.ERROR;
    }

    @Override
    public PurchaseListDto findById(Long entityId) {
        val foundList = purchaseListCrudJdbcRepository.findById(entityId);

        if (foundList.isEmpty()) {
            throw new PurchaseListIsNotFoundException("PurchaseList is not found.");
        }

        val purchaseList = foundList.get();
        return PurchaseListDto.fromModel(purchaseList);
    }

    @Override
    public List<PurchaseListDto> findAllPurchaseLists(long userId) {
        val foundLists = purchaseListCrudJdbcRepository.findAll(userId);
        return foundLists.stream().map(PurchaseListDto::fromModel).toList();
    }
}
