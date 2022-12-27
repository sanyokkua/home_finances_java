package ua.home.finances.finances.services.api;

import ua.home.finances.finances.services.dtos.UserDto;

public interface UserService {

    void createUser(UserDto userDto);

    void updateUser(UserDto userDto);

    void deleteUser(long id);

    UserDto findUserByEmail(String email);

    UserDto findUserByNickname(String nickname);
}
