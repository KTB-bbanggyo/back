package com.panggyo.panggyo_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "FavoriteBackery")
@Getter
@Setter
@NoArgsConstructor
public class FavoriteBakery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "bakery_id", nullable = false)
    private Bakery bakery;
}
