package ua.home.finances.web.app.controllers;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.home.finances.logic.services.api.Result;
import ua.home.finances.logic.services.api.UserCrudService;
import ua.home.finances.web.app.api.AdminUserController;
import ua.home.finances.web.app.dtos.DeleteResults;
import ua.home.finances.web.app.dtos.ReqUpdateUserSettingsDto;
import ua.home.finances.web.app.dtos.RespListItems;
import ua.home.finances.web.app.dtos.RespUserDto;

import java.security.Principal;

@RestController("/api/v1/users")
@RequiredArgsConstructor
public class RestAdminUserController implements AdminUserController {
    private final UserCrudService userCrudService;

    private long getCurrentUserId(Principal principal) {
        val userEmail = principal.getName();
        val user = userCrudService.findUserByEmail(userEmail);
        return user.getUserId();
    }

    private void validateThatNotCurrentUserWillBeProcessed(Principal principal,
                                                           ReqUpdateUserSettingsDto reqUpdateUserSettingsDto) {
        validateThatNotCurrentUserWillBeProcessed(principal, reqUpdateUserSettingsDto.getUserId());
    }

    private void validateThatNotCurrentUserWillBeProcessed(Principal principal, long userId) {
        val currentUserId = getCurrentUserId(principal);
        if (currentUserId == userId) {
            throw new IllegalArgumentException("Current user can't be processed");
        }
    }

    @PatchMapping("/{userId}") //TODO: FIX MISSING ID in INTERFACE
    @Override
    public ResponseEntity<RespUserDto> updateUser(Principal principal,
                                                  @RequestBody ReqUpdateUserSettingsDto reqUpdateUserSettingsDto) {
        validateThatNotCurrentUserWillBeProcessed(principal, reqUpdateUserSettingsDto);

        val userDto = ReqUpdateUserSettingsDto.fromRequest(reqUpdateUserSettingsDto);

        val result = userCrudService.update(userDto);

        val responseEntity = RespUserDto.fromUserDto(result);
        return ResponseEntity.status(HttpStatus.OK).body(responseEntity);
    }

    @DeleteMapping("/{userId}")
    @Override
    public ResponseEntity<DeleteResults> deleteUser(Principal principal, @PathVariable long userId) {
        validateThatNotCurrentUserWillBeProcessed(principal, userId);

        val result = userCrudService.delete(userId);

        val deleteResult = Result.SUCCESS.equals(result) ? DeleteResults.DELETED : DeleteResults.NOT_DELETED;
        return ResponseEntity.status(HttpStatus.OK).body(deleteResult);
    }

    @GetMapping("/{userId}")
    @Override
    public ResponseEntity<RespUserDto> findUserById(Principal principal, @PathVariable long userId) {
        validateThatNotCurrentUserWillBeProcessed(principal, userId);

        val result = userCrudService.findById(userId);

        val responseEntity = RespUserDto.fromUserDto(result);
        return ResponseEntity.status(HttpStatus.OK).body(responseEntity);
    }

    @GetMapping(value = "/", params = "email")
    @Override
    public ResponseEntity<RespUserDto> findUserByEmail(Principal principal, @RequestParam String email) {
        val result = userCrudService.findUserByEmail(email);

        validateThatNotCurrentUserWillBeProcessed(principal, result.getUserId());

        val responseEntity = RespUserDto.fromUserDto(result);
        return ResponseEntity.status(HttpStatus.OK).body(responseEntity);
    }

    @GetMapping(value = "/")
    @Override
    public ResponseEntity<RespListItems<RespUserDto>> findUsers(Principal principal) {
        val userId = getCurrentUserId(principal);

        val result = userCrudService.findAll();

        val purchasesRespItems = result.stream()
                .filter(u -> u.getUserId() != userId)
                .map(RespUserDto::fromUserDto)
                .toList();
        val responseEntity = new RespListItems<>(purchasesRespItems);
        return ResponseEntity.status(HttpStatus.OK).body(responseEntity);
    }
}
