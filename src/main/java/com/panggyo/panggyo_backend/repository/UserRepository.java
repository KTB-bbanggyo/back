package com.panggyo.panggyo_backend.repository;

import com.panggyo.panggyo_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}