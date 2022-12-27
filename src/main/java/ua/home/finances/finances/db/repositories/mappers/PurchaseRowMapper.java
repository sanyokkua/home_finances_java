package ua.home.finances.finances.db.repositories.mappers;

import org.springframework.jdbc.core.RowMapper;
import ua.home.finances.finances.common.Currency;
import ua.home.finances.finances.db.models.Purchase;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PurchaseRowMapper implements RowMapper<Purchase> {
    @Override
    public Purchase mapRow(ResultSet rs, int rowNum) throws SQLException {
        String currencyValue = rs.getString("p_currency");
        Date sqlDate = rs.getDate("p_date");

        return Purchase.builder()
                .purchaseId(rs.getLong("p_id"))
                .purchaseListId(rs.getLong("p_l_id"))
                .name(rs.getString("p_name"))
                .coins(rs.getLong("p_coins"))
                .currency(Currency.valueOf(currencyValue))
                .date(sqlDate.toLocalDate())
                .category(rs.getString("p_category"))
                .build();
    }
}
