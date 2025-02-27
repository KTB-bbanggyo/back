package com.panggyo.panggyo_backend.repository;

import com.panggyo.panggyo_backend.entity.Bakery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BakeryRepository extends JpaRepository<Bakery, Long> {
}
