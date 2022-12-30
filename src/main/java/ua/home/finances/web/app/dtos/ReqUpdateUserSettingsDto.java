package ua.home.finances.web.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.home.finances.logic.common.UserRoles;
import ua.home.finances.logic.services.dtos.UserDto;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReqUpdateUserSettingsDto {
    private Long userId;
    private boolean isActive;
    private String role;

    public static UserDto fromRequest(ReqUpdateUserSettingsDto reqUpdateUserSettingsDto) {
        return UserDto.builder()
                .userId(reqUpdateUserSettingsDto.getUserId())
                .isActive(reqUpdateUserSettingsDto.isActive())
                .role(UserRoles.valueOf(reqUpdateUserSettingsDto.getRole()))
                .build();
    }
}
