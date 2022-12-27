package ua.home.finances.finances.services;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.StringUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ua.home.finances.finances.common.exceptions.*;
import ua.home.finances.finances.db.models.AppUser;
import ua.home.finances.finances.db.models.UserAuthInfo;
import ua.home.finances.finances.db.repositories.api.CrudAppUserApi;
import ua.home.finances.finances.db.repositories.api.CrudUserAuthApi;
import ua.home.finances.finances.services.dtos.UserDto;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTests {
    @Mock
    CrudUserAuthApi mockCrudUserAuthApi;
    @Mock
    CrudAppUserApi mockCrudAppUserApi;
    @Mock
    PasswordEncoder mockPasswordEncoder;
    @InjectMocks
    UserServiceImpl userService;

    UserDto TEST_USER_DTO;
    UserAuthInfo TEST_USER_AUTH_INFO;
    AppUser TEST_APP_USER;

    @BeforeEach
    void beforeEach() {
        TEST_USER_DTO = UserDto.builder()
                .userId(1000L)
                .email("test@email.com")
                .nickname("nick")
                .password("password")
                .build();
        TEST_USER_AUTH_INFO = UserAuthInfo.builder()
                .userId(TEST_USER_DTO.getUserId())
                .email(TEST_USER_DTO.getEmail())
                .password(TEST_USER_DTO.getPassword())
                .build();
        TEST_APP_USER = AppUser.builder()
                .userId(TEST_USER_DTO.getUserId())
                .nickname(TEST_USER_DTO.getNickname())
                .build();
    }

    @Test
    void testCreateUser() {
        // @formatter:off
        when(mockCrudUserAuthApi.findUserAuthInfoByEmail(anyString())).thenReturn(Optional.empty(), Optional.of(TEST_USER_AUTH_INFO));
        when(mockPasswordEncoder.encode(anyString())).thenReturn("password");
        when(mockCrudUserAuthApi.createUserAuthInfo(any(UserAuthInfo.class))).thenReturn(true);
        when(mockCrudAppUserApi.createAppUser(any(AppUser.class))).thenReturn(true);
        when(mockCrudAppUserApi.findAppUserByNickname(anyString())).thenReturn(Optional.of(TEST_APP_USER));
        // @formatter:on

        userService.createUser(TEST_USER_DTO);

        verify(mockCrudUserAuthApi, times(2)).findUserAuthInfoByEmail(anyString());
        verify(mockPasswordEncoder, atMostOnce()).encode(anyString());
        verify(mockCrudUserAuthApi, atMostOnce()).createUserAuthInfo(any(UserAuthInfo.class));
        verify(mockCrudAppUserApi, atMostOnce()).createAppUser(any(AppUser.class));
        verify(mockCrudAppUserApi, atMostOnce()).findAppUserByNickname(anyString());
    }

    @Test
    void testCreateUserWhenAlreadyExists() {
        when(mockCrudUserAuthApi.findUserAuthInfoByEmail(anyString())).thenReturn(Optional.of(TEST_USER_AUTH_INFO));

        assertThrows(UserWithEmailAlreadyExistsException.class, () -> userService.createUser(TEST_USER_DTO));
        verify(mockCrudUserAuthApi, atMostOnce()).findUserAuthInfoByEmail(anyString());
        verify(mockCrudAppUserApi, never()).createAppUser(any(AppUser.class));
        verify(mockCrudAppUserApi, never()).findAppUserByNickname(anyString());
    }

    @Test
    void testCreateUserWhenIsNotCreated() {
        // @formatter:off
        when(mockCrudUserAuthApi.findUserAuthInfoByEmail(anyString())).thenReturn(Optional.empty());
        when(mockPasswordEncoder.encode(anyString())).thenReturn("password");
        when(mockCrudUserAuthApi.createUserAuthInfo(any(UserAuthInfo.class))).thenReturn(Boolean.FALSE);
        // @formatter:on

        assertThrows(EntityCreationException.class, () -> userService.createUser(TEST_USER_DTO));
        verify(mockCrudUserAuthApi, atMostOnce()).findUserAuthInfoByEmail(anyString());
        verify(mockPasswordEncoder, atMostOnce()).encode(anyString());
        verify(mockCrudUserAuthApi, atMostOnce()).createUserAuthInfo(any(UserAuthInfo.class));
        verify(mockCrudAppUserApi, never()).createAppUser(any(AppUser.class));
        verify(mockCrudAppUserApi, never()).findAppUserByNickname(anyString());
    }

    @Test
    void testCreateUserWhenIsNotFoundAfterCreation() {
        // @formatter:off
        when(mockCrudUserAuthApi.findUserAuthInfoByEmail(anyString())).thenReturn(Optional.empty(), Optional.empty());
        when(mockPasswordEncoder.encode(anyString())).thenReturn("password");
        when(mockCrudUserAuthApi.createUserAuthInfo(any(UserAuthInfo.class))).thenReturn(true);
        // @formatter:on

        assertThrows(UserIsNotFoundException.class, () -> userService.createUser(TEST_USER_DTO));
        verify(mockCrudUserAuthApi, times(2)).findUserAuthInfoByEmail(anyString());
        verify(mockPasswordEncoder, atMostOnce()).encode(anyString());
        verify(mockCrudUserAuthApi, atMostOnce()).createUserAuthInfo(any(UserAuthInfo.class));
        verify(mockCrudAppUserApi, never()).findAppUserByNickname(anyString());
        verify(mockCrudAppUserApi, never()).createAppUser(any(AppUser.class));
    }

    @Test
    void testCreateUserWhenIsNotFoundAppUser() {
        // @formatter:off
        when(mockCrudUserAuthApi.findUserAuthInfoByEmail(anyString())).thenReturn(Optional.empty(), Optional.of(TEST_USER_AUTH_INFO));
        when(mockPasswordEncoder.encode(anyString())).thenReturn("password");
        when(mockCrudUserAuthApi.createUserAuthInfo(any(UserAuthInfo.class))).thenReturn(true);
        when(mockCrudAppUserApi.createAppUser(any(AppUser.class))).thenReturn(false);
        // @formatter:on

        assertThrows(EntityCreationException.class, () -> userService.createUser(TEST_USER_DTO));
        verify(mockCrudUserAuthApi, times(2)).findUserAuthInfoByEmail(anyString());
        verify(mockPasswordEncoder, atMostOnce()).encode(anyString());
        verify(mockCrudUserAuthApi, atMostOnce()).createUserAuthInfo(any(UserAuthInfo.class));
        verify(mockCrudAppUserApi, atMostOnce()).createAppUser(any(AppUser.class));
        verify(mockCrudAppUserApi, never()).findAppUserByNickname(anyString());
    }

    @Test
    void testCreateUserWhenIsNotFoundAppUserByNickname() {
        // @formatter:off
        when(mockCrudUserAuthApi.findUserAuthInfoByEmail(anyString())).thenReturn(Optional.empty(), Optional.of(TEST_USER_AUTH_INFO));
        when(mockPasswordEncoder.encode(anyString())).thenReturn("password");
        when(mockCrudUserAuthApi.createUserAuthInfo(any(UserAuthInfo.class))).thenReturn(true);
        when(mockCrudAppUserApi.createAppUser(any(AppUser.class))).thenReturn(true);
        when(mockCrudAppUserApi.findAppUserByNickname(anyString())).thenReturn(Optional.empty());
        // @formatter:on

        assertThrows(UserIsNotFoundException.class, () -> userService.createUser(TEST_USER_DTO));
        verify(mockCrudUserAuthApi, times(2)).findUserAuthInfoByEmail(anyString());
        verify(mockPasswordEncoder, atMostOnce()).encode(anyString());
        verify(mockCrudUserAuthApi, atMostOnce()).createUserAuthInfo(any(UserAuthInfo.class));
        verify(mockCrudAppUserApi, atMostOnce()).createAppUser(any(AppUser.class));
        verify(mockCrudAppUserApi, atMostOnce()).findAppUserByNickname(anyString());
    }

    @Test
    void testUpdateUser() {
        // @formatter:off
        when(mockCrudUserAuthApi.findUserAuthInfoByEmail(anyString())).thenReturn(Optional.of(TEST_USER_AUTH_INFO));
        when(mockPasswordEncoder.encode(anyString())).thenReturn("password");
        when(mockCrudUserAuthApi.updateUserAuthInfo(any(UserAuthInfo.class))).thenReturn(true);
        when(mockCrudAppUserApi.updateAppUser(any(AppUser.class))).thenReturn(true);
        // @formatter:on

        userService.updateUser(TEST_USER_DTO);

        verify(mockCrudUserAuthApi, atMostOnce()).findUserAuthInfoByEmail(anyString());
        verify(mockPasswordEncoder, atMostOnce()).encode(anyString());
        verify(mockCrudUserAuthApi, atMostOnce()).updateUserAuthInfo(any(UserAuthInfo.class));
        verify(mockCrudAppUserApi, atMostOnce()).updateAppUser(any(AppUser.class));
    }

    @Test
    void testUpdateUserNotFound() {
        // @formatter:off
        when(mockCrudUserAuthApi.findUserAuthInfoByEmail(anyString())).thenReturn(Optional.empty());
        // @formatter:on

        assertThrows(UserIsNotFoundException.class, () -> userService.updateUser(TEST_USER_DTO));

        verify(mockCrudUserAuthApi, atMostOnce()).findUserAuthInfoByEmail(anyString());
    }

    @Test
    void testUpdateUserNotUpdated() {
        // @formatter:off
        when(mockCrudUserAuthApi.findUserAuthInfoByEmail(anyString())).thenReturn(Optional.of(TEST_USER_AUTH_INFO));
        when(mockPasswordEncoder.encode(anyString())).thenReturn("password");
        when(mockCrudUserAuthApi.updateUserAuthInfo(any(UserAuthInfo.class))).thenReturn(Boolean.FALSE);
        // @formatter:on

        assertThrows(EntityUpdateException.class, () -> userService.updateUser(TEST_USER_DTO));

        verify(mockCrudUserAuthApi, atMostOnce()).findUserAuthInfoByEmail(anyString());
        verify(mockPasswordEncoder, atMostOnce()).encode(anyString());
        verify(mockCrudUserAuthApi, atMostOnce()).updateUserAuthInfo(any(UserAuthInfo.class));
    }

    @Test
    void testUpdateAppUserNotUpdated() {
        // @formatter:off
        when(mockCrudUserAuthApi.findUserAuthInfoByEmail(anyString())).thenReturn(Optional.of(TEST_USER_AUTH_INFO));
        when(mockPasswordEncoder.encode(anyString())).thenReturn("password");
        when(mockCrudUserAuthApi.updateUserAuthInfo(any(UserAuthInfo.class))).thenReturn(true);
        when(mockCrudAppUserApi.updateAppUser(any(AppUser.class))).thenReturn(false);
        // @formatter:on

        assertThrows(EntityUpdateException.class, () -> userService.updateUser(TEST_USER_DTO));

        verify(mockCrudUserAuthApi, atMostOnce()).findUserAuthInfoByEmail(anyString());
        verify(mockPasswordEncoder, atMostOnce()).encode(anyString());
        verify(mockCrudUserAuthApi, atMostOnce()).updateUserAuthInfo(any(UserAuthInfo.class));
        verify(mockCrudAppUserApi, atMostOnce()).updateAppUser(any(AppUser.class));
    }

    @Test
    void testDeleteUser() {
        // @formatter:off
        when(mockCrudUserAuthApi.findUserAuthInfoById(anyLong())).thenReturn(Optional.of(TEST_USER_AUTH_INFO));
        when(mockCrudUserAuthApi.deleteUserAuthInfo(anyLong())).thenReturn(true);
        // @formatter:on

        userService.deleteUser(1_000L);

        verify(mockCrudUserAuthApi, atMostOnce()).findUserAuthInfoByEmail(anyString());
        verify(mockCrudUserAuthApi, atMostOnce()).deleteUserAuthInfo(anyLong());
    }

    @Test
    void testDeleteUserNotFound() {
        // @formatter:off
        when(mockCrudUserAuthApi.findUserAuthInfoById(anyLong())).thenReturn(Optional.empty());
        // @formatter:on

        assertThrows(UserIsNotFoundException.class, () -> userService.deleteUser(1_000L));

        verify(mockCrudUserAuthApi, atMostOnce()).findUserAuthInfoByEmail(anyString());
        verify(mockCrudUserAuthApi, never()).deleteUserAuthInfo(anyLong());
    }

    @Test
    void testDeleteUserNotDeleted() {
        // @formatter:off
        when(mockCrudUserAuthApi.findUserAuthInfoById(anyLong())).thenReturn(Optional.of(TEST_USER_AUTH_INFO));
        when(mockCrudUserAuthApi.deleteUserAuthInfo(anyLong())).thenReturn(false);
        // @formatter:on

        assertThrows(EntityDeletionException.class, () -> userService.deleteUser(1_000L));

        verify(mockCrudUserAuthApi, atMostOnce()).findUserAuthInfoByEmail(anyString());
        verify(mockCrudUserAuthApi, atMostOnce()).deleteUserAuthInfo(anyLong());
    }

    @Test
    void testFindUserByEmail() {
        // @formatter:off
        when(mockCrudUserAuthApi.findUserAuthInfoByEmail(anyString())).thenReturn(Optional.of(TEST_USER_AUTH_INFO));
        when(mockCrudAppUserApi.findAppUserById(anyLong())).thenReturn(Optional.of(TEST_APP_USER));
        // @formatter:on

        val res = userService.findUserByEmail("test@email.com");

        verify(mockCrudUserAuthApi, atMostOnce()).findUserAuthInfoByEmail(anyString());
        verify(mockCrudAppUserApi, atMostOnce()).findAppUserById(anyLong());

        assertEquals(TEST_USER_AUTH_INFO.getEmail(), res.getEmail());
        assertEquals(TEST_USER_AUTH_INFO.getUserId(), res.getUserId());
        assertEquals(TEST_APP_USER.getUserId(), res.getUserId());
        assertEquals(TEST_APP_USER.getNickname(), res.getNickname());
        assertTrue(StringUtils.isBlank(res.getPassword()));
    }

    @Test
    void testFindUserByEmailNotFoundUser() {
        // @formatter:off
        when(mockCrudUserAuthApi.findUserAuthInfoByEmail(anyString())).thenReturn(Optional.empty());
        // @formatter:on

        assertThrows(UserIsNotFoundException.class, () -> userService.findUserByEmail("test@email.com"));

        verify(mockCrudUserAuthApi, atMostOnce()).findUserAuthInfoByEmail(anyString());
    }

    @Test
    void testFindUserByEmailNotFoundAppUser() {
        // @formatter:off
        when(mockCrudUserAuthApi.findUserAuthInfoByEmail(anyString())).thenReturn(Optional.of(TEST_USER_AUTH_INFO));
        when(mockCrudAppUserApi.findAppUserById(anyLong())).thenReturn(Optional.empty());
        // @formatter:on

        assertThrows(UserIsNotFoundException.class, () -> userService.findUserByEmail("test@email.com"));

        verify(mockCrudUserAuthApi, atMostOnce()).findUserAuthInfoByEmail(anyString());
    }

    @Test
    void testFindUserByNickname() {
        // @formatter:off
        when(mockCrudAppUserApi.findAppUserByNickname(anyString())).thenReturn(Optional.of(TEST_APP_USER));
        when(mockCrudUserAuthApi.findUserAuthInfoById(anyLong())).thenReturn(Optional.of(TEST_USER_AUTH_INFO));
        // @formatter:on

        val res = userService.findUserByNickname("test");

        verify(mockCrudAppUserApi, atMostOnce()).findAppUserById(anyLong());
        verify(mockCrudUserAuthApi, atMostOnce()).findUserAuthInfoByEmail(anyString());

        assertEquals(TEST_USER_AUTH_INFO.getEmail(), res.getEmail());
        assertEquals(TEST_USER_AUTH_INFO.getUserId(), res.getUserId());
        assertEquals(TEST_APP_USER.getUserId(), res.getUserId());
        assertEquals(TEST_APP_USER.getNickname(), res.getNickname());
        assertTrue(StringUtils.isBlank(res.getPassword()));
    }

    @Test
    void testFindUserByNicknameNotFoundAppUser() {
        // @formatter:off
        when(mockCrudAppUserApi.findAppUserByNickname(anyString())).thenReturn(Optional.empty());
        // @formatter:on

        assertThrows(UserIsNotFoundException.class, () -> userService.findUserByNickname("test"));

        verify(mockCrudAppUserApi, atMostOnce()).findAppUserById(anyLong());
        verify(mockCrudUserAuthApi, never()).findUserAuthInfoByEmail(anyString());
    }

    @Test
    void testFindUserByNicknameNotUser() {
        // @formatter:off
        when(mockCrudAppUserApi.findAppUserByNickname(anyString())).thenReturn(Optional.of(TEST_APP_USER));
        when(mockCrudUserAuthApi.findUserAuthInfoById(anyLong())).thenReturn(Optional.empty());
        // @formatter:on

        assertThrows(UserIsNotFoundException.class, () -> userService.findUserByNickname("test"));

        verify(mockCrudAppUserApi, atMostOnce()).findAppUserById(anyLong());
        verify(mockCrudUserAuthApi, atMostOnce()).findUserAuthInfoByEmail(anyString());
    }

}
