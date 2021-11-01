package com.eleks.academy.pharmagator.services;

import com.eleks.academy.pharmagator.entities.Pharmacy;

import java.util.List;
import java.util.Optional;

public interface PharmacyService {

    List<Pharmacy> findAll();

    Optional<Pharmacy> findById(Long id);

    Pharmacy save(Pharmacy pharmacy);

    Optional<Pharmacy> update(Long id, Pharmacy pharmacyInput);

    void deleteById(Long id);

}
