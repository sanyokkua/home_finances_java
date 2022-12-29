package ua.home.finances.web.auth.api;

import org.springframework.http.ResponseEntity;
import ua.home.finances.web.auth.dtos.ReqPasswordUpdateDto;
import ua.home.finances.web.auth.dtos.ReqRegistrationDto;

import java.security.Principal;

public interface RegistrationController {
    ResponseEntity<String> register(ReqRegistrationDto reqRegistrationDto);

    ResponseEntity<String> updatePassword(Principal principal, ReqPasswordUpdateDto reqPasswordUpdateDto);
}
