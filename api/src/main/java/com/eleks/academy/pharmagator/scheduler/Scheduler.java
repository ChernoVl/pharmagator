package com.eleks.academy.pharmagator.scheduler;

import com.eleks.academy.pharmagator.dataproviders.DataProvider;
import com.eleks.academy.pharmagator.dataproviders.dto.MedicineDto;
import com.eleks.academy.pharmagator.entities.Medicine;
import com.eleks.academy.pharmagator.entities.Pharmacy;
import com.eleks.academy.pharmagator.entities.Price;
import com.eleks.academy.pharmagator.repositories.MedicineRepository;
import com.eleks.academy.pharmagator.repositories.PharmacyRepository;
import com.eleks.academy.pharmagator.repositories.PriceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@Profile("!test")
@RequiredArgsConstructor
public class Scheduler {

    private final List<DataProvider> dataProviderList;

    private final MedicineRepository medicineRepository;
    private final PharmacyRepository pharmacyRepository;
    private final PriceRepository priceRepository;

    private final ModelMapper modelMapper;

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void schedule() {
        log.info("Scheduler started at {}", Instant.now());
        dataProviderList.stream()
                .flatMap(DataProvider::loadData)
                .forEach(this::storeToDatabase);
    }

    private void storeToDatabase(MedicineDto dto) {
        log.info(dto.getTitle() + " - " + dto.getPrice());

        Medicine medicine = modelMapper.map(dto, Medicine.class);
        String medicineTitle = medicine.getTitle();
        Medicine medicineDB = medicineRepository.findByTitle(medicineTitle)
                .orElseGet(() -> medicineRepository.save(medicine));

        String pharmacyName = dto.getPharmacyName();
        Pharmacy pharmacyDB = pharmacyRepository.findByName(pharmacyName)
                .orElseGet(() -> {
                    Pharmacy pharmacy = new Pharmacy();
                    pharmacy.setName(pharmacyName);
                    return pharmacyRepository.save(pharmacy);
                });

        Price price = modelMapper.map(dto, Price.class);
        price.setMedicineId(medicineDB.getId());
        price.setPharmacyId(pharmacyDB.getId());
        price.setUpdatedAt(Instant.now());
        price.setExternalId(dto.getExternalId());
        priceRepository.save(price);

    }
}
