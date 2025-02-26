package main.java.com.panggyo.panggyo_backend.service;

import com.panggyo.panggyo_backend.entity.Bakery;
import com.panggyo.panggyo_backend.repository.BakeryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BakeryService {
    private final BakeryRepository bakeryRepository;

    public Optional<Bakery> getBakeryById(Long bakeryId) {
        return bakeryRepository.findById(bakeryId);
    }
}