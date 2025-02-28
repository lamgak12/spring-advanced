package org.example.expert.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.annotation.Auth;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.user.dto.request.UserRoleChangeRequest;
import org.example.expert.domain.user.service.UserAdminService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users/{userId}")
public class UserAdminController {

    private final UserAdminService userAdminService;

    @PatchMapping
    public void changeUserRole(
            @PathVariable long userId,
            @Auth AuthUser authUser,
            @RequestBody UserRoleChangeRequest userRoleChangeRequest
    ){
        userAdminService.changeUserRole(userId, authUser, userRoleChangeRequest);
    }

    @DeleteMapping
    public void deleteUser(
            @PathVariable long userId,
            @Auth AuthUser authUser
    ){
        userAdminService.deleteUser(userId, authUser);
    }
}
