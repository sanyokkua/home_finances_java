package ua.home.finances.finances.db.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.home.finances.finances.db.models.UserAuthInfo;
import ua.home.finances.finances.db.repositories.api.CrudUserAuthApi;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class PostgresqlCrudAppUserAuthApiRepositoryTests extends AbstractRepositoryTests {

    private CrudUserAuthApi crudUserAuthApi;

    @BeforeEach
    void beforeEach() {
        var jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        crudUserAuthApi = new PostgresqlCrudUserAuthApiRepository(jdbcTemplate);
    }

    @Test
    void testCreateUser() {
        var userEmail = "testCreateUser@gmail.com";
        var userPassword = "user_password";

        var userAuthInfoOptional = crudUserAuthApi.findUserAuthInfoByEmail(userEmail);
        assertTrue(userAuthInfoOptional.isEmpty());

        var isUserAuthInfoCreated = crudUserAuthApi.createUserAuthInfo(
                UserAuthInfo.builder().email(userEmail).password(userPassword).build());
        assertTrue(isUserAuthInfoCreated);

        var createdUserOptional = crudUserAuthApi.findUserAuthInfoByEmail(userEmail);
        assertTrue(createdUserOptional.isPresent());
        assertEquals(userEmail, createdUserOptional.get().getEmail());
        assertEquals(userPassword, createdUserOptional.get().getPassword());

    }

    @Test
    void testUpdateUser() {
        var userEmail = "testUpdateUser@gmail.com";
        var userPassword = "user_password";

        var userAuthInfoOptional = crudUserAuthApi.findUserAuthInfoByEmail(userEmail);
        assertTrue(userAuthInfoOptional.isEmpty());

        var isUserAuthInfoCreated = crudUserAuthApi.createUserAuthInfo(
                UserAuthInfo.builder().email(userEmail).password(userPassword).build());
        assertTrue(isUserAuthInfoCreated);

        var createdUserOptional = crudUserAuthApi.findUserAuthInfoByEmail(userEmail);
        assertTrue(createdUserOptional.isPresent());
        assertNotNull(createdUserOptional.get().getUserId());
        assertEquals(userEmail, createdUserOptional.get().getEmail());
        assertEquals(userPassword, createdUserOptional.get().getPassword());

        var updatedUserEmail = "UPDATED_testUpdateUser@gmail.com";
        var updatedUserPassword = "UPDATED_user_password";
        var userToUpdate = createdUserOptional.get();
        userToUpdate.setEmail(updatedUserEmail);
        userToUpdate.setPassword(updatedUserPassword);

        var isUserAuthInfoUpdated = crudUserAuthApi.updateUserAuthInfo(userToUpdate);
        assertTrue(isUserAuthInfoUpdated);

        var userByOldEmailOptional = crudUserAuthApi.findUserAuthInfoByEmail(userEmail);
        assertTrue(userByOldEmailOptional.isEmpty());

        var updateUserOptional = crudUserAuthApi.findUserAuthInfoByEmail(updatedUserEmail);
        assertTrue(updateUserOptional.isPresent());
        assertNotNull(updateUserOptional.get().getUserId());
        assertEquals(updatedUserEmail, updateUserOptional.get().getEmail());
        assertEquals(updatedUserPassword, updateUserOptional.get().getPassword());
    }

    @Test
    void testDeleteUser() {
        var userEmail = "testDeleteUser@gmail.com";
        var userPassword = "user_password";

        var userAuthInfoOptional = crudUserAuthApi.findUserAuthInfoByEmail(userEmail);
        assertTrue(userAuthInfoOptional.isEmpty());

        var created = crudUserAuthApi.createUserAuthInfo(
                UserAuthInfo.builder().email(userEmail).password(userPassword).build());
        assertTrue(created);

        var createUserOptional = crudUserAuthApi.findUserAuthInfoByEmail(userEmail);
        assertTrue(createUserOptional.isPresent());
        assertNotNull(createUserOptional.get().getUserId());

        var isDeleted = crudUserAuthApi.deleteUserAuthInfo(createUserOptional.get().getUserId());
        assertTrue(isDeleted);

        var deletedUserOptional = crudUserAuthApi.findUserAuthInfoByEmail(userEmail);
        assertTrue(deletedUserOptional.isEmpty());
    }

    @Test
    void testFindUserByEmail() {
        var user1Optional = crudUserAuthApi.findUserAuthInfoByEmail("user1@gmail.com");
        var user2Optional = crudUserAuthApi.findUserAuthInfoByEmail("user2@gmail.com");
        var user3Optional = crudUserAuthApi.findUserAuthInfoByEmail("user3@gmail.com");
        var user4Optional = crudUserAuthApi.findUserAuthInfoByEmail("user4@gmail.com");
        var nonExistingUser = crudUserAuthApi.findUserAuthInfoByEmail("non-existing@mail.org");

        assertTrue(user1Optional.isPresent());
        assertTrue(user2Optional.isPresent());
        assertTrue(user3Optional.isPresent());
        assertTrue(user4Optional.isPresent());
        assertTrue(nonExistingUser.isEmpty());
    }
}
