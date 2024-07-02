package com.example.managerFlow.userService;

import com.example.managerFlow.user.dto.UserDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;



public class UserJoinValidationTest {


    @Autowired
    private Validator validator;

    @BeforeEach
    void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    @DisplayName("userName @NotEmpty")
    void NameValidationIsEmpty() {

        //given
        UserDto userDto = new UserDto(null, "email@naver.com", "storeName", "password", "password");

        //when
        Set<ConstraintViolation<UserDto>> validate = validator.validate(userDto);

        //Then
        Iterator<ConstraintViolation<UserDto>> iterator = validate.iterator();
        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<UserDto> next = iterator.next();
            messages.add(next.getMessage());
            System.out.println("message = " + next.getMessage());
        }

        Assertions.assertThat(messages).contains("사용자명은 필수 입력 항목입니다.");
    }

    @Test
    @DisplayName("UserName @Size")
    void NameValidationIsShortLength() {

        //given
        UserDto userDto = new UserDto("n", "email@naver.com", "storeName", "password", "password");

        //when
        Set<ConstraintViolation<UserDto>> validate = validator.validate(userDto);

        //Then
        Iterator<ConstraintViolation<UserDto>> iterator = validate.iterator();
        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<UserDto> next = iterator.next();
            messages.add(next.getMessage());
            System.out.println("message = " + next.getMessage());
        }

        Assertions.assertThat(messages).contains("사용자명은 2자에서 30자 사이어야 합니다.");
    }

    @Test
    @DisplayName("UserName @NotEmpty")
    void emailValidationIsNotEmpty() {

        //given
        UserDto userDto = new UserDto("userName", null, "storeName", "password", "password");

        //when
        Set<ConstraintViolation<UserDto>> validate = validator.validate(userDto);

        //Then
        Iterator<ConstraintViolation<UserDto>> iterator = validate.iterator();
        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<UserDto> next = iterator.next();
            messages.add(next.getMessage());
            System.out.println("message = " + next.getMessage());
        }

        Assertions.assertThat(messages).contains("이메일은 필수 입력 항목입니다.");
    }

    @Test
    @DisplayName("UserName @Email")
    void emailValidationIsFormat() {

        //given
        UserDto userDto = new UserDto("userName", "email", "storeName", "password", "password");

        //when
        Set<ConstraintViolation<UserDto>> validate = validator.validate(userDto);

        //Then
        Iterator<ConstraintViolation<UserDto>> iterator = validate.iterator();
        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<UserDto> next = iterator.next();
            messages.add(next.getMessage());
            System.out.println("message = " + next.getMessage());
        }

        Assertions.assertThat(messages).contains("유효한 이메일 주소여야 합니다.");
    }

    @Test
    @DisplayName("storeName @NotEmpty")
    void storeNameValidationIsNotEmpty() {

        //given
        UserDto userDto = new UserDto("userName", "email@email.com", null, "password", "password");

        //when
        Set<ConstraintViolation<UserDto>> validate = validator.validate(userDto);

        //Then
        Iterator<ConstraintViolation<UserDto>> iterator = validate.iterator();
        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<UserDto> next = iterator.next();
            messages.add(next.getMessage());
            System.out.println("message = " + next.getMessage());
        }

        Assertions.assertThat(messages).contains("가게 이름은 필수 입력 항목입니다.");
    }

    @Test
    @DisplayName("storeName @Size")
    void storeNameValidationIsShortSize() {

        //given
        UserDto userDto = new UserDto("userName", "email@email.com", "n", "password", "password");

        //when
        Set<ConstraintViolation<UserDto>> validate = validator.validate(userDto);

        //Then
        Iterator<ConstraintViolation<UserDto>> iterator = validate.iterator();
        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<UserDto> next = iterator.next();
            messages.add(next.getMessage());
            System.out.println("message = " + next.getMessage());
        }

        Assertions.assertThat(messages).contains("가게 이름은 2자에서 30자 사이어야 합니다.");
    }

    @Test
    @DisplayName("password @IsEmpty, isPasswordConfirmed @IsEmpty")
    void passwordValidationIsEmptyAndPasswordConfirmedIsEmpty() {

        //given
        UserDto userDto = new UserDto("userName", "email@email.com", "storeName", "", "");

        //when
        Set<ConstraintViolation<UserDto>> validate = validator.validate(userDto);

        //Then
        Iterator<ConstraintViolation<UserDto>> iterator = validate.iterator();
        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<UserDto> next = iterator.next();
            messages.add(next.getMessage());
            System.out.println("message = " + next.getMessage());
        }

        Assertions.assertThat(messages).contains("비밀번호는 8자 이상 20자 이하여야 합니다.",
                "비밀번호는 필수 입력 항목입니다.",
                "비밀번호는 영문, 숫자, 특수문자를 포함하여 8자 이상 20자 이하여야 합니다.",
                "비밀번호 확인은 필수 입력 항목입니다.");
    }

    @Test
    @DisplayName("password @Size")
    void passwordValidationIsShortSize() {

        //given
        UserDto userDto = new UserDto("userName", "email@email.com", "storeName", "123asd@", "123asd@");

        //when
        Set<ConstraintViolation<UserDto>> validate = validator.validate(userDto);

        //Then
        Iterator<ConstraintViolation<UserDto>> iterator = validate.iterator();
        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<UserDto> next = iterator.next();
            messages.add(next.getMessage());
            System.out.println("message = " + next.getMessage());
        }

        Assertions.assertThat(messages).contains("비밀번호는 영문, 숫자, 특수문자를 포함하여 8자 이상 20자 이하여야 합니다.", "비밀번호는 8자 이상 20자 이하여야 합니다.");
    }

    @Test
    @DisplayName("password @PasswordComplexity")
    void passwordValidationIsComplexity() {

        //given
        UserDto userDto = new UserDto("userName", "email@email.com", "storeName", "12345asd1", "12345asd1");

        //when
        Set<ConstraintViolation<UserDto>> validate = validator.validate(userDto);

        //Then
        Iterator<ConstraintViolation<UserDto>> iterator = validate.iterator();
        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<UserDto> next = iterator.next();
            messages.add(next.getMessage());
            System.out.println("message = " + next.getMessage());
        }

        Assertions.assertThat(messages).contains("비밀번호는 영문, 숫자, 특수문자를 포함하여 8자 이상 20자 이하여야 합니다.");
    }

    @Test
    @DisplayName("reconfirmPassword @Empty")
    void reconfirmPasswordValidationIsEmpty() {

        //given
        UserDto userDto = new UserDto("userName", "email@email.com", "storeName", "12345asd1@", "");

        //when
        Set<ConstraintViolation<UserDto>> validate = validator.validate(userDto);

        //Then
        Iterator<ConstraintViolation<UserDto>> iterator = validate.iterator();
        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<UserDto> next = iterator.next();
            messages.add(next.getMessage());
            System.out.println("message = " + next.getMessage());
        }

        Assertions.assertThat(messages).contains("비밀번호와 비밀번호 확인이 일치해야 합니다.", "비밀번호 확인은 필수 입력 항목입니다.");
    }

    @Test
    @DisplayName("reconfirmPassword NotSame")
    void reconfirmPasswordValidationIsNotSame() {

        //given
        UserDto userDto = new UserDto("userName", "email@email.com", "storeName", "12345asd1@", "asdasd@!A");

        //when
        Set<ConstraintViolation<UserDto>> validate = validator.validate(userDto);

        //Then
        Iterator<ConstraintViolation<UserDto>> iterator = validate.iterator();
        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<UserDto> next = iterator.next();
            messages.add(next.getMessage());
            System.out.println("message = " + next.getMessage());
        }

        Assertions.assertThat(messages).contains("비밀번호와 비밀번호 확인이 일치해야 합니다.");
    }








}
