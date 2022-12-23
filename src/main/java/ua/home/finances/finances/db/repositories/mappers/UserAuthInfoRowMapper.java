package ua.home.finances.finances.db.repositories.mappers;

import org.springframework.jdbc.core.RowMapper;
import ua.home.finances.finances.db.models.UserAuthInfo;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAuthInfoRowMapper implements RowMapper<UserAuthInfo> {
    @Override
    public UserAuthInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        return UserAuthInfo.builder()
                .userId(rs.getLong("u_id"))
                .email(rs.getString("u_email"))
                .password(rs.getString("u_password"))
                .build();
    }
}
