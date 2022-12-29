package ua.home.finances.logic.repositories;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.home.finances.logic.common.Currency;
import ua.home.finances.logic.db.models.Purchase;
import ua.home.finances.logic.db.models.PurchaseList;
import ua.home.finances.logic.db.repositories.ApplicationUserCrudJdbcRepositoryImpl;
import ua.home.finances.logic.db.repositories.PurchaseCrudJdbcRepositoryImpl;
import ua.home.finances.logic.db.repositories.PurchaseListCrudJdbcRepositoryImpl;
import ua.home.finances.logic.db.repositories.api.ApplicationUserCrudJdbcRepository;
import ua.home.finances.logic.db.repositories.api.PurchaseCrudJdbcRepository;
import ua.home.finances.logic.db.repositories.api.PurchaseListCrudJdbcRepository;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class PurchaseCrudJdbcRepositoryTests extends AbstractRepositoryTests {

    private PurchaseCrudJdbcRepository crudJdbcRepository;
    private PurchaseListCrudJdbcRepository purchaseListCrudJdbcRepository;
    private ApplicationUserCrudJdbcRepository userCrudJdbcRepository;

    static Purchase createEntity(long listId, String name) {
        return Purchase.builder()
                .purchaseListId(listId)
                .coins(10000)
                .currency(Currency.USD)
                .name(name)
                .date(LocalDate.now())
                .category("default")
                .build();
    }

    @BeforeEach
    void beforeEach() {
        var jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        crudJdbcRepository = new PurchaseCrudJdbcRepositoryImpl(jdbcTemplate);
        purchaseListCrudJdbcRepository = new PurchaseListCrudJdbcRepositoryImpl(jdbcTemplate);
        userCrudJdbcRepository = new ApplicationUserCrudJdbcRepositoryImpl(jdbcTemplate);
    }

    @Test
    void testCreate() {
        val user = userCrudJdbcRepository.findByEmail("user2@gmail.com");
        val listEntity = PurchaseList.builder().name("testCreate").listUserId(user.get().getUserId()).build();
        val list = purchaseListCrudJdbcRepository.create(listEntity);

        val entity = createEntity(list.getListId(), "testCreate");

        val created = crudJdbcRepository.create(entity);

        assertNotNull(created);
        assertNotNull(created.getPurchaseId());
    }

    @Test
    void testUpdate() {
        val user = userCrudJdbcRepository.findByEmail("user2@gmail.com");
        val listEntity = PurchaseList.builder().name("testUpdate").listUserId(user.get().getUserId()).build();
        val list = purchaseListCrudJdbcRepository.create(listEntity);

        val entity = createEntity(list.getListId(), "testUpdate");

        val created = crudJdbcRepository.create(entity);

        assertNotNull(created);
        assertNotNull(created.getPurchaseId());

        created.setName("UPDATED");

        val updated = crudJdbcRepository.update(created);

        assertNotNull(updated);
        assertNotNull(updated.getPurchaseId());
        assertEquals("UPDATED", updated.getName());
    }

    @Test
    void testDelete() {
        val user = userCrudJdbcRepository.findByEmail("user2@gmail.com");
        val listEntity = PurchaseList.builder().name("testDelete").listUserId(user.get().getUserId()).build();
        val list = purchaseListCrudJdbcRepository.create(listEntity);

        val entity = createEntity(list.getListId(), "testDelete");

        val created = crudJdbcRepository.create(entity);

        assertNotNull(created);
        assertNotNull(created.getPurchaseId());

        val deleteRes = crudJdbcRepository.delete(created.getPurchaseId());

        assertTrue(deleteRes);

        val deleteNotExisting = crudJdbcRepository.delete(Long.MAX_VALUE);

        assertFalse(deleteNotExisting);

        val found = crudJdbcRepository.findById(created.getPurchaseId());

        assertTrue(found.isEmpty());
    }

    @Test
    void testFindById() {
        val user = userCrudJdbcRepository.findByEmail("user2@gmail.com");
        val listEntity = PurchaseList.builder().name("testFindById").listUserId(user.get().getUserId()).build();
        val list = purchaseListCrudJdbcRepository.create(listEntity);

        val entity = createEntity(list.getListId(), "testFindById");

        val created = crudJdbcRepository.create(entity);

        assertNotNull(created);
        assertNotNull(created.getPurchaseId());

        val found = crudJdbcRepository.findById(created.getPurchaseId());

        assertTrue(found.isPresent());

        val notFound = crudJdbcRepository.findById(Long.MAX_VALUE);

        assertTrue(notFound.isEmpty());
    }

    @Test
    void testFindAll() {
        val user = userCrudJdbcRepository.findByEmail("user3@gmail.com");
        val listEntity = PurchaseList.builder().name("testFindAll").listUserId(user.get().getUserId()).build();
        val list = purchaseListCrudJdbcRepository.create(listEntity);

        val res = crudJdbcRepository.findAll(list.getListId());

        assertNotNull(res);
        assertTrue(res.isEmpty());

        crudJdbcRepository.create(createEntity(list.getListId(), "entity1"));
        crudJdbcRepository.create(createEntity(list.getListId(), "entity2"));
        crudJdbcRepository.create(createEntity(list.getListId(), "entity3"));

        val newRes = crudJdbcRepository.findAll(list.getListId());

        assertNotNull(newRes);
        assertFalse(newRes.isEmpty());
        assertEquals(3, newRes.size());
    }

    @Test
    void testFindAllNames() {
        val user = userCrudJdbcRepository.findByEmail("user4@gmail.com");
        val listEntity = PurchaseList.builder().name("testFindAllNames").listUserId(user.get().getUserId()).build();
        val list = purchaseListCrudJdbcRepository.create(listEntity);

        val res = crudJdbcRepository.findAllNames(list.getListId());

        assertNotNull(res);
        assertTrue(res.isEmpty());

        crudJdbcRepository.create(createEntity(list.getListId(), "entity1"));
        crudJdbcRepository.create(createEntity(list.getListId(), "entity2"));
        crudJdbcRepository.create(createEntity(list.getListId(), "entity2"));

        val newRes = crudJdbcRepository.findAllNames(list.getListId());

        assertNotNull(newRes);
        assertFalse(newRes.isEmpty());
        assertEquals(2, newRes.size());
    }

}
