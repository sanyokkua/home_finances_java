package ua.home.finances.logic.db.repositories;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ua.home.finances.logic.common.ValidationUtils;
import ua.home.finances.logic.common.exceptions.EntityCreationException;
import ua.home.finances.logic.common.exceptions.EntityUpdateException;
import ua.home.finances.logic.db.models.ApplicationUser;
import ua.home.finances.logic.db.repositories.api.ApplicationUserCrudJdbcRepository;
import ua.home.finances.logic.db.repositories.mappers.ApplicationUserRowMapper;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Log4j2
@Repository
@RequiredArgsConstructor
public class ApplicationUserCrudJdbcRepositoryImpl implements ApplicationUserCrudJdbcRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public ApplicationUser create(ApplicationUser entity) {
        ValidationUtils.validateApplicationUser(entity);

        // @formatter:off
        val params = Map.of("email",    entity.getEmail(),
                            "password", entity.getPassword(),
                            "nickname", entity.getNickname(),
                            "role",     entity.getRole().name(),
                            "active",   entity.isActive()
        );
        // @formatter:on

        val sql = """
                INSERT INTO "app_finance"."application_user" ("u_email", "u_password", "u_nickname", "u_role", "u_active")
                VALUES (:email, :password, :nickname, :role, :active)
                ON CONFLICT DO NOTHING
                """;

        val keyHolder = new GeneratedKeyHolder();
        val updatedRows = namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(params), keyHolder,
                                                            new String[]{"u_id"});
        val key = keyHolder.getKey();

        if (updatedRows <= 0 || Objects.isNull(key)) {
            throw new EntityCreationException("Number of updated rows is not correct or ID is null");
        }

        val createdEntity = findById(key.longValue());
        return createdEntity.orElseThrow(() -> new EntityCreationException("User is not found after creation"));
    }

    @Override
    public ApplicationUser update(ApplicationUser entity) {
        ValidationUtils.validateApplicationUser(entity);

        // @formatter:off
        val params = Map.of("userId",   entity.getUserId(),
                            "nickname", entity.getNickname(),
                            "role",     entity.getRole().name(),
                            "active",   entity.isActive()
        );
        // @formatter:on

        val sql = """
                UPDATE "app_finance"."application_user"
                SET  "u_nickname" = :nickname, "u_role" = :role, "u_active" = :active
                WHERE "u_id" = :userId
                """;

        val updatedRows = namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(params));

        if (updatedRows <= 0) {
            throw new EntityUpdateException("Number of updated rows is not correct");
        }

        val updatedUser = findById(entity.getUserId());
        return updatedUser.orElseThrow(() -> new EntityUpdateException("User is not found after update"));
    }

    @Override
    public boolean delete(Long entityId) {
        ValidationUtils.validateParamCommon(() -> Objects.isNull(entityId) || entityId < 0, "Id is not valid");

        val params = Map.of("id", entityId);
        val sql = """
                DELETE FROM "app_finance"."application_user" WHERE "u_id" = :id
                """;
        val updatedRows = namedParameterJdbcTemplate.update(sql, params);

        return updatedRows > 0;
    }

    @Override
    public Optional<ApplicationUser> findById(Long entityId) {
        ValidationUtils.validateParamCommon(() -> Objects.isNull(entityId) || entityId < 0, "Id is not valid");

        val params = Map.of("id", entityId);
        val sql = """
                SELECT * FROM "app_finance"."application_user" WHERE "u_id" = :id
                """;

        var queryResult = namedParameterJdbcTemplate.query(sql, params, new ApplicationUserRowMapper());

        return Optional.ofNullable(queryResult).stream().flatMap(List::stream).findAny();
    }

    @Override
    public Optional<ApplicationUser> findByEmail(String email) {
        ValidationUtils.validateEmail(email);

        val params = Map.of("email", email);
        val sql = """
                SELECT * FROM "app_finance"."application_user" WHERE "u_email" = :email
                """;

        var queryResult = namedParameterJdbcTemplate.query(sql, params, new ApplicationUserRowMapper());

        return Optional.ofNullable(queryResult).stream().flatMap(List::stream).findAny();
    }

    @Override
    public List<ApplicationUser> findAll() {
        val sql = """
                SELECT * FROM "app_finance"."application_user"
                """;

        var queryResult = namedParameterJdbcTemplate.query(sql, Map.of(), new ApplicationUserRowMapper());

        return Optional.ofNullable(queryResult).orElse(List.of());
    }
}
