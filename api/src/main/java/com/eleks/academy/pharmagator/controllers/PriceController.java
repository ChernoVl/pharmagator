package com.eleks.academy.pharmagator.controllers;

import com.eleks.academy.pharmagator.entities.Price;
import com.eleks.academy.pharmagator.entities.PriceId;
import com.eleks.academy.pharmagator.repositories.MedicineRepository;
import com.eleks.academy.pharmagator.repositories.PharmacyRepository;
import com.eleks.academy.pharmagator.repositories.PriceRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/prices")
public class PriceController {
    private final PriceRepository priceRepository;
    private final MedicineRepository medicineRepository;
    private final PharmacyRepository pharmacyRepository;

    @GetMapping
    public ResponseEntity<List<Price>> getAll() {
        return ResponseEntity.ok(priceRepository.findAll());
    }

    @GetMapping("/{pharmacyId:[\\d]+}/{medicineId:[\\d]+}")
    public ResponseEntity<Price> getById(@PathVariable Long pharmacyId, @PathVariable Long medicineId) {
        return priceRepository.findById(new PriceId(pharmacyId, medicineId))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Price> create(@RequestBody Price price) {
        if (pharmacyRepository.findById(price.getPharmacyId()).isEmpty() ||
                medicineRepository.findById(price.getMedicineId()).isEmpty() ||
                priceRepository.findById(new PriceId(price.getPharmacyId(), price.getMedicineId())).isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok(priceRepository.save(price));
    }

    @PutMapping("/{pharmacyId:[\\d]+}/{medicineId:[\\d]+}")
    public ResponseEntity<Price> update(@PathVariable Long pharmacyId, @PathVariable Long medicineId, @RequestBody Price price) {
        return priceRepository.findById(new PriceId(pharmacyId, medicineId))
                .map(p -> p.getMedicineId() == price.getMedicineId() && p.getPharmacyId() == price.getPharmacyId() ?
                        priceRepository.save(price) :
                        priceRepository.save(p))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{pharmacyId:[\\d]+}/{medicineId:[\\d]+}")
    public ResponseEntity<Price> deleteById(@PathVariable Long pharmacyId, @PathVariable Long medicineId) {
        priceRepository.deleteById(new PriceId(pharmacyId, medicineId));
        return ResponseEntity.noContent().build();
    }
}
