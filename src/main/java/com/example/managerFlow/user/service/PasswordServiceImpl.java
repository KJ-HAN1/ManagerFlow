package com.example.managerFlow.user.service;

import com.example.managerFlow.user.domain.User;
import com.example.managerFlow.user.dto.NewPasswordDto;
import com.example.managerFlow.user.dto.UserDto;
import com.example.managerFlow.user.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PasswordServiceImpl implements PasswordService {

    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PasswordServiceImpl(JavaMailSender javaMailSender,
                               UserRepository userRepository,
                               PasswordEncoder passwordEncoder) {
        this.javaMailSender = javaMailSender;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private final String senderEmail = "Managerflow@mflow.com";
    private static int number;


    public static int createNumber() {
        number = (int)(Math.random() * (900000)) + 100000; //(int) Math.random() * (최댓값-최소값+1) + 최소값
        return number;
    }

    @Override
    public MimeMessage CreateMail(String mail) {
        createNumber();
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, mail);
            message.setSubject("ManagerFlow 이메일 인증");
            String body = "";
            body += "<h3>" + "요청하신 인증 번호입니다." + "</h3>";
            body += "<h1>" + number + "</h1>";
            body += "<h3>" + "감사합니다." + "</h3>";
            message.setText(body,"UTF-8", "html");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return message;

    }

    @Override
    public int sendMail(String mail) {
        MimeMessage message = CreateMail(mail);
        javaMailSender.send(message);
        return number;
    }


    @Override
    @Transactional
    public boolean findUser(UserDto userDto) {
        Optional<User> user = userRepository.findByUserNameAndEmail(userDto.getUserName(), userDto.getEmail());
        return user.isPresent();
    }

    public User updatePassword(NewPasswordDto newPasswordDto) {
        User user = userRepository.findByEmail(newPasswordDto.getEmail()).orElseThrow(
                () -> new RuntimeException("User not found with Email " + newPasswordDto.getEmail()));
        user.setPassword(passwordEncoder.encode(newPasswordDto.getPassword()));
        return user;
    }

}
