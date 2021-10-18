package com.eleks.academy.pharmagator.controllers;

import com.eleks.academy.pharmagator.entities.Pharmacy;
import com.eleks.academy.pharmagator.entities.PriceId;
import com.eleks.academy.pharmagator.repositories.PharmacyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        if (pharmacyRepository.findById(pharmacy.getId()).isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok(pharmacyRepository.save(pharmacy));
    }

    @PutMapping("/{id:[\\d]+}")
    public ResponseEntity<Pharmacy> update(@PathVariable Long id, @RequestBody Pharmacy pharmacy) {
        //FIXME dont work with 0 id
        return pharmacyRepository.findById(id)
                .map(ph -> ph.getId() == pharmacy.getId() ?
                        pharmacyRepository.save(pharmacy) :
                        pharmacyRepository.save(ph))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id:[\\d]+}")
    public ResponseEntity<Pharmacy> deleteById(@PathVariable Long id) {
        //FIXME dont work with 0 id
        pharmacyRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
