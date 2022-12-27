package ua.home.finances.finances.db.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.home.finances.finances.db.models.PurchaseList;
import ua.home.finances.finances.db.repositories.api.CrudAppUserApi;
import ua.home.finances.finances.db.repositories.api.CrudPurchaseListApi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
class PostgresqlCrudPurchaseListServiceRepositoryTests extends AbstractRepositoryTests {
    private CrudPurchaseListApi crudPurchaseListApi;
    private CrudAppUserApi crudAppUserApi;

    @BeforeEach
    void beforeEach() {
        var jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        crudPurchaseListApi = new PostgresqlCrudPurchaseListApiRepository(jdbcTemplate);
        crudAppUserApi = new PostgresqlCrudAppUserApiRepository(jdbcTemplate);
    }

    @Test
    void testCreatePurchaseList() {
        var user1Optional = crudAppUserApi.findAppUserByNickname("user1");
        assertTrue(user1Optional.isPresent());

        long userId = user1Optional.get().getUserId();
        var listName = "LIST_1";

        var purchaseListOptional = crudPurchaseListApi.findByName(userId, listName);
        assertTrue(purchaseListOptional.isEmpty());

        var isListCreated = crudPurchaseListApi.createPurchaseList(
                PurchaseList.builder().listUserId(userId).name(listName).build());
        assertTrue(isListCreated);

        var foundCreatedListOptional = crudPurchaseListApi.findByName(userId, listName);
        assertTrue(foundCreatedListOptional.isPresent());
    }

    @Test
    void testUpdatePurchaseList() {
        var user2Optional = crudAppUserApi.findAppUserByNickname("user2");
        assertTrue(user2Optional.isPresent());

        long userId = user2Optional.get().getUserId();
        var listName = "LIST_1";

        var purchaseListOptional = crudPurchaseListApi.findByName(userId, listName);
        assertTrue(purchaseListOptional.isEmpty());

        var isListCreated = crudPurchaseListApi.createPurchaseList(
                PurchaseList.builder().listUserId(userId).name(listName).build());
        assertTrue(isListCreated);

        var foundCreatedListOptional = crudPurchaseListApi.findByName(userId, listName);
        assertTrue(foundCreatedListOptional.isPresent());

        var updatedListName = "updated_name";
        var updateList = foundCreatedListOptional.get();
        updateList.setName(updatedListName);
        var isListUpdated = crudPurchaseListApi.updatePurchaseList(updateList);
        assertTrue(isListUpdated);

        var foundOldListOptional = crudPurchaseListApi.findByName(userId, listName);
        assertTrue(foundOldListOptional.isEmpty());

        var foundUpdatedListOptional = crudPurchaseListApi.findByName(userId, updatedListName);
        assertTrue(foundUpdatedListOptional.isPresent());
    }

    @Test
    void testDeletePurchaseList() {
        var user3Optional = crudAppUserApi.findAppUserByNickname("user3");
        assertTrue(user3Optional.isPresent());

        long userId = user3Optional.get().getUserId();
        var listName = "LIST_1";

        var foundListOptional = crudPurchaseListApi.findByName(userId, listName);
        assertTrue(foundListOptional.isEmpty());

        var isListCreated = crudPurchaseListApi.createPurchaseList(
                PurchaseList.builder().listUserId(userId).name(listName).build());
        assertTrue(isListCreated);

        var foundCreatedListOptional = crudPurchaseListApi.findByName(userId, listName);
        assertTrue(foundCreatedListOptional.isPresent());

        var isListDeleted = crudPurchaseListApi.deletePurchaseList(userId, foundCreatedListOptional.get().getListId());
        assertTrue(isListDeleted);

        var deletedIsFoundOptional = crudPurchaseListApi.findByName(userId, listName);
        assertTrue(deletedIsFoundOptional.isEmpty());
    }

    @Test
    void testFindById() {
        var user4Optional = crudAppUserApi.findAppUserByNickname("user1");
        assertTrue(user4Optional.isPresent());

        long userId = user4Optional.get().getUserId();

        var foundListByNameOptional = crudPurchaseListApi.findByName(userId, "DEFAULT");
        assertTrue(foundListByNameOptional.isPresent());

        long listId = foundListByNameOptional.get().getListId();
        var foundListByIdOptional = crudPurchaseListApi.findById(userId, listId);
        assertTrue(foundListByIdOptional.isPresent());
        assertEquals(foundListByNameOptional.get().getName(), foundListByIdOptional.get().getName());
    }

    @Test
    void testFindByName() {
        var user4Optional = crudAppUserApi.findAppUserByNickname("user1");
        assertTrue(user4Optional.isPresent());

        long userId = user4Optional.get().getUserId();
        var foundListByNameOptional = crudPurchaseListApi.findByName(userId, "DEFAULT");
        assertTrue(foundListByNameOptional.isPresent());
    }

    @Test
    void testFindAll() {
        var user4Optional = crudAppUserApi.findAppUserByNickname("user4");
        assertTrue(user4Optional.isPresent());

        long userId = user4Optional.get().getUserId();

        var defaultListOptional = crudPurchaseListApi.findByName(userId, "DEFAULT");
        assertTrue(defaultListOptional.isPresent());

        var allUserLists = crudPurchaseListApi.findAll(userId);
        assertEquals(1, allUserLists.size());

        var listName = "LIST_1";
        var foundListOptional = crudPurchaseListApi.findByName(userId, listName);
        assertTrue(foundListOptional.isEmpty());

        var isListCreated = crudPurchaseListApi.createPurchaseList(
                PurchaseList.builder().listUserId(userId).name(listName).build());
        assertTrue(isListCreated);

        var allListsAfterCreationOneAdditionalList = crudPurchaseListApi.findAll(userId);
        assertEquals(2, allListsAfterCreationOneAdditionalList.size());
    }
}
