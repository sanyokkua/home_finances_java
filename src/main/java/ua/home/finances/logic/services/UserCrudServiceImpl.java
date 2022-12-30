package ua.home.finances.logic.services;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.home.finances.logic.common.exceptions.UserIsNotFoundException;
import ua.home.finances.logic.db.repositories.api.ApplicationUserCrudJdbcRepository;
import ua.home.finances.logic.services.api.Result;
import ua.home.finances.logic.services.api.UserCrudService;
import ua.home.finances.logic.services.dtos.UserDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCrudServiceImpl implements UserCrudService {
    private final ApplicationUserCrudJdbcRepository userCrudJdbcRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto create(UserDto entity) {
        val nickname = StringUtils.isNotBlank(entity.getNickname()) ? entity.getNickname() : entity.getEmail();
        val password = passwordEncoder.encode(entity.getPassword());
        val applicationUser = UserDto.fromDto(entity);
        applicationUser.setNickname(nickname);
        applicationUser.setPassword(password);
        applicationUser.setActive(true);//TODO: until confirmation by email will be implemented

        val res = userCrudJdbcRepository.create(applicationUser);
        res.setPassword("");

        return UserDto.fromModel(res);
    }

    @Override
    public UserDto update(UserDto entity) {
        val res = userCrudJdbcRepository.update(UserDto.fromDto(entity));
        res.setPassword("");

        return UserDto.fromModel(res);
    }

    @Override
    public Result delete(Long entityId) {
        val res = userCrudJdbcRepository.delete(entityId);
        return res ? Result.SUCCESS : Result.ERROR;
    }

    @Override
    public UserDto findById(Long entityId) {
        val foundAppUser = userCrudJdbcRepository.findById(entityId);

        if (foundAppUser.isEmpty()) {
            throw new UserIsNotFoundException("ApplicationUser is not found.");
        }

        val user = foundAppUser.get();
        user.setPassword("");
        return UserDto.fromModel(user);
    }

    @Override
    public UserDto findUserByEmail(String email) {
        val foundAppUser = userCrudJdbcRepository.findByEmail(email);

        if (foundAppUser.isEmpty()) {
            throw new UserIsNotFoundException("ApplicationUser is not found.");
        }

        val user = foundAppUser.get();
        user.setPassword("");
        return UserDto.fromModel(user);
    }

    @Override
    public List<UserDto> findAll() {
        return userCrudJdbcRepository.findAll().stream().map(UserDto::fromModel).toList();
    }
}
