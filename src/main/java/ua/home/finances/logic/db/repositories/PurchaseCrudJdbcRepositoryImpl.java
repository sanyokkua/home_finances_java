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
import ua.home.finances.logic.db.models.Purchase;
import ua.home.finances.logic.db.repositories.api.PurchaseCrudJdbcRepository;
import ua.home.finances.logic.db.repositories.mappers.PurchaseRowMapper;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Log4j2
@Repository
@RequiredArgsConstructor
public class PurchaseCrudJdbcRepositoryImpl implements PurchaseCrudJdbcRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Purchase create(Purchase entity) {
        ValidationUtils.validatePurchase(entity);
        // @formatter:off
        val params = Map.of("listId",   entity.getPurchaseListId(),
                            "name",     entity.getName(),
                            "coins",    entity.getCoins(),
                            "currency", entity.getCurrency().name(),
                            "date",     entity.getDate(),
                            "category", entity.getCategory());
        // @formatter:on

        val sql = """
                INSERT INTO "app_finance"."purchase" ("p_l_id", "p_name", "p_coins", "p_currency", "p_date", "p_category") 
                VALUES (:listId, :name, :coins, :currency, :date, :category) 
                ON CONFLICT DO NOTHING
                """;

        val keyHolder = new GeneratedKeyHolder();
        val updatedRows = namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(params), keyHolder,
                                                            new String[]{"p_id"});
        val key = keyHolder.getKey();

        if (updatedRows <= 0 || Objects.isNull(key)) {
            throw new EntityCreationException("Number of updated rows is not correct or ID is null");
        }

        val createdEntity = findById(key.longValue());
        return createdEntity.orElseThrow(() -> new EntityCreationException("Purchase is not found after creation"));
    }

    @Override
    public Purchase update(Purchase entity) {
        ValidationUtils.validatePurchase(entity);
        // @formatter:off
        val params = Map.of("id", entity.getPurchaseId(),
                            "listId",   entity.getPurchaseListId(),
                            "name",     entity.getName(),
                            "coins",    entity.getCoins(),
                            "currency", entity.getCurrency().name(),
                            "date",     entity.getDate(),
                            "category", entity.getCategory());
        // @formatter:on

        val sql = """
                UPDATE "app_finance"."purchase"
                SET  "p_name" = :name, "p_coins" = :coins, "p_currency" = :currency, "p_date" = :date, "p_category" = :category
                WHERE "p_id" = :id AND "p_l_id" = :listId
                """;
        val updatedRows = namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(params));

        if (updatedRows <= 0) {
            throw new EntityUpdateException("Number of updated rows is not correct");
        }

        val updatedEntity = findById(entity.getPurchaseId());
        return updatedEntity.orElseThrow(() -> new EntityUpdateException("Purchase is not found after update"));
    }

    @Override
    public boolean delete(Long entityId) {
        ValidationUtils.validateParamCommon(() -> Objects.isNull(entityId) || entityId < 0, "Id is not valid");

        val params = Map.of("id", entityId);
        val sql = """
                DELETE FROM "app_finance"."purchase" WHERE "p_id" = :id
                """;
        val updatedRows = namedParameterJdbcTemplate.update(sql, params);

        return updatedRows > 0;
    }

    @Override
    public Optional<Purchase> findById(Long entityId) {
        ValidationUtils.validateParamCommon(() -> Objects.isNull(entityId) || entityId < 0, "Id is not valid");

        val params = Map.of("id", entityId);
        val sql = """
                SELECT * FROM "app_finance"."purchase" WHERE "p_id" = :id
                """;
        var queryResult = namedParameterJdbcTemplate.query(sql, params, new PurchaseRowMapper());

        return Optional.ofNullable(queryResult).stream().flatMap(List::stream).findAny();
    }

    @Override
    public List<Purchase> findAll(long purchaseListId) {
        ValidationUtils.validateParamCommon(() -> purchaseListId < 0, "Id is not valid");

        val params = Map.of("listId", purchaseListId);
        val sql = """
                SELECT * FROM "app_finance"."purchase" WHERE "p_l_id" = :listId
                """;
        var queryResult = namedParameterJdbcTemplate.query(sql, params, new PurchaseRowMapper());

        return Optional.ofNullable(queryResult).orElse(List.of());
    }

    @Override
    public List<String> findAllNames(long purchaseListId) {
        ValidationUtils.validateParamCommon(() -> purchaseListId < 0, "Id is not valid");

        return findAll(purchaseListId).stream().map(Purchase::getName).distinct().toList();
    }
}
