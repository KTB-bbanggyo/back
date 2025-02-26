package com.panggyo.panggyo_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WebtyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String nickname;

    @OneToOne
    @JoinColumn(name = "social_provider_id")
    private SocialProvider socialProvider;

    // 추가: String, SocialProvider 인자를 받는 생성자 추가
    public WebtyUser(String nickname, SocialProvider socialProvider) {
        this.nickname = nickname;
        this.socialProvider = socialProvider;
    }
}
