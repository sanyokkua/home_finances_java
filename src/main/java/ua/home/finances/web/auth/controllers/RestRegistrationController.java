package ua.home.finances.web.auth.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ua.home.finances.web.auth.api.RegistrationController;
import ua.home.finances.web.auth.dtos.ReqPasswordUpdateDto;
import ua.home.finances.web.auth.dtos.ReqRegistrationDto;

import java.security.Principal;

@RestController
public class RestRegistrationController implements RegistrationController {
    @Override
    public ResponseEntity<String> register(ReqRegistrationDto reqRegistrationDto) {
        return null;
    }

    @Override
    public ResponseEntity<String> updatePassword(Principal principal, ReqPasswordUpdateDto reqPasswordUpdateDto) {
        return null;
    }
}
