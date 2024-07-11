package com.example.managerFlow.user.service;

import com.example.managerFlow.user.domain.User;
import com.example.managerFlow.user.dto.NewPasswordDto;
import com.example.managerFlow.user.dto.UserDto;
import jakarta.mail.internet.MimeMessage;

public interface PasswordService {
    MimeMessage CreateMail(String mail);
    int sendMail(String mail);
    boolean findUser(UserDto userDto);
    User updatePassword(NewPasswordDto newPasswordDto);
}
