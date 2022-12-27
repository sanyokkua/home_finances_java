package ua.home.finances.finances.services;

import ua.home.finances.finances.db.models.AppUser;
import ua.home.finances.finances.db.models.UserAuthInfo;

import java.util.Optional;

public interface UserService {

    void createUserAuthInfo(UserAuthInfo userAuthInfo);

    void updateUserAuthInfo(UserAuthInfo userAuthInfo);

    void deleteUserAuthInfo(long id);

    Optional<UserAuthInfo> findUserAuthInfoByEmail(String email);

    void createAppUser(AppUser appUserAuthInfo);

    void updateAppUser(AppUser appUserAuthInfo);

    void deleteAppUser(long id);

    Optional<AppUser> findAppUserByNickname(String nickname);
}
