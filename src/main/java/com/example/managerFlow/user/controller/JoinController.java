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
import org.springframework.web.bind.annotation.*;

@Controller // 컨트롤러 임을 명시
public class JoinController {
    private final JoinService joinService;

    @Autowired
    public JoinController(JoinService joinService) {
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

}