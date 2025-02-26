package com.panggyo.panggyo_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocialProvider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String provider;
    private String providerId;

    @OneToOne(mappedBy = "socialProvider", cascade = CascadeType.ALL)
    private WebtyUser user;

    // 추가: WebtyUser를 nullable하게 받을 수 있는 생성자
    public SocialProvider(String provider, String providerId, WebtyUser user) {
        this.provider = provider;
        this.providerId = providerId;
        this.user = user;
    }
}
