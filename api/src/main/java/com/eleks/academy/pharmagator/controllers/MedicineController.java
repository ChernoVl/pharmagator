package com.eleks.academy.pharmagator.controllers;

import com.eleks.academy.pharmagator.entities.Medicine;
import com.eleks.academy.pharmagator.repositories.MedicineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/medicines")
public class MedicineController {
    private final MedicineRepository medicineRepository;

    @GetMapping
    public ResponseEntity<List<Medicine>> getAll(){
        return ResponseEntity.ok(medicineRepository.findAll());
    }

    @GetMapping("/{id:[\\d]+}")
    public ResponseEntity<Medicine> getById(@PathVariable Long id){
        return medicineRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Medicine> create(@RequestBody Medicine medicine){
        if (medicineRepository.findById(medicine.getId()).isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok(medicineRepository.save(medicine));
    }

    @PutMapping("/{id:[\\d]+}")
    public ResponseEntity<Medicine> update(@PathVariable Long id, @RequestBody Medicine medicine){
        return medicineRepository.findById(id)
                .map(m -> m.getId() == medicine.getId() ?
                        medicineRepository.save(medicine) :
                        medicineRepository.save(m))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id:[\\d]+}")
    public ResponseEntity<Medicine> deleteById(@PathVariable Long id){
        medicineRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
