package ua.home.finances.finances.db.repositories;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.home.finances.finances.common.ValidationUtils;
import ua.home.finances.finances.db.models.AppUser;
import ua.home.finances.finances.db.repositories.api.CrudAppUserApi;
import ua.home.finances.finances.db.repositories.mappers.AppUserRowMapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Log4j2
@Repository
@RequiredArgsConstructor
public class PostgresqlCrudAppUserApiRepository implements CrudAppUserApi {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public boolean createAppUser(AppUser appUserAuthInfo) {
        ValidationUtils.validateAppUser(appUserAuthInfo);

        // @formatter:off
        val params = Map.of("userId",   appUserAuthInfo.getUserId(),
                            "nickname", appUserAuthInfo.getNickname());
        // @formatter:on
        val sql = """
                INSERT INTO "app_finance"."user" ("u_id", "u_nickname")
                VALUES (:userId, :nickname)
                ON CONFLICT DO NOTHING
                """;
        val updatedRows = namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(params));
        return updatedRows > 0;
    }

    @Override
    public boolean updateAppUser(AppUser appUserAuthInfo) {
        ValidationUtils.validateAppUser(appUserAuthInfo);

        // @formatter:off
        val params = Map.of("userId",   appUserAuthInfo.getUserId(),
                            "nickname", appUserAuthInfo.getNickname());
        // @formatter:on
        val sql = """
                UPDATE "app_finance"."user"
                SET  "u_nickname" = :nickname
                WHERE "u_id" = :userId
                """;
        val updatedRows = namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(params));
        return updatedRows > 0;
    }

    @Override
    public boolean deleteAppUser(long id) {
        ValidationUtils.validateParamCommon(() -> id < 0, "AppUser ID should be >=0");

        val params = Map.of("id", id);
        val sql = """
                DELETE FROM "app_finance"."user" WHERE "u_id" = :id
                """;
        val updatedRows = namedParameterJdbcTemplate.update(sql, params);
        return updatedRows > 0;
    }

    @Override
    public Optional<AppUser> findAppUserByNickname(String nickname) {
        ValidationUtils.validateParamCommon(() -> StringUtils.isBlank(nickname), "Nickname is blank");

        val params = Map.of("nickname", nickname);
        val sql = """
                SELECT * FROM "app_finance"."user" WHERE "u_nickname" = :nickname
                """;

        var queryResult = namedParameterJdbcTemplate.query(sql, params, new AppUserRowMapper());
        return Optional.ofNullable(queryResult).stream().flatMap(List::stream).findAny();
    }

    @Override
    public Optional<AppUser> findAppUserById(long id) {
        ValidationUtils.validateParamCommon(() -> id < 0, "Id is not valid");

        val params = Map.of("id", id);
        val sql = """
                SELECT * FROM "app_finance"."user" WHERE "u_id" = :id
                """;

        var queryResult = namedParameterJdbcTemplate.query(sql, params, new AppUserRowMapper());
        return Optional.ofNullable(queryResult).stream().flatMap(List::stream).findAny();
    }
}
