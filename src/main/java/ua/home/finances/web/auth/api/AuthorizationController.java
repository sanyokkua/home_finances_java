package ua.home.finances.web.auth.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import ua.home.finances.web.auth.dtos.RespTokenDto;

public interface AuthorizationController {
    ResponseEntity<RespTokenDto> login(Authentication authentication);
}
