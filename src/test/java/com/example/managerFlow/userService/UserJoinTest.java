package com.example.managerFlow.userService;

import com.example.managerFlow.user.domain.User;
import com.example.managerFlow.user.dto.UserJoinDto;
import com.example.managerFlow.user.repository.UserRepository;
import com.example.managerFlow.user.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest // 스프링 컨텍스트 로드, DI 사용가능
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 실제 DB와 동일한 조건에서 수행
public class UserJoinTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    UserJoinDto user1;

    @BeforeEach
    void setUp() {
        user1 = new UserJoinDto("name1", "email@naver.com", "store1", "password1", "password1");
    }

    @AfterEach()
    void setup() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 테스트")
    void joinTest() {
        //given
        // @BeforeEach의 user1
        UserJoinDto user2 = new UserJoinDto("name2", "email2@naver.com", "store2", "password2", "password2");
        //when
        userService.save(user1);
        userService.save(user2);

        //then
        //회원가입 확인
        Optional<User> savedUser1 = userRepository.findByEmail(user1.getEmail());
        assertThat(savedUser1.get().getEmail()).isEqualTo(user1.getEmail());

        //회원 수 2명 확인
        long userCount = userRepository.count();
        assertThat(userCount).isEqualTo(2);

    }


    @Test
    @DisplayName("이메일 중복 테스트, 예외가 발생해야 한다.")
    void joinDuplicationExceptionTest() {
        //given
        // @BeforeEach의 user1
        userService.save(user1); //초기 유저 저장
        UserJoinDto user2 = new UserJoinDto("name1", "email@naver.com", "store1", "password1", "password1");

        //when then
        org.junit.jupiter.api.Assertions.assertThrows(DataIntegrityViolationException.class, () ->{
            userService.save(user2); // 예외발생
        });

    }

    @Test
    @DisplayName("비밀번호 암호화 확인")
    void passwordEncryptTest() {
        //given
        // @BeforeEach의 user1
        String password = user1.getPassword();
        //when
        userService.save(user1);

        //then
        Optional<User> savedUser = userRepository.findByEmail("email@naver.com");
        String Encryptpassword = savedUser.get().getPassword();
        assertThat(password).isNotEqualTo(Encryptpassword); // 사용자입력 패스워드와 암호화 패스워드 비교

    }

}

