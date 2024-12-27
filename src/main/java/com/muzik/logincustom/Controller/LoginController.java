package com.muzik.logincustom.Controller;

import com.muzik.logincustom.DTO.LoginDTO;
import com.muzik.logincustom.Service.LoginService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    // 시작페이지(모든 사용자 권한)
    @GetMapping({"/", "/index"})
    public String indexForm() {
        return "index";
    }

    // 결과페이지(모든 사용자 권한)
    @GetMapping("/result")
    public String resultForm() {
        return "result";
    }

    // 유저페이지(사용자 권한)
    @GetMapping("/user")
    public String userForm() {
        return "user";
    }

    // 관리자페이지(관리자 권한)
    @GetMapping("/admin")
    public String adminForm() {
        return "admin";
    }

    // 로그인페이지로 이동
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // 로그아웃 처리
    // 사용자 로그아웃을 작업할 때 반드시 섹션을 제거하는 부분을 추가
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();       // 세션에서 로그인 정보를 제거
        return "redirect:/login";
    }

    // 회원가입(가입폼 -> 가입처리)
    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerProc(LoginDTO loginDTO) {

        loginService.saveUser(loginDTO);

        return "register";
    }

}