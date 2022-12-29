package ua.home.finances.logic.repositories;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.home.finances.logic.db.models.PurchaseList;
import ua.home.finances.logic.db.repositories.ApplicationUserCrudJdbcRepositoryImpl;
import ua.home.finances.logic.db.repositories.PurchaseListCrudJdbcRepositoryImpl;
import ua.home.finances.logic.db.repositories.api.ApplicationUserCrudJdbcRepository;
import ua.home.finances.logic.db.repositories.api.PurchaseListCrudJdbcRepository;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class PostgresqlCrudPurchaseListServiceRepositoryTests extends AbstractRepositoryTests {
    private PurchaseListCrudJdbcRepository crudJdbcRepository;
    private ApplicationUserCrudJdbcRepository userCrudJdbcRepository;

    @BeforeEach
    void beforeEach() {
        var jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        crudJdbcRepository = new PurchaseListCrudJdbcRepositoryImpl(jdbcTemplate);
        userCrudJdbcRepository = new ApplicationUserCrudJdbcRepositoryImpl(jdbcTemplate);
    }

    @Test
    void testCreate() {
        val user = userCrudJdbcRepository.findByEmail("user1@gmail.com");
        val entity = PurchaseList.builder().name("testCreate").listUserId(user.get().getUserId()).build();

        val created = crudJdbcRepository.create(entity);

        assertNotNull(created);
        assertNotNull(created.getListId());
    }

    @Test
    void testUpdate() {
        val user = userCrudJdbcRepository.findByEmail("user1@gmail.com");
        val entity = PurchaseList.builder().name("testUpdate").listUserId(user.get().getUserId()).build();

        val created = crudJdbcRepository.create(entity);

        assertNotNull(created);
        assertNotNull(created.getListId());

        created.setName("UPDATED");

        val updated = crudJdbcRepository.update(created);

        assertNotNull(updated);
        assertNotNull(updated.getListId());
        assertEquals("UPDATED", updated.getName());
    }

    @Test
    void testDelete() {
        val user = userCrudJdbcRepository.findByEmail("user1@gmail.com");
        val entity = PurchaseList.builder().name("testDelete").listUserId(user.get().getUserId()).build();

        val created = crudJdbcRepository.create(entity);

        assertNotNull(created);
        assertNotNull(created.getListId());

        val deleteRes = crudJdbcRepository.delete(created.getListId());

        assertTrue(deleteRes);

        val deleteNotExisting = crudJdbcRepository.delete(Long.MAX_VALUE);

        assertFalse(deleteNotExisting);

        val found = crudJdbcRepository.findById(created.getListId());

        assertTrue(found.isEmpty());
    }

    @Test
    void testFindById() {
        val user = userCrudJdbcRepository.findByEmail("user1@gmail.com");
        val entity = PurchaseList.builder().name("testFindById").listUserId(user.get().getUserId()).build();

        val created = crudJdbcRepository.create(entity);

        assertNotNull(created);
        assertNotNull(created.getListId());

        val found = crudJdbcRepository.findById(created.getListId());

        assertTrue(found.isPresent());

        val notFound = crudJdbcRepository.findById(Long.MAX_VALUE);

        assertTrue(notFound.isEmpty());
    }

    @Test
    void testFindAll() {
        val user = userCrudJdbcRepository.findByEmail("user1@gmail.com");

        val res = crudJdbcRepository.findAll(user.get().getUserId());

        assertNotNull(res);
        assertFalse(res.isEmpty());
    }
}
