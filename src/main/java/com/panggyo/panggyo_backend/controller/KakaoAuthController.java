package com.panggyo.panggyo_backend.controller;

import com.panggyo.panggyo_backend.service.KakaoOAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth/kakao")
public class KakaoAuthController {

    private final KakaoOAuthService kakaoOAuthService;

    public KakaoAuthController(KakaoOAuthService kakaoOAuthService) {
        this.kakaoOAuthService = kakaoOAuthService;
    }

    @GetMapping("/login")
    public ResponseEntity<Map<String, String>> getLoginUrl() {
        return ResponseEntity.ok(Map.of("login_url", kakaoOAuthService.getKakaoLoginUrl()));
    }

    @GetMapping("/callback")
    public ResponseEntity<?> kakaoCallback(@RequestParam String code) {
        String accessToken = kakaoOAuthService.getAccessToken(code);
        return accessToken != null 
            ? ResponseEntity.ok(Map.of("access_token", accessToken)) 
            : ResponseEntity.status(401).body(Map.of("error", "카카오 인증 실패"));
    }
}
