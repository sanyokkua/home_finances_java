package ua.home.finances.logic.services;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.home.finances.logic.common.Currency;
import ua.home.finances.logic.common.exceptions.PurchaseIsNotFoundException;
import ua.home.finances.logic.db.models.Purchase;
import ua.home.finances.logic.db.models.PurchaseList;
import ua.home.finances.logic.db.repositories.api.PurchaseCrudJdbcRepository;
import ua.home.finances.logic.db.repositories.api.PurchaseListCrudJdbcRepository;
import ua.home.finances.logic.services.api.Result;
import ua.home.finances.logic.services.dtos.PurchaseDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PurchaseCrudServiceImplTests {
    @Mock
    PurchaseListCrudJdbcRepository mockCrudPurchaseListApi;
    @Mock
    PurchaseCrudJdbcRepository mockCrudPurchaseApi;
    @InjectMocks
    PurchaseCrudServiceImpl purchaseService;

    PurchaseDto purchaseDto1;
    Purchase purchase1;
    Purchase purchase2;
    Purchase purchase3;

    @BeforeEach
    void beforeEach() {
        purchaseDto1 = PurchaseDto.builder()
                .purchaseId(100L)
                .name("purchase1")
                .category("default")
                .coins(10_00L)
                .currency(Currency.USD)
                .date(LocalDate.now())
                .build();
        purchase1 = Purchase.builder()
                .purchaseId(100L)
                .purchaseListId(1000L)
                .name("purchase1")
                .category("default")
                .coins(10_000L)
                .currency(Currency.USD)
                .date(LocalDate.now())
                .build();
        purchase2 = Purchase.builder()
                .purchaseId(100L)
                .purchaseListId(1000L)
                .name("purchase-same")
                .category("default")
                .coins(50_000L)
                .currency(Currency.USD)
                .date(LocalDate.now())
                .build();
        purchase3 = Purchase.builder()
                .purchaseId(100L)
                .purchaseListId(1000L)
                .name("purchase-same")
                .category("default")
                .coins(55_000L)
                .currency(Currency.USD)
                .date(LocalDate.now())
                .build();
    }

    @Test
    void testCreate() {
        when(mockCrudPurchaseApi.create(any(Purchase.class))).thenReturn(purchase1);

        val res = purchaseService.create(purchaseDto1);

        assertNotNull(res);
        verify(mockCrudPurchaseApi, atMostOnce()).create(any(Purchase.class));
    }

    @Test
    void testUpdate() {
        when(mockCrudPurchaseApi.update(any(Purchase.class))).thenReturn(purchase1);

        val res = purchaseService.update(purchaseDto1);

        assertNotNull(res);
        verify(mockCrudPurchaseApi, atMostOnce()).update(any(Purchase.class));
    }

    @Test
    void testDelete() {
        when(mockCrudPurchaseApi.delete(anyLong())).thenReturn(true, false);

        val res1 = purchaseService.delete(1000L);
        val res2 = purchaseService.delete(1000L);

        assertEquals(Result.SUCCESS, res1);
        assertEquals(Result.ERROR, res2);
        verify(mockCrudPurchaseApi, times(2)).delete(anyLong());
    }

    @Test
    void testFindById() {
        when(mockCrudPurchaseApi.findById(anyLong())).thenReturn(Optional.of(purchase1));

        val res = purchaseService.findById(1000L);

        assertNotNull(res);
        verify(mockCrudPurchaseApi, atMostOnce()).findById(anyLong());
    }

    @Test
    void testFindByIdNotFound() {
        when(mockCrudPurchaseApi.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(PurchaseIsNotFoundException.class, () -> purchaseService.findById(1000L));

        verify(mockCrudPurchaseApi, atMostOnce()).findById(anyLong());
    }

    @Test
    void testFindAllInList() {
        when(mockCrudPurchaseApi.findAll(anyLong())).thenReturn(List.of(purchase1, purchase2));

        val res = purchaseService.findAllInList(1000L);

        assertNotNull(res);
        assertEquals(2, res.size());
        verify(mockCrudPurchaseApi, atMostOnce()).findAll(anyLong());

    }

    @Test
    void testFindAllInUser() {
        when(mockCrudPurchaseListApi.findAll(anyLong())).thenReturn(
                List.of(PurchaseList.builder().listId(1000L).listUserId(1000L).name("TEST_LIST").build()));
        when(mockCrudPurchaseApi.findAll(anyLong())).thenReturn(List.of(purchase1, purchase2));

        val res = purchaseService.findAllInUser(1000L);

        assertNotNull(res);
        assertEquals(2, res.size());
        verify(mockCrudPurchaseListApi, atMostOnce()).findAll(anyLong());
        verify(mockCrudPurchaseApi, atMostOnce()).findAll(anyLong());
    }

    @Test
    void testFindAllPurchaseNamesInUser() {
        when(mockCrudPurchaseListApi.findAll(anyLong())).thenReturn(
                List.of(PurchaseList.builder().listId(1000L).listUserId(1000L).name("TEST_LIST").build()));
        when(mockCrudPurchaseApi.findAllNames(anyLong())).thenReturn(
                List.of(purchase1.getName(), purchase2.getName(), purchase3.getName()));

        val res = purchaseService.findAllPurchaseNamesInUser(1000L);

        assertNotNull(res);
        assertEquals(2, res.size());
        verify(mockCrudPurchaseListApi, atMostOnce()).findAll(anyLong());
        verify(mockCrudPurchaseApi, atMostOnce()).findAll(anyLong());
    }

}
