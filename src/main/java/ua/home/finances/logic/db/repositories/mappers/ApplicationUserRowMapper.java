package ua.home.finances.logic.db.repositories.mappers;

import lombok.val;
import org.springframework.jdbc.core.RowMapper;
import ua.home.finances.logic.common.UserRoles;
import ua.home.finances.logic.db.models.ApplicationUser;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ApplicationUserRowMapper implements RowMapper<ApplicationUser> {
    @Override
    public ApplicationUser mapRow(ResultSet rs, int rowNum) throws SQLException {
        val role = UserRoles.valueOf(rs.getString("u_role"));
        return ApplicationUser.builder()
                .userId(rs.getLong("u_id"))
                .email(rs.getString("u_email"))
                .password(rs.getString("u_password"))
                .nickname(rs.getString("u_nickname"))
                .isActive(rs.getBoolean("u_active"))
                .role(role)
                .build();
    }
}
