package ua.home.finances.finances.db.repositories;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.home.finances.finances.common.ValidationUtils;
import ua.home.finances.finances.db.models.UserAuthInfo;
import ua.home.finances.finances.db.repositories.api.CrudUserAuthApi;
import ua.home.finances.finances.db.repositories.mappers.UserAuthInfoRowMapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Log4j2
@Repository
@RequiredArgsConstructor
public class PostgresqlCrudUserAuthApiRepository implements CrudUserAuthApi {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public boolean createUserAuthInfo(UserAuthInfo userAuthInfo) {
        ValidationUtils.validateUserAuth(userAuthInfo);

        // @formatter:off
        val params = Map.of("email",    userAuthInfo.getEmail(),
                            "password", userAuthInfo.getPassword());
        // @formatter:on
        val sql = """
                INSERT INTO "app_finance"."user_auth" ("u_email", "u_password")
                VALUES (:email, :password)
                ON CONFLICT DO NOTHING
                """;
        val updatedRows = namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(params));
        return updatedRows > 0;
    }

    @Override
    public boolean updateUserAuthInfo(UserAuthInfo userAuthInfo) {
        ValidationUtils.validateUserAuth(userAuthInfo);

        // @formatter:off
        val params = Map.of("email",    userAuthInfo.getEmail(),
                            "password", userAuthInfo.getPassword(),
                            "id",       userAuthInfo.getUserId());
        // @formatter:on
        val sql = """
                UPDATE "app_finance"."user_auth"
                SET  "u_email" = :email, "u_password" = :password
                WHERE "u_id" = :id
                """;
        val updatedRows = namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(params));
        return updatedRows > 0;
    }

    @Override
    public boolean deleteUserAuthInfo(long id) {
        ValidationUtils.validateParamCommon(() -> id < 0, "UserAuthInfo ID should be >=0");

        val params = Map.of("id", id);
        val sql = """
                DELETE FROM "app_finance"."user_auth" WHERE "u_id" = :id
                """;
        val updatedRows = namedParameterJdbcTemplate.update(sql, params);
        return updatedRows > 0;
    }

    @Override
    public Optional<UserAuthInfo> findUserAuthInfoByEmail(String email) {
        ValidationUtils.validateEmail(email);

        val params = Map.of("email", email);
        val sql = """
                SELECT * FROM "app_finance"."user_auth" WHERE "u_email" = :email
                """;

        var queryResult = namedParameterJdbcTemplate.query(sql, params, new UserAuthInfoRowMapper());
        return Optional.ofNullable(queryResult).stream().flatMap(List::stream).findAny();
    }

    @Override
    public Optional<UserAuthInfo> findUserAuthInfoById(long id) {
        ValidationUtils.validateParamCommon(() -> id < 0, "Id is not valid");

        val params = Map.of("id", id);
        val sql = """
                SELECT * FROM "app_finance"."user_auth" WHERE "u_id" = :id
                """;

        var queryResult = namedParameterJdbcTemplate.query(sql, params, new UserAuthInfoRowMapper());
        return Optional.ofNullable(queryResult).stream().flatMap(List::stream).findAny();
    }
}
