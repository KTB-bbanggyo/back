package com.panggyo.panggyo_backend.service;

import com.panggyo.panggyo_backend.entity.User;
import com.panggyo.panggyo_backend.entity.FavoriteBakery;
import com.panggyo.panggyo_backend.repository.UserRepository;
import com.panggyo.panggyo_backend.repository.FavoriteBakeryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final FavoriteBakeryRepository favoriteBakeryRepository;

    public UserService(UserRepository userRepository, FavoriteBakeryRepository favoriteBakeryRepository) {
        this.userRepository = userRepository;
        this.favoriteBakeryRepository = favoriteBakeryRepository;
    }

    public List<FavoriteBakery> getFavoriteBakeries(Long userId) {
        return favoriteBakeryRepository.findByUserUserId(userId);
    }
}