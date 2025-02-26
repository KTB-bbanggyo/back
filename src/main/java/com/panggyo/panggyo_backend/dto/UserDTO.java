package main.java.com.panggyo.panggyo_backend.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String nickname;
    private String profileImage;
    private int age;
    private String gender;
}