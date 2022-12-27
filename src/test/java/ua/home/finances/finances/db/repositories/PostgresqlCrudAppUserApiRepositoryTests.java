package ua.home.finances.finances.db.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.home.finances.finances.db.models.AppUser;
import ua.home.finances.finances.db.models.UserAuthInfo;
import ua.home.finances.finances.db.repositories.api.CrudAppUserApi;
import ua.home.finances.finances.db.repositories.api.CrudUserAuthApi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
class PostgresqlCrudAppUserApiRepositoryTests extends AbstractRepositoryTests {
    private CrudAppUserApi crudAppUserApi;
    private CrudUserAuthApi crudUserAuthApi;

    @BeforeEach
    void beforeEach() {
        var jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        crudAppUserApi = new PostgresqlCrudAppUserApiRepository(jdbcTemplate);
        crudUserAuthApi = new PostgresqlCrudUserAuthApiRepository(jdbcTemplate);
    }

    @Test
    void testCreateUser() {
        var emailOfNewUser = "testCreateUser@gmail.com";
        var userPassword = "user_password";

        var userOptional = crudUserAuthApi.findUserAuthInfoByEmail(emailOfNewUser);
        assertTrue(userOptional.isEmpty());

        var isCreatedUserAuth = crudUserAuthApi.createUserAuthInfo(
                UserAuthInfo.builder().email(emailOfNewUser).password(userPassword).build());
        assertTrue(isCreatedUserAuth);

        var createUserAuthOptional = crudUserAuthApi.findUserAuthInfoByEmail(emailOfNewUser);
        assertTrue(createUserAuthOptional.isPresent());

        var userNickname = "userTestNick";
        var isCreatedUser = crudAppUserApi.createAppUser(
                AppUser.builder().userId(createUserAuthOptional.get().getUserId()).nickname(userNickname).build());
        assertTrue(isCreatedUser);

        var foundUserOptional = crudAppUserApi.findAppUserByNickname(userNickname);
        assertTrue(foundUserOptional.isPresent());
        assertEquals(userNickname, foundUserOptional.get().getNickname());

    }

    @Test
    void testUpdateUser() {
        var emailOfNewUser = "testUpdateUser@gmail.com";
        var userPassword = "user_password";

        var userAuthOptional = crudUserAuthApi.findUserAuthInfoByEmail(emailOfNewUser);
        assertTrue(userAuthOptional.isEmpty());

        var isCreatedUserAuth = crudUserAuthApi.createUserAuthInfo(
                UserAuthInfo.builder().email(emailOfNewUser).password(userPassword).build());
        assertTrue(isCreatedUserAuth);

        var createdUserAuthOptional = crudUserAuthApi.findUserAuthInfoByEmail(emailOfNewUser);
        assertTrue(createdUserAuthOptional.isPresent());

        var userNickname = "userTestNick2";
        var isCreatedUser = crudAppUserApi.createAppUser(
                AppUser.builder().userId(createdUserAuthOptional.get().getUserId()).nickname(userNickname).build());
        assertTrue(isCreatedUser);

        var foundUserOptional = crudAppUserApi.findAppUserByNickname(userNickname);
        assertTrue(foundUserOptional.isPresent());
        assertEquals(userNickname, foundUserOptional.get().getNickname());

        var updatedUserNickname = "UPDATED_userTestNick2";
        var foundUser = foundUserOptional.get();
        foundUser.setNickname(updatedUserNickname);
        var isUpdatedUser = crudAppUserApi.updateAppUser(foundUser);
        assertTrue(isUpdatedUser);

        var oldNicknameUserOptional = crudAppUserApi.findAppUserByNickname(userNickname);
        assertTrue(oldNicknameUserOptional.isEmpty());

        var newNicknameUserOptional = crudAppUserApi.findAppUserByNickname(updatedUserNickname);
        assertTrue(newNicknameUserOptional.isPresent());
    }

    @Test
    void testDeleteUser() {
        var emailOfNewUser = "testDeleteUser@gmail.com";
        var userPassword = "user_password";

        var userAuthOptional = crudUserAuthApi.findUserAuthInfoByEmail(emailOfNewUser);
        assertTrue(userAuthOptional.isEmpty());

        var isCreatedUserAuth = crudUserAuthApi.createUserAuthInfo(
                UserAuthInfo.builder().email(emailOfNewUser).password(userPassword).build());
        assertTrue(isCreatedUserAuth);

        var foundCreatedUserAuthOptional = crudUserAuthApi.findUserAuthInfoByEmail(emailOfNewUser);
        assertTrue(foundCreatedUserAuthOptional.isPresent());

        var userNickname = "userTestNick3";
        var isCreatedUser = crudAppUserApi.createAppUser(AppUser.builder()
                                                                 .userId(foundCreatedUserAuthOptional.get().getUserId())
                                                                 .nickname(userNickname)
                                                                 .build());
        assertTrue(isCreatedUser);

        var foundUserOptional = crudAppUserApi.findAppUserByNickname(userNickname);
        assertTrue(foundUserOptional.isPresent());
        assertEquals(userNickname, foundUserOptional.get().getNickname());

        var isDeletedUser = crudAppUserApi.deleteAppUser(foundUserOptional.get().getUserId());
        assertTrue(isDeletedUser);

        var deletedUserOptional = crudAppUserApi.findAppUserByNickname(userNickname);
        assertTrue(deletedUserOptional.isEmpty());
    }

    @Test
    void testFindUserByNickname() {
        var foundUser1Optional = crudAppUserApi.findAppUserByNickname("user1");
        assertTrue(foundUser1Optional.isPresent());

        var foundUser2Optional = crudAppUserApi.findAppUserByNickname("user2");
        assertTrue(foundUser2Optional.isPresent());

        var foundNonExistingUserOptional = crudAppUserApi.findAppUserByNickname("notExisting");
        assertTrue(foundNonExistingUserOptional.isEmpty());
    }

    @Test
    void testFindUserById() {
        var foundUser1Optional = crudAppUserApi.findAppUserByNickname("user1");
        assertTrue(foundUser1Optional.isPresent());

        var foundUser2Optional = crudAppUserApi.findAppUserById(foundUser1Optional.get().getUserId());
        assertTrue(foundUser2Optional.isPresent());

        assertEquals(foundUser1Optional.get().getNickname(), foundUser2Optional.get().getNickname());
    }
}
