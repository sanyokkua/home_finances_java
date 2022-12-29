package ua.home.finances.logic.db.repositories.api;

import ua.home.finances.logic.db.models.ApplicationUser;

import java.util.List;
import java.util.Optional;

public interface ApplicationUserCrudJdbcRepository extends BasicCrudJdbcRepository<ApplicationUser, Long> {
    Optional<ApplicationUser> findByEmail(String email);

    List<ApplicationUser> findAll();
}
