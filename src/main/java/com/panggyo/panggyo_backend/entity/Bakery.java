package main.java.com.panggyo.panggyo_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bakery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private String phone;
    private int rating;

    @ElementCollection
    private List<String> tags;

    @OneToMany(mappedBy = "bakery", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews;
}