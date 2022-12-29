package ua.home.finances.web.auth.services;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.home.finances.logic.services.api.UserCrudService;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final UserCrudService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        val res = userService.findUserByEmail(username);

        return User.builder().username(res.getEmail()).password(res.getPassword()).authorities("read", "write").build();
    }
}
