package ua.home.finances.finances.db.repositories.mappers;

import org.springframework.jdbc.core.RowMapper;
import ua.home.finances.finances.db.models.PurchaseList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PurchaseListRowMapper implements RowMapper<PurchaseList> {
    @Override
    public PurchaseList mapRow(ResultSet rs, int rowNum) throws SQLException {
        return PurchaseList.builder()
                .listId(rs.getLong("l_id"))
                .listUserId(rs.getLong("l_u_id"))
                .name(rs.getString("l_name"))
                .build();
    }
}
