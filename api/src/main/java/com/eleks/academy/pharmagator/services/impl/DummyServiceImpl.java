package com.eleks.academy.pharmagator.services.impl;

import com.eleks.academy.pharmagator.entities.Pharmacy;
import com.eleks.academy.pharmagator.projection.PharmacyLight;
import com.eleks.academy.pharmagator.repositories.PharmacyRepository;
import com.eleks.academy.pharmagator.services.DummyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DummyServiceImpl implements DummyService {

    private final PharmacyRepository pharmacyRepository;

    @Override
    public List<Pharmacy> findAllEven(){
        return filter(p -> p.getId() % 2 == 0)
                .apply(this.pharmacyRepository.findAll());
    }

    @Override
    public List<Pharmacy> findAllOdd() {
        return filter(p -> p.getId() % 2 == 1)
                .apply(this.pharmacyRepository.findAll());
    }

    private UnaryOperator<List<Pharmacy>> filter(Predicate<Pharmacy> predicate){
        return list -> list
                .stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }
}