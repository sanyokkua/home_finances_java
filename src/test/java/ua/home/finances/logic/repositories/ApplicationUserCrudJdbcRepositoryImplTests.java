package ua.home.finances.logic.repositories;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.home.finances.logic.common.UserRoles;
import ua.home.finances.logic.db.models.ApplicationUser;
import ua.home.finances.logic.db.repositories.ApplicationUserCrudJdbcRepositoryImpl;
import ua.home.finances.logic.db.repositories.api.ApplicationUserCrudJdbcRepository;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class ApplicationUserCrudJdbcRepositoryImplTests extends AbstractRepositoryTests {
    private ApplicationUserCrudJdbcRepository crudJdbcRepository;

    @BeforeEach
    void beforeEach() {
        var jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        crudJdbcRepository = new ApplicationUserCrudJdbcRepositoryImpl(jdbcTemplate);
    }

    @Test
    void testCreate() {
        val entity = ApplicationUser.builder()
                .email("testCreate@email.com")
                .password("testing")
                .nickname("testCreate")
                .role(UserRoles.ADMIN)
                .build();

        val created = crudJdbcRepository.create(entity);

        assertNotNull(created);
        assertNotNull(created.getUserId());
        assertEquals(ua.home.finances.logic.common.UserRoles.ADMIN, created.getRole());
    }

    @Test
    void testUpdate() {
        val entity = ApplicationUser.builder()
                .email("testUpdate@email.com")
                .password("testing")
                .nickname("testUpdate")
                .build();

        val created = crudJdbcRepository.create(entity);

        assertNotNull(created);
        assertNotNull(created.getUserId());
        assertEquals(UserRoles.USER, created.getRole());

        created.setNickname("UPDATED");
        created.setRole(UserRoles.ADMIN);

        val updated = crudJdbcRepository.update(created);

        assertNotNull(updated);
        assertNotNull(updated.getUserId());
        assertEquals("UPDATED", updated.getNickname());
        assertEquals(UserRoles.ADMIN, updated.getRole());
    }

    @Test
    void testDelete() {
        val entity = ApplicationUser.builder()
                .email("testDelete@email.com")
                .password("testDelete")
                .nickname("testDelete")
                .build();

        val created = crudJdbcRepository.create(entity);

        assertNotNull(created);
        assertNotNull(created.getUserId());

        val deleteRes = crudJdbcRepository.delete(created.getUserId());

        assertTrue(deleteRes);

        val deleteNotExisting = crudJdbcRepository.delete(Long.MAX_VALUE);

        assertFalse(deleteNotExisting);

        val found = crudJdbcRepository.findById(created.getUserId());

        assertTrue(found.isEmpty());
    }

    @Test
    void testFindById() {
        val entity = ApplicationUser.builder()
                .email("testFindById@email.com")
                .password("testFindById")
                .nickname("testFindById")
                .build();

        val created = crudJdbcRepository.create(entity);

        assertNotNull(created);
        assertNotNull(created.getUserId());

        val found = crudJdbcRepository.findById(created.getUserId());

        assertTrue(found.isPresent());

        val notFound = crudJdbcRepository.findById(Long.MAX_VALUE);

        assertTrue(notFound.isEmpty());
    }

    @Test
    void testFindByEmail() {
        val entity = ApplicationUser.builder()
                .email("testFindByEmail@email.com")
                .password("testFindByEmail")
                .nickname("testFindByEmail")
                .build();

        val created = crudJdbcRepository.create(entity);

        assertNotNull(created);
        assertNotNull(created.getUserId());

        val found = crudJdbcRepository.findByEmail("testFindByEmail@email.com");

        assertTrue(found.isPresent());

        val notFound = crudJdbcRepository.findByEmail("non-existing-email@goo.eu");

        assertTrue(notFound.isEmpty());
    }

    @Test
    void testFindAll() {
        val res = crudJdbcRepository.findAll();

        assertNotNull(res);
        assertFalse(res.isEmpty());
    }

}
