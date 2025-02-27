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
}
