package com.eleks.academy.pharmagator.controllers;

import com.eleks.academy.pharmagator.entities.Medicine;
import com.eleks.academy.pharmagator.repositories.MedicineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return ResponseEntity.ok(medicineRepository.save(medicine));
    }
}
