package ua.home.finances.finances.db.repositories;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.home.finances.finances.common.ValidationUtils;
import ua.home.finances.finances.db.models.Purchase;
import ua.home.finances.finances.db.repositories.api.CrudPurchaseApi;
import ua.home.finances.finances.db.repositories.mappers.PurchaseRowMapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Repository
@RequiredArgsConstructor
public class PostgresqlCrudPurchaseApiRepository implements CrudPurchaseApi {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public boolean createPurchase(Purchase purchase) {
        ValidationUtils.validatePurchase(purchase);

        val params = Map.of("listId", purchase.getPurchaseListId(), "name", purchase.getName(), "coins",
                            purchase.getCoins(), "currency", purchase.getCurrency().name(), "date", purchase.getDate());
        val sql = """
                INSERT INTO app_finance.purchase (p_l_id, p_name, p_coins, p_currency, p_date) 
                VALUES (:listId, :name, :coins, :currency, :date) 
                ON CONFLICT DO NOTHING
                """;
        val updatedRows = jdbcTemplate.update(sql, new MapSqlParameterSource(params));
        return updatedRows > 0;
    }

    @Override
    public boolean updatePurchase(Purchase purchase) {
        ValidationUtils.validatePurchase(purchase);

        val params = Map.of("id", purchase.getPurchaseId(), "listId", purchase.getPurchaseListId(), "name",
                            purchase.getName(), "coins", purchase.getCoins(), "currency", purchase.getCurrency().name(),
                            "date", purchase.getDate());
        val sql = """
                UPDATE app_finance.purchase
                SET  p_name = :name, p_coins = :coins, p_currency = :currency, p_date = :date
                WHERE p_id = :id and p_l_id = :listId
                """;
        val updatedRows = jdbcTemplate.update(sql, new MapSqlParameterSource(params));
        return updatedRows > 0;
    }

    @Override
    public boolean deletePurchase(long purchaseListId, long purchaseId) {
        ValidationUtils.validateParamCommon(() -> purchaseListId < 0, "PurchaseListId should be >=0");
        ValidationUtils.validateParamCommon(() -> purchaseId < 0, "PurchaseId should be >=0");

        val params = Map.of("id", purchaseId, "listId", purchaseListId);
        val sql = "DELETE FROM app_finance.purchase WHERE p_id = :id and p_l_id = :listId";
        val updatedRows = jdbcTemplate.update(sql, params);
        return updatedRows > 0;
    }

    @Override
    public Optional<Purchase> getPurchase(long purchaseListId, long purchaseId) {
        ValidationUtils.validateParamCommon(() -> purchaseListId < 0, "PurchaseListId should be >=0");
        ValidationUtils.validateParamCommon(() -> purchaseId < 0, "PurchaseId should be >=0");

        val params = Map.of("id", purchaseId, "listId", purchaseListId);
        val sql = "SELECT * FROM app_finance.purchase WHERE p_id = :id and p_l_id = :listId";

        var queryResult = jdbcTemplate.query(sql, params, new PurchaseRowMapper());
        return Optional.ofNullable(queryResult).stream().flatMap(List::stream).findAny();
    }

    @Override
    public List<Purchase> getAll(long purchaseListId) {
        ValidationUtils.validateParamCommon(() -> purchaseListId < 0, "PurchaseListId should be >=0");

        val sql = "SELECT * FROM app_finance.purchase WHERE p_l_id = :listId";

        var queryResult = jdbcTemplate.query(sql, Map.of("listId", purchaseListId), new PurchaseRowMapper());
        return Optional.ofNullable(queryResult).stream().flatMap(List::stream).toList();
    }

    @Override
    public List<String> getAllNames(long purchaseListId) {
        ValidationUtils.validateParamCommon(() -> purchaseListId < 0, "PurchaseListId should be >=0");

        val res = this.getAll(purchaseListId);
        return res.stream().map(Purchase::getName).distinct().collect(Collectors.toList());
    }
}
