package ua.home.finances.web.app.api;

import org.springframework.http.ResponseEntity;
import ua.home.finances.web.app.dtos.DeleteResults;
import ua.home.finances.web.app.dtos.ReqUpdateUserSettingsDto;
import ua.home.finances.web.app.dtos.RespListItems;
import ua.home.finances.web.app.dtos.RespUserDto;

import java.security.Principal;

public interface AdminUserController {

    ResponseEntity<RespUserDto> updateUser(Principal principal, ReqUpdateUserSettingsDto reqUpdateUserSettingsDto);

    ResponseEntity<DeleteResults> deleteUser(Principal principal, long userId);

    ResponseEntity<RespUserDto> findUserById(Principal principal, long userId);

    ResponseEntity<RespUserDto> findUserByEmail(Principal principal, String email);

    ResponseEntity<RespListItems<RespUserDto>> findUsers(Principal principal);
}
