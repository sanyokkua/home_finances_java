package ua.home.finances.logic.services.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.home.finances.logic.common.UserRoles;
import ua.home.finances.logic.db.models.ApplicationUser;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long userId;
    private String email;
    private String password;
    private String nickname;
    @Builder.Default
    private boolean isActive = false;
    @Builder.Default
    private UserRoles role = UserRoles.USER;

    public static UserDto fromModel(ApplicationUser applicationUser) {
        return UserDto.builder()
                .userId(applicationUser.getUserId())
                .email(applicationUser.getEmail())
                .password(applicationUser.getPassword())
                .nickname(applicationUser.getNickname())
                .isActive(applicationUser.isActive())
                .role(applicationUser.getRole())
                .build();
    }

    public static ApplicationUser fromDto(UserDto userDto) {
        return ApplicationUser.builder()
                .userId(userDto.getUserId())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .nickname(userDto.getNickname())
                .isActive(userDto.isActive())
                .role(userDto.getRole())
                .build();
    }
}
