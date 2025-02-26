package main.java.com.panggyo.panggyo_backend.dto;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BakeryDTO {
    private Long id;
    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private String phone;
    private int rating;
    private List<String> tags;
}