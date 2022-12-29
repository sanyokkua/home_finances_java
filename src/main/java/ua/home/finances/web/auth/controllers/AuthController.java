package ua.home.finances.web.auth.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.home.finances.web.auth.services.TokenService;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final TokenService tokenService;

    @PostMapping("/auth/token")
    public String token(Authentication authentication) {
        return tokenService.generateToken(authentication);
    }
}
