package org.example.expert.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.user.dto.request.UserRoleChangeRequest;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.enums.UserRole;
import org.example.expert.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAdminService {

    private final UserRepository userRepository;

    @Transactional
    public void changeUserRole(long userId, AuthUser authUser, UserRoleChangeRequest userRoleChangeRequest) {
        if(authUser.getUserRole() != UserRole.ADMIN) {
            throw new InvalidRequestException("관리자만 해당 기능을 이용할 수 있습니다.");
        }

        User user = userRepository.findById(userId).orElseThrow(() -> new InvalidRequestException("User not found"));

        user.updateRole(UserRole.of(userRoleChangeRequest.getRole()));
    }

    public void deleteUser(long userId, AuthUser authUser) {

        if(authUser.getUserRole() != UserRole.ADMIN) {
            throw new InvalidRequestException("관리자만 해당 기능을 이용할 수 있습니다.");
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new InvalidRequestException("User not found"));

        if (user.getUserRole() != UserRole.USER) {
            throw new InvalidRequestException("관리자는 삭제할 수 없습니다.");
        }

        userRepository.delete(user);
    }
}
