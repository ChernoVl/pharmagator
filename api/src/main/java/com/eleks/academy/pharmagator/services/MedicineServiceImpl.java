package com.eleks.academy.pharmagator.services;

import com.eleks.academy.pharmagator.entities.Medicine;
import com.eleks.academy.pharmagator.repositories.MedicineRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MedicineServiceImpl implements MedicineService {
    private final MedicineRepository medicineRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<Medicine> findAll() {
        return medicineRepository.findAll();
    }

    @Override
    public Optional<Medicine> findById(Long id) {
        return medicineRepository.findById(id);
    }

    @Override
    public Medicine save(Medicine medicine) {
        Medicine m = modelMapper.map(medicine, Medicine.class);
        return medicineRepository.save(m);
    }

    @Override
    public Optional<Medicine> update(Long id, Medicine medicine) {
        return medicineRepository.findById(id)
                .map(source -> {
                    Medicine m = modelMapper.map(medicine, Medicine.class);
                    m.setId(id);
                    medicineRepository.save(m);
                    return m;
                });
    }

    @Override
    public void deleteById(Long id) {
        medicineRepository.deleteById(id);
    }
}