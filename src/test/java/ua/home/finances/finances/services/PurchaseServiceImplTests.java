package ua.home.finances.finances.services;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.home.finances.finances.common.Currency;
import ua.home.finances.finances.common.exceptions.EntityCreationException;
import ua.home.finances.finances.common.exceptions.EntityDeletionException;
import ua.home.finances.finances.common.exceptions.EntityUpdateException;
import ua.home.finances.finances.common.exceptions.PurchaseIsNotFoundException;
import ua.home.finances.finances.db.models.Purchase;
import ua.home.finances.finances.db.models.PurchaseList;
import ua.home.finances.finances.db.repositories.api.CrudPurchaseApi;
import ua.home.finances.finances.db.repositories.api.CrudPurchaseListApi;
import ua.home.finances.finances.services.dtos.PurchaseDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PurchaseServiceImplTests {
    @Mock
    CrudPurchaseListApi crudPurchaseListApi;
    @Mock
    CrudPurchaseApi crudPurchaseApi;
    @InjectMocks
    PurchaseServiceImpl purchaseService;

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
    void testCreatePurchaseList() {
        // @formatter:off
        when(crudPurchaseListApi.createPurchaseList(any(PurchaseList.class))).thenReturn(true);
        // @formatter:on

        purchaseService.createPurchaseList(1_00L, "New List");

        verify(crudPurchaseListApi, atMostOnce()).createPurchaseList(any(PurchaseList.class));
    }

    @Test
    void testCreatePurchaseListNotCreated() {
        // @formatter:off
        when(crudPurchaseListApi.createPurchaseList(any(PurchaseList.class))).thenReturn(false);
        // @formatter:on

        assertThrows(EntityCreationException.class, () -> purchaseService.createPurchaseList(1_00L, "New List"));

        verify(crudPurchaseListApi, atMostOnce()).createPurchaseList(any(PurchaseList.class));
    }

    @Test
    void testUpdatePurchaseList() {
        // @formatter:off
        when(crudPurchaseListApi.updatePurchaseList(any(PurchaseList.class))).thenReturn(true);
        // @formatter:on

        purchaseService.updatePurchaseList(1_00L, 12_00L, "New List");

        verify(crudPurchaseListApi, atMostOnce()).updatePurchaseList(any(PurchaseList.class));
    }

    @Test
    void testUpdatePurchaseListNotUpdated() {
        // @formatter:off
        when(crudPurchaseListApi.updatePurchaseList(any(PurchaseList.class))).thenReturn(false);
        // @formatter:on

        assertThrows(EntityUpdateException.class, () -> purchaseService.updatePurchaseList(1_00L, 2000L, "New List"));

        verify(crudPurchaseListApi, atMostOnce()).updatePurchaseList(any(PurchaseList.class));
    }

    @Test
    void testDeletePurchaseList() {
        // @formatter:off
        when(crudPurchaseListApi.deletePurchaseList(anyLong(), anyLong())).thenReturn(true);
        // @formatter:on

        purchaseService.deletePurchaseList(1_00L, 2000L);

        verify(crudPurchaseListApi, atMostOnce()).deletePurchaseList(anyLong(), anyLong());
    }

    @Test
    void testDeletePurchaseListNotDeleted() {
        // @formatter:off
        when(crudPurchaseListApi.deletePurchaseList(anyLong(), anyLong())).thenReturn(false);
        // @formatter:on

        assertThrows(EntityDeletionException.class, () -> purchaseService.deletePurchaseList(1_00L, 2000L));

        verify(crudPurchaseListApi, atMostOnce()).deletePurchaseList(anyLong(), anyLong());
    }

    @Test
    void testFindAllPurchaseLists() {
        // @formatter:off
        val purchaseList1 = PurchaseList.builder().listUserId(10L).listId(10L).name("list1").build();
        val purchaseList2 = PurchaseList.builder().listUserId(10L).listId(20L).name("list2").build();
        val purchaseList3 = PurchaseList.builder().listUserId(10L).listId(30L).name("list3").build();
        val list = List.of(purchaseList1, purchaseList2, purchaseList3);
        // @formatter:on

        // @formatter:off
        when(crudPurchaseListApi.findAll(anyLong())).thenReturn(list);
        // @formatter:on

        val res = purchaseService.findAllPurchaseLists(10L);

        assertNotNull(res);
        assertEquals(3, res.size());
    }

    @Test
    void testCreatePurchase() {
        when(crudPurchaseApi.createPurchase(any(Purchase.class))).thenReturn(true);

        purchaseService.createPurchase(1000L, purchaseDto1);

        verify(crudPurchaseApi, atMostOnce()).createPurchase(any(Purchase.class));
    }

    @Test
    void testCreatePurchaseNotCreated() {
        when(crudPurchaseApi.createPurchase(any(Purchase.class))).thenReturn(false);

        assertThrows(EntityCreationException.class, () -> purchaseService.createPurchase(1000L, purchaseDto1));

        verify(crudPurchaseApi, atMostOnce()).createPurchase(any(Purchase.class));
    }

    @Test
    void testUpdatePurchase() {
        when(crudPurchaseApi.updatePurchase(any(Purchase.class))).thenReturn(true);

        purchaseService.updatePurchase(1000L, purchaseDto1);

        verify(crudPurchaseApi, atMostOnce()).updatePurchase(any(Purchase.class));
    }

    @Test
    void testUpdatePurchaseNotUpdated() {
        when(crudPurchaseApi.updatePurchase(any(Purchase.class))).thenReturn(false);

        assertThrows(EntityUpdateException.class, () -> purchaseService.updatePurchase(1000L, purchaseDto1));

        verify(crudPurchaseApi, atMostOnce()).updatePurchase(any(Purchase.class));
    }

    @Test
    void testDeletePurchase() {
        when(crudPurchaseApi.deletePurchase(anyLong(), anyLong())).thenReturn(true);

        purchaseService.deletePurchase(100L, 10L);

        verify(crudPurchaseApi, atMostOnce()).deletePurchase(anyLong(), anyLong());
    }

    @Test
    void testDeletePurchaseNotDeleted() {
        when(crudPurchaseApi.deletePurchase(anyLong(), anyLong())).thenReturn(false);

        assertThrows(EntityDeletionException.class, () -> purchaseService.deletePurchase(100L, 10L));

        verify(crudPurchaseApi, atMostOnce()).deletePurchase(anyLong(), anyLong());
    }

    @Test
    void testGetPurchase() {
        when(crudPurchaseApi.getPurchase(anyLong(), anyLong())).thenReturn(Optional.of(purchase1));

        val res = purchaseService.getPurchase(100L, 10L);

        verify(crudPurchaseApi, atMostOnce()).getPurchase(anyLong(), anyLong());
        assertNotNull(res);
        assertEquals(purchase1.getPurchaseId(), res.getPurchaseId());
        assertEquals(purchase1.getCoins(), res.getCoins());
        assertEquals(purchase1.getName(), res.getName());
        assertEquals(purchase1.getDate(), res.getDate());
        assertEquals(purchase1.getCategory(), res.getCategory());
        assertEquals(purchase1.getCurrency(), res.getCurrency());
    }

    @Test
    void testGetPurchaseNotFound() {
        when(crudPurchaseApi.getPurchase(anyLong(), anyLong())).thenReturn(Optional.empty());

        assertThrows(PurchaseIsNotFoundException.class, () -> purchaseService.getPurchase(100L, 10L));

        verify(crudPurchaseApi, atMostOnce()).getPurchase(anyLong(), anyLong());
    }

    @Test
    void testGetAllForList() {
        // @formatter:off
        when(crudPurchaseApi.getAll(anyLong())).thenReturn(List.of(purchase1, purchase2, purchase3));
        // @formatter:on

        val res = purchaseService.getAllForList(100L);

        verify(crudPurchaseApi, atMostOnce()).getAll(anyLong());
        assertEquals(3, res.size());
    }

    @Test
    void testGetAll() {
        // @formatter:off
        val purchaseList1 = PurchaseList.builder().listUserId(10L).listId(10L).name("list1").build();
        val purchaseList2 = PurchaseList.builder().listUserId(10L).listId(20L).name("list2").build();
        val purchaseList3 = PurchaseList.builder().listUserId(10L).listId(30L).name("list3").build();
        val list = List.of(purchaseList1, purchaseList2, purchaseList3);
        // @formatter:on

        // @formatter:off
        when(crudPurchaseListApi.findAll(anyLong())).thenReturn(list);
        when(crudPurchaseApi.getAll(anyLong())).thenReturn(List.of(purchase1, purchase2), List.of(purchase3), new ArrayList<>());
        // @formatter:on

        val res = purchaseService.getAll(100L);

        verify(crudPurchaseListApi, atMostOnce()).findAll(anyLong());
        verify(crudPurchaseApi, times(3)).getAll(anyLong());
        assertEquals(3, res.size());
    }

    @Test
    void testGetAllNames() {
        // @formatter:off
        val purchaseList1 = PurchaseList.builder().listUserId(10L).listId(10L).name("list1").build();
        val purchaseList2 = PurchaseList.builder().listUserId(10L).listId(20L).name("list2").build();
        val list = List.of(purchaseList1, purchaseList2);
        // @formatter:on

        // @formatter:off
        when(crudPurchaseListApi.findAll(anyLong())).thenReturn(list);
        when(crudPurchaseApi.getAllNames(anyLong())).thenReturn(List.of("purchase1", "purchase2"), List.of("purchase2"));
        // @formatter:on

        val res = purchaseService.getAllNames(100L);

        verify(crudPurchaseListApi, atMostOnce()).findAll(anyLong());
        verify(crudPurchaseApi, times(2)).getAllNames(anyLong());
        assertEquals(2, res.size());
    }
}
