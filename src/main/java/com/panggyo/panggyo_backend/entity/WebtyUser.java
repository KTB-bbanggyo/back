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
}
