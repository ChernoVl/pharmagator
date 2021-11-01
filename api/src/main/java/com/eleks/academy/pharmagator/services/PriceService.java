package com.eleks.academy.pharmagator.services;

import com.eleks.academy.pharmagator.entities.Price;

import java.util.List;
import java.util.Optional;

public interface PriceService {

    List<Price> findAll();

    Optional<Price> findById(Long pharmacyId, Long medicineId);

    Price save(Price price);

    Optional<Price> update(Long pharmacyId, Long medicineId, Price price);

    void deleteById(Long pharmacyId, Long medicineId);
    
}
