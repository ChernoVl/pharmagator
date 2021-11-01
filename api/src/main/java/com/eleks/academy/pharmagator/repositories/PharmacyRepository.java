package com.eleks.academy.pharmagator.repositories;

import com.eleks.academy.pharmagator.entities.Pharmacy;
import com.eleks.academy.pharmagator.projection.PharmacyLight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {
    // 1 варіант використання проекцій
    @Override
    List<Pharmacy> findAll();

    // 2 варіант повртати клас
    @Query("SELECT pharmacy FROM Pharmacy pharmacy")
    List<PharmacyLight> findAllLight();
}
