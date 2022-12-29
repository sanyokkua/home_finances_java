package ua.home.finances.web.auth.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReqPasswordUpdateDto {
    private String oldPassword;
    private String newPassword;
}
