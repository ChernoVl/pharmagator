package com.eleks.academy.pharmagator.scheduler;

import com.eleks.academy.pharmagator.dataproviders.DataProvider;
import com.eleks.academy.pharmagator.dataproviders.dto.MedicineDto;
import com.eleks.academy.pharmagator.entities.Medicine;
import com.eleks.academy.pharmagator.entities.Price;
import com.eleks.academy.pharmagator.repositories.MedicineRepository;
import com.eleks.academy.pharmagator.repositories.PriceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class Scheduler {
    private final List<DataProvider> dataProviderList;
    private final MedicineRepository medicineRepository;
    private final PriceRepository priceRepository;

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void schedule() {
        log.info("Scheduler started at {}", Instant.now());
        dataProviderList.stream().flatMap(DataProvider::loadData).forEach(this::storeToDatabase);
    }

    private void storeToDatabase(MedicineDto dto) {
        medicineRepository.save(
                new Medicine(
                        Long.parseLong(dto.getExternalId()),
                        dto.getTitle()
                ));
        priceRepository.save(
                new Price(
                        1,
                        Long.parseLong(dto.getExternalId()),
                        dto.getPrice(),
                        "localhost",
                        Instant.now()
                ));
    }
}
