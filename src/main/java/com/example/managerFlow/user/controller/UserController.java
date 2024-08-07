package com.example.managerFlow.user.controller;

import com.example.managerFlow.user.dto.UserDto;
import com.example.managerFlow.user.service.LoginService;
import com.example.managerFlow.user.service.JoinService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller // 컨트롤러 임을 명시
public class UserController {


    private final LoginService loginService;
    private final JoinService joinService;

    @Autowired
    public UserController(LoginService loginService, JoinService joinService) {
        this.loginService = loginService;
        this.joinService = joinService;
    }

    @GetMapping("/join") //GET
    public String showJoinForm(Model model) {
        model.addAttribute("userDto", new UserDto()); // 빈 객체 폼에 전달
        return "user/joinPage"; // 뷰 반환
    }

    @PostMapping("/join-submit")
    public String join(@Valid @ModelAttribute UserDto userDto, BindingResult Result, Model model) {
        if(Result.hasErrors()){ // 폼데이터 유효성 검증
            return "user/joinPage"; // 오류 있으면 다시 뷰 반환
        }

        joinService.save(userDto); // join
        model.addAttribute("message", "successfully!!"); // message 전달
        return "user/login"; // 성공 뷰 반환
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
