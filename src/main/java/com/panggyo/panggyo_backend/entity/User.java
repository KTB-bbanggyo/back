package com.panggyo.panggyo_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;

@Entity
@Table(name = "Users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true)
    private Long kakaoId;

    @Column(unique = true)
    private String username;

    private String userPassword;
    private String email;

    private Provider provider;

    private String nickname;
    private String profileImage;
    private String ageRange;
    private Gender gender;

    private String llmThreadId;

    @ManyToMany
    @JoinTable(
            name = "FavoriteBackery",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "bakery_id")
    )
    private Set<Bakery> favoriteBakeries;
}
