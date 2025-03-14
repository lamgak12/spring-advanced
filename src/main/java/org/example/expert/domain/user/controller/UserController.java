package org.example.expert.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.annotation.Auth;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.user.dto.request.UserChangePasswordRequest;
import org.example.expert.domain.user.dto.request.UserDeleteRequest;
import org.example.expert.domain.user.dto.response.UserResponse;
import org.example.expert.domain.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable long userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @PutMapping("/{userId}")
    public void changePassword(
            @Auth AuthUser authUser,
            @PathVariable long userId,
            @Valid @RequestBody UserChangePasswordRequest userChangePasswordRequest
    ) {
        userService.changePassword(authUser.getId(), userId, userChangePasswordRequest);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(
            @Auth AuthUser authUser,
            @PathVariable long userId,
            @Valid @RequestBody UserDeleteRequest userDeleteRequest
    ){
        userService.deleteUser(authUser.getId(), userId, userDeleteRequest);
    }
}
