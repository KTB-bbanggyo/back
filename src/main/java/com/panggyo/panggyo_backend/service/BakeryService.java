package com.panggyo.panggyo_backend.service;

import com.panggyo.panggyo_backend.entity.Bakery;
import com.panggyo.panggyo_backend.repository.BakeryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BakeryService {
    
    private final BakeryRepository bakeryRepository;

    public BakeryService(BakeryRepository bakeryRepository) {
        this.bakeryRepository = bakeryRepository;
    }

    public List<Bakery> getAllBakeries() {
        return bakeryRepository.findAll();
    }
}