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
import ua.home.finances.logic.db.models.PurchaseList;
import ua.home.finances.logic.db.repositories.api.PurchaseListCrudJdbcRepository;
import ua.home.finances.logic.db.repositories.mappers.PurchaseListRowMapper;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Log4j2
@Repository
@RequiredArgsConstructor
public class PurchaseListCrudJdbcRepositoryImpl implements PurchaseListCrudJdbcRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public PurchaseList create(PurchaseList entity) {
        ValidationUtils.validatePurchaseList(entity);

        // @formatter:off
        val params = Map.of("userId",   entity.getListUserId(),
                            "name",     entity.getName());
        // @formatter:on

        val sql = """
                INSERT INTO "app_finance"."purchase_list" ("l_u_id", "l_name")
                VALUES (:userId, :name)
                ON CONFLICT DO NOTHING
                """;

        val keyHolder = new GeneratedKeyHolder();
        val updatedRows = namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(params), keyHolder,
                                                            new String[]{"l_id"});
        val key = keyHolder.getKey();

        if (updatedRows <= 0 || Objects.isNull(key)) {
            throw new EntityCreationException("Number of updated rows is not correct or ID is null");
        }

        val createdEntity = findById(key.longValue());
        return createdEntity.orElseThrow(() -> new EntityCreationException("PurchaseList is not found after creation"));
    }

    @Override
    public PurchaseList update(PurchaseList entity) {
        ValidationUtils.validatePurchaseList(entity);
        // @formatter:off
        val params = Map.of("name",     entity.getName(),
                            "id",       entity.getListId());
        // @formatter:on
        val sql = """
                UPDATE "app_finance"."purchase_list"
                SET  "l_name" = :name
                WHERE "l_id" = :id
                """;

        val updatedRows = namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(params));

        if (updatedRows <= 0) {
            throw new EntityUpdateException("Number of updated rows is not correct");
        }

        val updatedEntity = findById(entity.getListId());
        return updatedEntity.orElseThrow(() -> new EntityUpdateException("PurchaseList is not found after update"));
    }

    @Override
    public boolean delete(Long entityId) {
        ValidationUtils.validateParamCommon(() -> Objects.isNull(entityId) || entityId < 0, "Id is not valid");

        val params = Map.of("id", entityId);
        val sql = """
                DELETE FROM "app_finance"."purchase_list" WHERE "l_id" = :id
                """;
        val updatedRows = namedParameterJdbcTemplate.update(sql, params);

        return updatedRows > 0;
    }

    @Override
    public Optional<PurchaseList> findById(Long entityId) {
        ValidationUtils.validateParamCommon(() -> Objects.isNull(entityId) || entityId < 0, "Id is not valid");

        val params = Map.of("id", entityId);
        val sql = """
                SELECT * FROM "app_finance"."purchase_list" WHERE "l_id" = :id
                """;

        var queryResult = namedParameterJdbcTemplate.query(sql, params, new PurchaseListRowMapper());
        return Optional.ofNullable(queryResult).stream().flatMap(List::stream).findAny();
    }

    @Override
    public List<PurchaseList> findAll(long userId) {
        ValidationUtils.validateParamCommon(() -> userId < 0, "Id is not valid");

        val params = Map.of("userId", userId);
        val sql = """
                SELECT * FROM "app_finance"."purchase_list" WHERE "l_u_id" = :userId
                """;

        var queryResult = namedParameterJdbcTemplate.query(sql, params, new PurchaseListRowMapper());
        return Optional.ofNullable(queryResult).orElse(List.of());
    }
}
