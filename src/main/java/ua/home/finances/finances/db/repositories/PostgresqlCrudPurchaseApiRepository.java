package ua.home.finances.finances.db.repositories;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.val;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.home.finances.finances.db.models.Purchase;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Log
@Repository
@RequiredArgsConstructor
public class PostgresqlCrudPurchaseApiRepository implements CrudPurchaseApi {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public boolean createPurchase(final Purchase purchase) {

        val sql = """
                INSERT INTO app_finance.purchase (p_id, p_name, p_coins, p_currency, p_date) 
                VALUES (DEFAULT, :name, :coins, :currency, :date) 
                ON CONFLICT DO NOTHING
                """;
        val num = jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(purchase));
        return num > 0;
    }

    @Override
    public boolean updatePurchase(final Purchase purchase) {
        val sql = """
                UPDATE app_finance.purchase
                SET  p_name = :name, p_coins = :coins, p_currency = :currency, p_date = :date
                WHERE p_id = :id
                """;
        val num = jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(purchase));
        return num > 0;
    }

    @Override
    public boolean deletePurchase(final long id) {
        val sql = "delete from app_finance.purchase WHERE p_id = :id";
        val params = Map.of("id", id);
        val num = jdbcTemplate.update(sql, params);
        return num > 0;
    }

    @Override
    public Purchase getPurchase(final long id) {
        val sql = "select * from app_finance.purchase where p_id = :id";
        val params = Map.of("id", id);
        return jdbcTemplate.queryForObject(sql, params, Purchase.class);
    }

    @Override
    public List<Purchase> getAll() {
        val sql = "select * from app_finance.purchase";
        return jdbcTemplate.queryForList(sql, Map.of(), Purchase.class);
    }

    @Override
    public List<String> getAllNames() {
        val sql = "select * from app_finance.purchase";
        val list = jdbcTemplate.queryForList(sql, Map.of(), Purchase.class);
        val res = Optional.ofNullable(list);
        return res.stream()
                  .flatMap(List::stream)
                  .map(Purchase::getName)
                  .collect(Collectors.toList());
    }
}
