package org.example.expert.domain.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static org.example.expert.domain.common.constant.RegexpConst.EMAIL_REGEXP;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SigninRequest {
    
    @NotBlank
    @Pattern(
        regexp = EMAIL_REGEXP,
        message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @NotBlank
    private String password;
}
