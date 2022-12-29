package ua.home.finances.logic.services.api;

import ua.home.finances.logic.services.dtos.UserDto;

public interface UserCrudService extends CrudService<UserDto, Long> {
    UserDto findUserByEmail(String email);
}
