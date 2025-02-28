package org.example.expert.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static org.example.expert.domain.common.constant.RegexpConst.PASSWORD_REGEXP;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserChangePasswordRequest {

    @NotBlank
    private String oldPassword;

    @NotBlank
    @Pattern(regexp = PASSWORD_REGEXP,
            message = "비밀번호는 8자 이상이어야 하고, 숫자와 대문자를 포함해야 합니다.")
    private String newPassword;
}
