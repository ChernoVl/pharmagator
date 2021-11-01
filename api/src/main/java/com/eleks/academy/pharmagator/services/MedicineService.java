package com.eleks.academy.pharmagator.services;

import com.eleks.academy.pharmagator.entities.Medicine;

import java.util.List;
import java.util.Optional;

public interface MedicineService {
    List<Medicine> findAll();

    Optional<Medicine> findById(Long id);

    Medicine save(Medicine medicine);

    Optional<Medicine> update(Long id, Medicine medicine);

    void deleteById(Long id);
}