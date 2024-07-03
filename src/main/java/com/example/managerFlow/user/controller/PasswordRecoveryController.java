package com.example.managerFlow.user.controller;

import com.example.managerFlow.user.dto.NewPasswordDto;
import com.example.managerFlow.user.dto.UserDto;
import com.example.managerFlow.user.service.PasswordService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PasswordRecoveryController {

    private final PasswordService passwordService;

    @Autowired
    public PasswordRecoveryController(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @GetMapping("/find-pw")
    public String findPasswordForm(Model model){
        model.addAttribute("userDto", new UserDto());
        return "user/PasswordRecovery/findUser";
    }

    @PostMapping("/find-pw-submit")
    public String findPasswordSubmit(@ModelAttribute UserDto userDto, HttpSession session, Model model){
        boolean user = passwordService.findUser(userDto); // 유저 존재하면
        if(user){
            int verificationCode = passwordService.sendMail(userDto.getEmail()); // 유저 이메일에 이메일 전송
            session.setAttribute("email",userDto.getEmail()); // 세션에 이메일 저장
            session.setAttribute("verificationCode", verificationCode); // 세션에 verificationCode 저장
            return "user/PasswordRecovery/PasswordRecoveryVerificationCode";
        }
        model.addAttribute("userNotFoundError", "이름이나 이메일 입력이 잘못되었습니다.");
        return "user/PasswordRecovery/findUser";
    } // keep

    @PostMapping("/verification-code-submit")
    public String verificationCodeSubmit(@RequestParam("userVerificationCode") String userVerificationCode,
                                         HttpSession session,
                                         Model model) {
        String email = (String) session.getAttribute("email"); // 세션에 저장된 이메일
        Integer verificationCode = (Integer) session.getAttribute("verificationCode"); // 세션에 저장된 인증 코드

        if (verificationCode != null && userVerificationCode.equals(verificationCode.toString())) { // 인증코드와 사용자가 입력한 코드가 같으면
            session.removeAttribute("verificationCode"); //세션에서 인증정보 삭제
            model.addAttribute("newPasswordDto", new NewPasswordDto()); // 비밀번호 설정 객체를 넘긴다.
            return "user/PasswordRecovery/PasswordRecovery";
        } else {
            model.addAttribute("verificationError", "인증 코드가 일치하지 않습니다.");
            return "user/PasswordRecovery/PasswordRecoveryVerificationCode"; // 인증 실패 시 재시도 페이지로 반환
        }

    }

    @PostMapping("/new-password-submit")
    public String newPasswordSubmit(@Valid @ModelAttribute("newPasswordDto") NewPasswordDto newPasswordDto,
                                    BindingResult result,
                                    HttpSession session) {

        if (result.hasErrors()) {
            return "user/PasswordRecovery/PasswordRecovery"; // 유효성 검사 오류 발생 시 다시 입력 폼으로 이동
        }

        newPasswordDto.setEmail((String) session.getAttribute("email")); //이메일을 dto 객체에 저장
        passwordService.updatePassword(newPasswordDto); // 비밀번호 수정 후
        session.removeAttribute("email"); // 이메일 정보를 세션에서 삭제

        return "user/login"; // 비밀번호 업데이트 성공 시 로그인 화면으로 이동
    }
}
