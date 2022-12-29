package ua.home.finances.web.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
