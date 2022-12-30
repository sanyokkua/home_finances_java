package ua.home.finances.web.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.home.finances.logic.services.dtos.UserDto;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RespUserDto {
    private long userId;
    private String email;
    private String nickname;
    private boolean isActive;
    private String role;

    public static RespUserDto fromUserDto(UserDto userDto) {
        return RespUserDto.builder()
                .userId(userDto.getUserId())
                .email(userDto.getEmail())
                .nickname(userDto.getNickname())
                .isActive(userDto.isActive())
                .role(userDto.getRole().name())
                .build();
    }
}
