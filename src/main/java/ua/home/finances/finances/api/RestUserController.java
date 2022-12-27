package ua.home.finances.finances.api;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.home.finances.finances.services.api.UserService;
import ua.home.finances.finances.services.dtos.UserDto;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class RestUserController {

    private final UserService userService;

    @PostMapping("/")
    public ResponseEntity<UserDto> create(@RequestBody UserDto userDto) {
        userService.createUser(userDto);
        val user = userService.findUserByEmail(userDto.getEmail());
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody UserDto userDto) {
        userDto.setUserId(id);
        userService.updateUser(userDto);
        val user = userService.findUserByEmail(userDto.getEmail());
        return ResponseEntity.ok(user);
    }
}
