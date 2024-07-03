package com.example.managerFlow.user.dto;


import com.example.managerFlow.user.validation.PasswordComplexity;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data // getter, setter, toString, equles 등등
@NoArgsConstructor
@AllArgsConstructor
public class NewPasswordDto {

    private String email;

    @NotEmpty(message = "비밀번호는 필수 입력 항목입니다")
    @PasswordComplexity(message = "비밀번호는 영문, 숫자, 특수문자를 포함하여 8자 이상 20자 이하여야 합니다.")
    private String password;

    @NotEmpty(message = "비밀번호 확인은 필수 입력 항목입니다")
    private String reconfirmPassword;

    @AssertTrue(message = "비밀번호와 비밀번호 확인이 일치해야 합니다")
    public boolean isPasswordConfirmed() {
        return password != null && password.equals(reconfirmPassword);
    }
}
