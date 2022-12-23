package ua.home.finances.finances.db.repositories.api;

import ua.home.finances.finances.db.models.AppUser;

import java.util.Optional;

public interface CrudAppUserApi {

    boolean createAppUser(AppUser appUserAuthInfo);

    boolean updateAppUser(AppUser appUserAuthInfo);

    boolean deleteAppUser(long id);

    Optional<AppUser> findAppUserByNickname(String nickname);
}
