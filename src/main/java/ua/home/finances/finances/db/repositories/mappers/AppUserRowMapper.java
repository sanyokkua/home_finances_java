package ua.home.finances.finances.db.repositories.mappers;

import org.springframework.jdbc.core.RowMapper;
import ua.home.finances.finances.db.models.AppUser;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AppUserRowMapper implements RowMapper<AppUser> {
    @Override
    public AppUser mapRow(ResultSet rs, int rowNum) throws SQLException {
        return AppUser.builder().userId(rs.getLong("u_id")).nickname(rs.getString("u_nickname")).build();
    }
}
