package ua.home.finances.logic.services;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.StringUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ua.home.finances.logic.common.exceptions.UserIsNotFoundException;
import ua.home.finances.logic.db.models.ApplicationUser;
import ua.home.finances.logic.db.repositories.api.ApplicationUserCrudJdbcRepository;
import ua.home.finances.logic.services.api.Result;
import ua.home.finances.logic.services.dtos.UserDto;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserCrudServiceImplTests {
    @Mock
    ApplicationUserCrudJdbcRepository mockUserRepository;
    @Mock
    PasswordEncoder mockPasswordEncoder;
    @InjectMocks
    UserCrudServiceImpl userService;
    UserDto TEST_USER_DTO;
    ApplicationUser TEST_APPLICATION_USER;

    @BeforeEach
    void beforeEach() {
        TEST_USER_DTO = UserDto.builder()
                .userId(1000L)
                .email("test@email.com")
                .nickname("nick")
                .password("password")
                .build();
        TEST_APPLICATION_USER = UserDto.fromDto(TEST_USER_DTO);
    }

    @Test
    void testCreateUser() {
        when(mockPasswordEncoder.encode(anyString())).thenReturn("password");
        when(mockUserRepository.create(any(ApplicationUser.class))).thenReturn(TEST_APPLICATION_USER);

        val res = userService.create(TEST_USER_DTO);

        assertEquals("", res.getPassword());
        verify(mockPasswordEncoder, atMostOnce()).encode(anyString());
        verify(mockUserRepository, atMostOnce()).create(any(ApplicationUser.class));
    }

    @Test
    void testUpdateUser() {
        when(mockUserRepository.update(any(ApplicationUser.class))).thenReturn(TEST_APPLICATION_USER);

        val res = userService.update(TEST_USER_DTO);

        assertEquals("", res.getPassword());
        verify(mockUserRepository, atMostOnce()).update(any(ApplicationUser.class));
    }

    @Test
    void testDeleteUser() {
        when(mockUserRepository.delete(anyLong())).thenReturn(true, false);

        val res1 = userService.delete(1_000L);
        val res2 = userService.delete(1_000L);

        assertEquals(Result.SUCCESS, res1);
        assertEquals(Result.ERROR, res2);

        verify(mockUserRepository, times(2)).delete(anyLong());
    }

    @Test
    void testFindById() {
        when(mockUserRepository.findById(anyLong())).thenReturn(Optional.of(TEST_APPLICATION_USER));

        val res = userService.findById(1000L);

        verify(mockUserRepository, atMostOnce()).findById(anyLong());

        assertEquals(TEST_APPLICATION_USER.getEmail(), res.getEmail());
        assertEquals(TEST_APPLICATION_USER.getUserId(), res.getUserId());
        assertEquals(TEST_APPLICATION_USER.getUserId(), res.getUserId());
        assertEquals(TEST_APPLICATION_USER.getNickname(), res.getNickname());
        assertTrue(StringUtils.isBlank(res.getPassword()));
    }

    @Test
    void testFindByIdNotFound() {
        when(mockUserRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserIsNotFoundException.class, () -> userService.findById(1000L));

        verify(mockUserRepository, atMostOnce()).findById(anyLong());
    }

    @Test
    void testFindUserByEmail() {
        when(mockUserRepository.findByEmail(anyString())).thenReturn(Optional.of(TEST_APPLICATION_USER));

        val res = userService.findUserByEmail("test@email.com");

        verify(mockUserRepository, atMostOnce()).findByEmail(anyString());

        assertEquals(TEST_APPLICATION_USER.getEmail(), res.getEmail());
        assertEquals(TEST_APPLICATION_USER.getUserId(), res.getUserId());
        assertEquals(TEST_APPLICATION_USER.getUserId(), res.getUserId());
        assertEquals(TEST_APPLICATION_USER.getNickname(), res.getNickname());
        assertTrue(StringUtils.isBlank(res.getPassword()));
    }

    @Test
    void testFindUserByEmailNotFound() {
        when(mockUserRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(UserIsNotFoundException.class, () -> userService.findUserByEmail("user@email.com"));

        verify(mockUserRepository, atMostOnce()).findByEmail(anyString());
    }

    @Test
    void testFindAll() {
        when(mockUserRepository.findAll()).thenReturn(List.of(TEST_APPLICATION_USER));

        val res = userService.findAll();

        verify(mockUserRepository, atMostOnce()).findAll();
        assertFalse(res.isEmpty());
    }
}
