package com.example.demo.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @GetMapping("/kakao/callback")
    public String getUserInfo(@RequestParam("code") String authorizationCode, Model model) {
        String accessToken = this.authService.getToken(authorizationCode);
        String userId = this.authService.getUserInfo(accessToken);

        model.addAttribute("userId", userId);

        return "a";
    }
}
