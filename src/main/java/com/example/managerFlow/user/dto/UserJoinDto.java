package com.example.managerFlow.user.dto;


import com.example.managerFlow.user.domain.User;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder // 빌더 패턴 사용
@Data // getter, setter, toString, equles 등등
@NoArgsConstructor // 파라미터 없는 생성자
@AllArgsConstructor // 모든 필드 파라미터 필수 생성자
@Getter
public class UserJoinDto {

    @NotEmpty(message = "사용자명은 필수 입력 항목입니다")
    @Size(min = 2, max = 30, message = "사용자명은 2자에서 30자 사이어야 합니다")
    private String userName;

    @NotEmpty(message = "이메일은 필수 입력 항목입니다")
    @Email(message = "유효한 이메일 주소여야 합니다")
    private String email;

    @NotEmpty(message = "가게 이름은 필수 입력 항목입니다")
    @Size(min = 2, max = 30, message = "가게 이름은 2자에서 30자 사이어야 합니다")
    private String storeName;

    @NotEmpty(message = "비밀번호는 필수 입력 항목입니다")
    @Size(min = 6, message = "비밀번호는 최소 6자 이상이어야 합니다")
    private String password;

    @NotEmpty(message = "비밀번호 확인은 필수 입력 항목입니다")
    private String reconfirmPassword;


    //entity to dto
    public static UserJoinDto toUserJoinDto(User user) {
        UserJoinDto userJoinDto = new UserJoinDto();
        userJoinDto.setUserName(user.getUserName());
        userJoinDto.setEmail(user.getEmail());
        userJoinDto.setStoreName(user.getStoreName());
        userJoinDto.setPassword(user.getPassword());

        return userJoinDto;
    }

    @AssertTrue(message = "비밀번호와 비밀번호 확인이 일치해야 합니다")
    public boolean isPasswordConfirmed() {
        return password != null && password.equals(reconfirmPassword);
    }

}
