package ua.home.finances.finances.db.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.home.finances.finances.common.Currency;
import ua.home.finances.finances.db.models.Purchase;
import ua.home.finances.finances.db.repositories.api.CrudAppUserApi;
import ua.home.finances.finances.db.repositories.api.CrudPurchaseApi;
import ua.home.finances.finances.db.repositories.api.CrudPurchaseListApi;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class PostgresqlCrudPurchaseApiRepositoryTests extends AbstractRepositoryTests {

    private CrudPurchaseApi crudPurchaseApi;
    private CrudPurchaseListApi crudPurchaseListApi;
    private CrudAppUserApi crudAppUserApi;

    static Purchase createIphone(long listId, String name) {
        return Purchase.builder()
                .purchaseListId(listId)
                .coins(10000)
                .currency(Currency.USD)
                .name(name)
                .date(LocalDate.now())
                .build();
    }

    @BeforeEach
    void beforeEach() {
        var jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        crudPurchaseApi = new PostgresqlCrudPurchaseApiRepository(jdbcTemplate);
        crudPurchaseListApi = new PostgresqlCrudPurchaseListApiRepository(jdbcTemplate);
        crudAppUserApi = new PostgresqlCrudAppUserApiRepository(jdbcTemplate);
    }

    @Test
    void testCreatePurchase() {
        var user1Optional = crudAppUserApi.findAppUserByNickname("user1");
        assertTrue(user1Optional.isPresent());

        var listOptional = crudPurchaseListApi.findByName(user1Optional.get().getUserId(), "DEFAULT");
        assertTrue(listOptional.isPresent());

        long listId = listOptional.get().getListId();

        var isPurchaseCreated = crudPurchaseApi.createPurchase(createIphone(listId, "iPhone XXX"));
        assertTrue(isPurchaseCreated);

        var allPurchases = crudPurchaseApi.getAll(listId);
        assertNotNull(allPurchases);
        assertEquals(2, allPurchases.size()); //DB already had 1, so +1 = 2

    }

    @Test
    void testUpdatePurchase() {
        var user2Optional = crudAppUserApi.findAppUserByNickname("user2");
        assertTrue(user2Optional.isPresent());

        var listOptional = crudPurchaseListApi.findByName(user2Optional.get().getUserId(), "DEFAULT");
        assertTrue(listOptional.isPresent());

        long listId = listOptional.get().getListId();
        var purchaseName = "iPhone XXX";

        var isPurchaseCreated = crudPurchaseApi.createPurchase(createIphone(listId, purchaseName));
        assertTrue(isPurchaseCreated);

        var allPurchases = crudPurchaseApi.getAll(listId);
        assertNotNull(allPurchases);
        assertEquals(2, allPurchases.size());

        var purchase = allPurchases.stream().filter(p -> purchaseName.equals(p.getName())).findFirst();
        assertTrue(purchase.isPresent());

        var updatedPurchaseName = "Galaxy S300";
        var purchaseToUpdate = purchase.get();
        purchaseToUpdate.setName(updatedPurchaseName);
        var isPurchaseUpdated = crudPurchaseApi.updatePurchase(purchaseToUpdate);
        assertTrue(isPurchaseUpdated);

        var allPurchasesUpdated = crudPurchaseApi.getAll(listId);
        assertNotNull(allPurchasesUpdated);
        assertEquals(2, allPurchasesUpdated.size());

        var oldPurchaseOptional = allPurchasesUpdated.stream()
                .filter(p -> purchaseName.equals(p.getName()))
                .findFirst();
        assertTrue(oldPurchaseOptional.isEmpty());

        var updatedPurchaseOptional = allPurchasesUpdated.stream()
                .filter(p -> updatedPurchaseName.equals(p.getName()))
                .findFirst();
        assertTrue(updatedPurchaseOptional.isPresent());
    }

    @Test
    void testDeletePurchase() {
        var user3Optional = crudAppUserApi.findAppUserByNickname("user3");
        assertTrue(user3Optional.isPresent());

        var listOptional = crudPurchaseListApi.findByName(user3Optional.get().getUserId(), "DEFAULT");
        assertTrue(listOptional.isPresent());

        long listId = listOptional.get().getListId();

        var allPurchases = crudPurchaseApi.getAll(listId);
        assertEquals(1, allPurchases.size());

        var purchaseOptional = allPurchases.stream().findAny();
        assertTrue(purchaseOptional.isPresent());

        var isPurchaseDeleted = crudPurchaseApi.deletePurchase(listId, purchaseOptional.get().getPurchaseId());
        assertTrue(isPurchaseDeleted);

        var allPurchasesUpdated = crudPurchaseApi.getAll(listId);
        assertTrue(allPurchasesUpdated.isEmpty());
    }

    @Test
    void testGetPurchase() {
        var user4Optional = crudAppUserApi.findAppUserByNickname("user4");
        assertTrue(user4Optional.isPresent());

        long userId = user4Optional.get().getUserId();

        var listOptional = crudPurchaseListApi.findByName(userId, "DEFAULT");
        assertTrue(listOptional.isPresent());

        long listId = listOptional.get().getListId();

        var all = crudPurchaseApi.getAll(listId);
        assertEquals(1, all.size());

        var purchase = all.stream().findAny();
        assertTrue(purchase.isPresent());

        var foundPurchase = crudPurchaseApi.getPurchase(userId, purchase.get().getPurchaseId());
        assertTrue(foundPurchase.isPresent());
        assertEquals(purchase.get().getPurchaseId(), foundPurchase.get().getPurchaseId());
    }

    @Test
    void testGetAll() {
        var user5Optional = crudAppUserApi.findAppUserByNickname("user5");
        assertTrue(user5Optional.isPresent());

        var listOptional = crudPurchaseListApi.findByName(user5Optional.get().getUserId(), "DEFAULT");
        assertTrue(listOptional.isPresent());

        long listId = listOptional.get().getListId();

        var isPurchaseCreated1 = crudPurchaseApi.createPurchase(createIphone(listId, "iPhone 10"));
        assertTrue(isPurchaseCreated1);

        var isPurchaseCreated2 = crudPurchaseApi.createPurchase(createIphone(listId, "iPhone 11"));
        assertTrue(isPurchaseCreated2);

        var isPurchaseCreated3 = crudPurchaseApi.createPurchase(createIphone(listId, "iPhone 12"));
        assertTrue(isPurchaseCreated3);

        var result = crudPurchaseApi.getAll(listId);
        assertEquals(3, result.size());
    }

    @Test
    void testGetAllNames() {
        var user6Optional = crudAppUserApi.findAppUserByNickname("user6");
        assertTrue(user6Optional.isPresent());

        var listOptional = crudPurchaseListApi.findByName(user6Optional.get().getUserId(), "DEFAULT");
        assertTrue(listOptional.isPresent());

        long listId = listOptional.get().getListId();

        var isPurchaseCreated1 = crudPurchaseApi.createPurchase(createIphone(listId, "iPhone 10"));
        assertTrue(isPurchaseCreated1);

        var isPurchaseCreated2 = crudPurchaseApi.createPurchase(createIphone(listId, "iPhone 11"));
        assertTrue(isPurchaseCreated2);

        var isPurchaseCreated3 = crudPurchaseApi.createPurchase(createIphone(listId, "iPhone 12"));
        assertTrue(isPurchaseCreated3);

        var isPurchaseCreated4 = crudPurchaseApi.createPurchase(createIphone(listId, "iPhone 12"));
        assertTrue(isPurchaseCreated4);

        var allPurchases = crudPurchaseApi.getAll(listId);
        assertEquals(4, allPurchases.size());

        var allPurchasesNames = crudPurchaseApi.getAllNames(listId);
        assertEquals(3, allPurchasesNames.size());
    }

}
