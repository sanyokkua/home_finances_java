package ua.home.finances.logic.db.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.home.finances.logic.common.UserRoles;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationUser {
    private Long userId;
    private String email;
    private String password;
    private String nickname;
    @Builder.Default
    private boolean isActive = false;
    @Builder.Default
    private UserRoles role = UserRoles.USER;
}
