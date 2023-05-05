package com.example.demo.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @GetMapping("/kakao/callback")
    public ResponseEntity<String> getUserInfo(@RequestParam("code") String authorizationCode) {
        return this.authService.getUserInfo(authorizationCode);
    }
}
