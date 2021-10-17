package com.eleks.academy.pharmagator.controllers;

import com.eleks.academy.pharmagator.entities.Price;
import com.eleks.academy.pharmagator.entities.PriceId;
import com.eleks.academy.pharmagator.repositories.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("prices")
public class PriceController {
    private final PriceRepository priceRepository;

    @GetMapping
    public ResponseEntity<List<Price>> getAll(){
        return ResponseEntity.ok(priceRepository.findAll());
    }

    @GetMapping("/{pharmacyId:[\\d]+}/{medicineId:[\\d]+}")
    public ResponseEntity<Price> getById(@PathVariable Long pharmacyId, @PathVariable Long medicineId){
        return priceRepository.findById(new PriceId(pharmacyId, medicineId))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

//    @PostMapping
//    public ResponseEntity<Price> create(@RequestBody Price price){
//
//    }
}
