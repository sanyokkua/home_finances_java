package ua.home.finances.logic.services;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.home.finances.logic.common.exceptions.PurchaseListIsNotFoundException;
import ua.home.finances.logic.db.models.PurchaseList;
import ua.home.finances.logic.db.repositories.api.PurchaseListCrudJdbcRepository;
import ua.home.finances.logic.services.api.Result;
import ua.home.finances.logic.services.dtos.PurchaseListDto;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PurchaseListCrudServiceImplImplTests {
    @Mock
    PurchaseListCrudJdbcRepository mockRepository;
    @InjectMocks
    PurchaseListCrudServiceImpl purchaseListService;
    PurchaseList testPurchaseList;
    PurchaseListDto testPurchaseListDto;

    @BeforeEach
    void beforeEach() {
        testPurchaseList = PurchaseList.builder().listId(1000L).listUserId(1000L).name("TEST_LIST").build();
        testPurchaseListDto = PurchaseListDto.fromModel(testPurchaseList);
    }

    @Test
    void testCreate() {
        when(mockRepository.create(any(PurchaseList.class))).thenReturn(testPurchaseList);

        val res = purchaseListService.create(testPurchaseListDto);

        assertNotNull(res);
        verify(mockRepository, atMostOnce()).create(any(PurchaseList.class));
    }

    @Test
    void testUpdate() {
        when(mockRepository.update(any(PurchaseList.class))).thenReturn(testPurchaseList);

        val res = purchaseListService.update(testPurchaseListDto);

        assertNotNull(res);
        verify(mockRepository, atMostOnce()).update(any(PurchaseList.class));
    }

    @Test
    void testDelete() {
        when(mockRepository.delete(anyLong())).thenReturn(true, false);

        val res1 = purchaseListService.delete(1000L);
        val res2 = purchaseListService.delete(1000L);

        assertEquals(Result.SUCCESS, res1);
        assertEquals(Result.ERROR, res2);
        verify(mockRepository, times(2)).delete(anyLong());
    }

    @Test
    void testFindById() {
        when(mockRepository.findById(anyLong())).thenReturn(Optional.of(testPurchaseList));

        val res = purchaseListService.findById(1000L);

        assertNotNull(res);
        verify(mockRepository, atMostOnce()).findById(anyLong());
    }

    @Test
    void testFindByIdNotFound() {
        when(mockRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(PurchaseListIsNotFoundException.class, () -> purchaseListService.findById(1000L));

        verify(mockRepository, atMostOnce()).findById(anyLong());
    }

    @Test
    void testFindAllPurchaseLists() {
        when(mockRepository.findAll(anyLong())).thenReturn(List.of(testPurchaseList));

        val res = purchaseListService.findAllPurchaseLists(1000L);

        assertNotNull(res);
        assertEquals(1, res.size());
        verify(mockRepository, atMostOnce()).findAll(anyLong());
    }
}
