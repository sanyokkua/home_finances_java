package ua.home.finances.web.auth.controllers;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.home.finances.web.auth.api.AuthorizationController;
import ua.home.finances.web.auth.dtos.RespTokenDto;
import ua.home.finances.web.auth.services.TokenService;

@RestController
@RequiredArgsConstructor
public class RestAuthorizationController implements AuthorizationController {
    private final TokenService tokenService;

    @PostMapping("/auth/token")
    @Override
    public ResponseEntity<RespTokenDto> login(Authentication authentication) {
        val token = tokenService.generateToken(authentication);
        val responseEntity = new RespTokenDto(token);
        return ResponseEntity.ok(responseEntity);
    }
}
