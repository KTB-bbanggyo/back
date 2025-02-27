package com.panggyo.panggyo_backend.controller;

import com.panggyo.panggyo_backend.entity.Bakery;
import com.panggyo.panggyo_backend.service.BakeryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bakeries")
public class BakeryController {

    private final BakeryService bakeryService;

    public BakeryController(BakeryService bakeryService) {
        this.bakeryService = bakeryService;
    }

    @GetMapping
    public List<Bakery> getAllBakeries() {
        return bakeryService.getAllBakeries();
    }
}
