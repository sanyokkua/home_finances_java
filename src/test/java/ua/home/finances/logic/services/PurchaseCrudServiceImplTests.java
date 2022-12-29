package ua.home.finances.logic.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.home.finances.logic.common.Currency;
import ua.home.finances.logic.db.models.Purchase;
import ua.home.finances.logic.db.repositories.api.PurchaseCrudJdbcRepository;
import ua.home.finances.logic.db.repositories.api.PurchaseListCrudJdbcRepository;
import ua.home.finances.logic.services.dtos.PurchaseDto;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
class PurchaseCrudServiceImplTests {
    @Mock
    PurchaseListCrudJdbcRepository crudPurchaseListApi;
    @Mock
    PurchaseCrudJdbcRepository crudPurchaseApi;
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

}
