package com.example.managerFlow.user.controller;

import com.example.managerFlow.user.dto.UserJoinDto;
import com.example.managerFlow.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // 컨트롤러 임을 명시
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/join") //GET
    public String showJoinForm(Model model) {
        model.addAttribute("userJoinDto", new UserJoinDto()); // 빈 객체 폼에 전달
        return "user/joinPage"; // 뷰 반환
    }

    @PostMapping("/join-submit")
    public String join(@Valid @ModelAttribute UserJoinDto userJoinDto, BindingResult Result, Model model) {
        if(Result.hasErrors()){ // 폼데이터 유효성 검증
            return "user/joinPage"; // 오류 있으면 다시 뷰 반환
        }

        userService.save(userJoinDto); // join
        model.addAttribute("message", "successfully!!"); // message 전달
        return "user/joinSuccessPage"; // 성공 뷰 반환
    }
}
