package com.panggyo.panggyo_backend.controller;

import com.panggyo.panggyo_backend.entity.FavoriteBakery;
import com.panggyo.panggyo_backend.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/favorites")
    public List<FavoriteBakery> getFavoriteBakeries(@RequestParam Long userId) {
        return userService.getFavoriteBakeries(userId);
    }
}