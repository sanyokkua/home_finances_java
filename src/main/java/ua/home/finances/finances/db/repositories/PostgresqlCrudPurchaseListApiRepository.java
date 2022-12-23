package ua.home.finances.finances.db.repositories;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.home.finances.finances.common.ValidationUtils;
import ua.home.finances.finances.db.models.PurchaseList;
import ua.home.finances.finances.db.repositories.api.CrudPurchaseListApi;
import ua.home.finances.finances.db.repositories.mappers.PurchaseListRowMapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Log4j2
@Repository
@RequiredArgsConstructor
public class PostgresqlCrudPurchaseListApiRepository implements CrudPurchaseListApi {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public boolean createPurchaseList(PurchaseList purchaseList) {
        ValidationUtils.validatePurchaseList(purchaseList);

        val params = Map.of("userId", purchaseList.getListUserId(), "name", purchaseList.getName());
        val sql = """
                INSERT INTO app_finance.list (l_u_id, l_name)
                VALUES (:userId, :name)
                ON CONFLICT DO NOTHING
                """;
        val updatedRows = namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(params));
        return updatedRows > 0;
    }

    @Override
    public boolean updatePurchaseList(PurchaseList purchaseList) {
        ValidationUtils.validatePurchaseList(purchaseList);

        val params = Map.of("userId", purchaseList.getListUserId(), "name", purchaseList.getName(), "id",
                            purchaseList.getListId());
        val sql = """
                UPDATE app_finance.list
                SET  l_name = :name
                WHERE l_id = :id and l_u_id = :userId
                """;
        val updatedRows = namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(params));
        return updatedRows > 0;
    }

    @Override
    public boolean deletePurchaseList(long userId, long listId) {
        ValidationUtils.validateParamCommon(() -> userId < 0, "USER_ID should be >=0");
        ValidationUtils.validateParamCommon(() -> listId < 0, "LIST_ID should be >=0");

        val params = Map.of("userId", userId, "id", listId);
        val sql = "DELETE FROM app_finance.list WHERE l_id = :id and l_u_id = :userId";
        val updatedRows = namedParameterJdbcTemplate.update(sql, params);
        return updatedRows > 0;
    }

    @Override
    public Optional<PurchaseList> findById(long userId, long listId) {
        ValidationUtils.validateParamCommon(() -> userId < 0, "USER_ID should be >=0");
        ValidationUtils.validateParamCommon(() -> listId < 0, "LIST_ID should be >=0");

        val params = Map.of("userId", userId, "id", listId);
        val sql = "SELECT * FROM app_finance.list WHERE l_id = :id and l_u_id = :userId";

        var queryResult = namedParameterJdbcTemplate.query(sql, params, new PurchaseListRowMapper());
        return Optional.ofNullable(queryResult).stream().flatMap(List::stream).findAny();
    }

    @Override
    public Optional<PurchaseList> findByName(long userId, String name) {
        ValidationUtils.validateParamCommon(() -> userId < 0, "USER_ID should be >=0");
        ValidationUtils.validateParamCommon(() -> StringUtils.isBlank(name), "NAME should be >=0");

        val params = Map.of("userId", userId, "name", name);
        val sql = "SELECT * FROM app_finance.list WHERE l_u_id = :userId and l_name = :name";

        var queryResult = namedParameterJdbcTemplate.query(sql, params, new PurchaseListRowMapper());
        return Optional.ofNullable(queryResult).stream().flatMap(List::stream).findAny();
    }

    @Override
    public List<PurchaseList> findAll(long userId) {
        ValidationUtils.validateParamCommon(() -> userId < 0, "USER_ID should be >=0");

        val params = Map.of("userId", userId);
        val sql = "SELECT * FROM app_finance.list WHERE l_u_id = :userId";

        var queryResult = namedParameterJdbcTemplate.query(sql, params, new PurchaseListRowMapper());
        return Optional.ofNullable(queryResult).stream().flatMap(List::stream).toList();
    }
}
