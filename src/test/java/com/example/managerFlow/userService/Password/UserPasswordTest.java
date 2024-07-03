package com.example.managerFlow.userService.Password;

import com.example.managerFlow.user.domain.User;
import com.example.managerFlow.user.dto.NewPasswordDto;
import com.example.managerFlow.user.dto.UserDto;
import com.example.managerFlow.user.repository.UserRepository;
import com.example.managerFlow.user.service.PasswordService;
import com.example.managerFlow.user.service.PasswordServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@SpringBootTest
public class UserPasswordTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    User user;

    @BeforeEach
    void beforeSetup() {
        user = User.builder().
                userName("이름").
                storeName("가게").
                email("qwer1234@naver.com").
                password("qwer1234@@@").
                build();

        userRepository.save(user);
    }

    @AfterEach
    void afterSetup() {
            userRepository.deleteAll();
    }


    @Test
    @DisplayName("유저 찾기 성공 true 반환")
    void findUserReturnTrueTest(){
        //given
        //BeforeEach의 user
        UserDto userDto = UserDto.builder().userName("이름").email("qwer1234@naver.com").build();

        //when
        boolean user = passwordService.findUser(userDto);

        //then
        Assertions.assertThat(user).isEqualTo(true);

    }

    @Test
    @DisplayName("유저 찾기 실패 false 반환")
    void findUserReturnFalseTest() {

        //given
        //BeforeEach의 user
        UserDto userDto = UserDto.builder().userName("이름").email("qwer123@naver.com").build();

        //when
        boolean user = passwordService.findUser(userDto);

        //then
        Assertions.assertThat(user).isEqualTo(false);
    }

    @Test
    @DisplayName("인증번호 6자리 생성 검증")
    void generateVerificationNumber() {

        //given
        int number = PasswordServiceImpl.createNumber();

        //when then
        org.junit.jupiter.api.Assertions.assertTrue(number >= 100000 && number <= 999999);
    }

    @Test
    @DisplayName("비밀번호 업데이트 테스트")
    void updateUserPasswordTest() {
        //given
        NewPasswordDto newPasswordDto = NewPasswordDto.builder().
                email("qwer1234@naver.com").password("password@@").build();

        //when
        User user = passwordService.updatePassword(newPasswordDto);

        //then
        org.junit.jupiter.api.Assertions.assertTrue(
                passwordEncoder.matches(
                        newPasswordDto.getPassword(),
                        user.getPassword()
                ));

    }

    

}
