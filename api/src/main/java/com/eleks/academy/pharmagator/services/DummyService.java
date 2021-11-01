package com.eleks.academy.pharmagator.services;

import com.eleks.academy.pharmagator.entities.Pharmacy;
import com.eleks.academy.pharmagator.projection.PharmacyLight;

import java.util.List;

public interface DummyService {

    List<Pharmacy> findAllEven();

    List<Pharmacy> findAllOdd();
}
