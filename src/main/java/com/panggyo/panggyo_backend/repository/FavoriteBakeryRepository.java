package com.panggyo.panggyo_backend.repository;

import com.panggyo.panggyo_backend.entity.FavoriteBakery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteBakeryRepository extends JpaRepository<FavoriteBakery, Long> {
    List<FavoriteBakery> findByUserUserId(Long userId);
}