package ua.home.finances.logic.services.api;

import ua.home.finances.logic.services.dtos.UserDto;

import java.util.List;

public interface UserCrudService extends CrudService<UserDto, Long> {
    UserDto findUserByEmail(String email);

    List<UserDto> findAll();
}
