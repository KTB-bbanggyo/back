package com.panggyo.panggyo_backend.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    // 로그인 후 사용자 정보 확인
    @GetMapping("/user/me")
    public Object getUserInfo(@AuthenticationPrincipal OAuth2User oAuth2User) {
        if (oAuth2User == null) {
            return "로그인되지 않았습니다.";
        }
        return oAuth2User.getAttributes(); 
        /*
          예시 반환:
          {
            "provider": "kakao",
            "kakao_id": "123456789",
            "email": "test@kakao.com",
            "nickname": "카카오닉네임",
            "profile_image": "http://k.kakaocdn.net/..."
          }
        */
    }

    // 간단한 홈
    @GetMapping("/")
    public String home() {
        return "메인 페이지";
    }
}
