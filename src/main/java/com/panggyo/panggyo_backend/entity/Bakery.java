package com.panggyo.panggyo_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Bakery {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bakeryId;

    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private String phone;
    private Double rating;
    private String openingHours;
}