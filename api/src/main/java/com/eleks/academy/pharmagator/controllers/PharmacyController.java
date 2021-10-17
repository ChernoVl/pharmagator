package com.eleks.academy.pharmagator.controllers;

import com.eleks.academy.pharmagator.entities.Pharmacy;
import com.eleks.academy.pharmagator.repositories.PharmacyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.mappers.ModelMapper;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/pharmacies")
public class PharmacyController {
    private final PharmacyRepository pharmacyRepository;

    @GetMapping
    public ResponseEntity<List<Pharmacy>> getAll() {
        return ResponseEntity.ok(pharmacyRepository.findAll());
    }

    @GetMapping(value = "/{id:[\\d]+}")
    public ResponseEntity<Pharmacy> getById(@PathVariable Long id) {
        return pharmacyRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Pharmacy> create(@RequestBody Pharmacy pharmacy) {
        return ResponseEntity.ok(pharmacyRepository.save(pharmacy));
    }

    @PutMapping("/{id:[\\d]+}")
    public ResponseEntity<Pharmacy> update(@PathVariable Long id, @RequestBody Pharmacy pharmacy) {
        //FIXME
        return pharmacyRepository.findById(id)
                .map(ph -> pharmacyRepository.save(pharmacy))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id:[\\d]+}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        //FIXME
        pharmacyRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
