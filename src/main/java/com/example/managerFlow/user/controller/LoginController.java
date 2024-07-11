package com.example.managerFlow.user.controller;

import com.example.managerFlow.user.dto.UserDto;
import com.example.managerFlow.user.service.LoginService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginResult", new UserDto());
        return "user/login";
    }

    @PostMapping("/login")
    // httpSession을 사용해 로그인 상태를 유지
    public String login(@ModelAttribute UserDto userDto, HttpSession session, Model model) {
        UserDto loginResult = loginService.login(userDto);
        if (loginResult != null) {
            // 로그인 성공 시
            // 세션에 유저의 이메일 정보를 저장 <- 로그인 상태 유지에 활용
            session.setAttribute("loginResult", loginResult.getEmail());
            return "user/main";
        } else {
            // 로그인 실패 시
            model.addAttribute("loginError", "Invalid email or password.");
            return "user/login";
        }
    }
}