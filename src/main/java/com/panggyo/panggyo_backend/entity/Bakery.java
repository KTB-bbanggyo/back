package com.panggyo.panggyo_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.panggyo.panggyo_backend.converter.ListToStringConverter;
import java.util.List;

@Entity
@Table(name = "Bakery")
@Getter
@Setter
@NoArgsConstructor
public class Bakery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bakeryId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, precision = 10, scale = 7)
    private Double latitude;

    @Column(nullable = false, precision = 10, scale = 7)
    private Double longitude;

    private String phone;

    @Column(precision = 3, scale = 2)
    private Double rating;

    private String openingHours;

    @Convert(converter = ListToStringConverter.class)
    @Column(columnDefinition = "JSON")
    private List<String> keywords;
}
