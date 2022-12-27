package ua.home.finances.finances.db.repositories.api;

import ua.home.finances.finances.db.models.UserAuthInfo;

import java.util.Optional;

public interface CrudUserAuthApi {

    boolean createUserAuthInfo(UserAuthInfo userAuthInfo);

    boolean updateUserAuthInfo(UserAuthInfo userAuthInfo);

    boolean deleteUserAuthInfo(long id);

    Optional<UserAuthInfo> findUserAuthInfoByEmail(String email);

    Optional<UserAuthInfo> findUserAuthInfoById(long id);
}
