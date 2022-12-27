package ua.home.finances.finances.services;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.home.finances.finances.common.exceptions.*;
import ua.home.finances.finances.db.models.AppUser;
import ua.home.finances.finances.db.models.UserAuthInfo;
import ua.home.finances.finances.db.repositories.api.CrudAppUserApi;
import ua.home.finances.finances.db.repositories.api.CrudUserAuthApi;
import ua.home.finances.finances.services.api.UserService;
import ua.home.finances.finances.services.dtos.UserDto;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final CrudUserAuthApi crudUserAuthApi;
    private final CrudAppUserApi crudAppUserApi;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createUser(UserDto userDto) {
        val foundExisting = crudUserAuthApi.findUserAuthInfoByEmail(userDto.getEmail());
        if (foundExisting.isPresent()) {
            throw new UserWithEmailAlreadyExistsException(
                    "User with provided email already exists. Email" + userDto.getEmail());
        }

        val userAuthInfo = UserAuthInfo.builder()
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .build();
        val isCreated = crudUserAuthApi.createUserAuthInfo(userAuthInfo);

        if (!isCreated) {
            throw new EntityCreationException("User was not created. Email: " + userDto.getEmail());
        }

        val createdUser = crudUserAuthApi.findUserAuthInfoByEmail(userDto.getEmail());

        if (createdUser.isEmpty()) {
            throw new UserIsNotFoundException("User was created but not found by email. Email: " + userDto.getEmail());
        }

        val userId = createdUser.get().getUserId();
        val nickname = StringUtils.isNotBlank(userDto.getNickname()) ? userDto.getNickname() : userDto.getEmail();
        val appUser = AppUser.builder().userId(userId).nickname(nickname).build();

        val isCreatedAppUser = crudAppUserApi.createAppUser(appUser);

        if (!isCreatedAppUser) {
            throw new EntityCreationException("AppUser is not created for user with email: " + userDto.getEmail());
        }
        if (crudAppUserApi.findAppUserByNickname(nickname).isEmpty()) {
            throw new UserIsNotFoundException("AppUser is created but not found by user nickname: " + nickname);
        }
    }

    @Override
    public void updateUser(UserDto userDto) {
        val userInfoToUpdate = crudUserAuthApi.findUserAuthInfoByEmail(userDto.getEmail());
        if (userInfoToUpdate.isEmpty()) {
            throw new UserIsNotFoundException("User not found for update. Email: " + userDto.getEmail());
        }
        val userAuthInfo = UserAuthInfo.builder()
                .userId(userInfoToUpdate.get().getUserId())
                .email(userInfoToUpdate.get().getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .build();
        val isUpdated = crudUserAuthApi.updateUserAuthInfo(userAuthInfo);

        if (!isUpdated) {
            throw new EntityUpdateException("User was not updated. Email: " + userDto.getEmail());
        }

        val userId = userInfoToUpdate.get().getUserId();
        val nickname = StringUtils.isNotBlank(userDto.getNickname()) ? userDto.getNickname() : userDto.getEmail();
        val appUser = AppUser.builder().userId(userId).nickname(nickname).build();

        val isAppUserUpdated = crudAppUserApi.updateAppUser(appUser);

        if (!isAppUserUpdated) {
            throw new EntityUpdateException("AppUser is not updated for user with email: " + userDto.getEmail());
        }
    }

    @Override
    public void deleteUser(long id) {
        val userFound = crudUserAuthApi.findUserAuthInfoById(id);
        if (userFound.isEmpty()) {
            throw new UserIsNotFoundException("UserAuthInfo is not found and will not be deleted.");
        }

        val isDeleted = crudUserAuthApi.deleteUserAuthInfo(id);
        if (!isDeleted) {
            throw new EntityDeletionException("UserAuthInfo is not deleted");
        }
    }

    @Override
    public UserDto findUserByEmail(String email) {
        val foundUser = crudUserAuthApi.findUserAuthInfoByEmail(email);
        if (foundUser.isEmpty()) {
            throw new UserIsNotFoundException("UserAuthInfo not found. Email: " + email);
        }

        val foundAppUser = crudAppUserApi.findAppUserById(foundUser.get().getUserId());
        if (foundAppUser.isEmpty()) {
            throw new UserIsNotFoundException("AppUser not found.");
        }

        return UserDto.builder()
                .userId(foundUser.get().getUserId())
                .email(foundUser.get().getEmail())
                .nickname(foundAppUser.get().getNickname())
                .password("") // Do not return password
                .build();
    }

    @Override
    public UserDto findUserByNickname(String nickname) {
        val foundAppUser = crudAppUserApi.findAppUserByNickname(nickname);
        if (foundAppUser.isEmpty()) {
            throw new UserIsNotFoundException("AppUser not found.");
        }

        val foundUser = crudUserAuthApi.findUserAuthInfoById(foundAppUser.get().getUserId());
        if (foundUser.isEmpty()) {
            throw new UserIsNotFoundException("UserAuthInfo not found.");
        }

        return UserDto.builder()
                .userId(foundUser.get().getUserId())
                .email(foundUser.get().getEmail())
                .nickname(foundAppUser.get().getNickname())
                .password("") // Do not return password
                .build();
    }
}
