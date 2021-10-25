package com.eleks.academy.pharmagator.dataproviders;

import com.eleks.academy.pharmagator.dataproviders.dto.MedicineDto;
import com.eleks.academy.pharmagator.dataproviders.dto.ants.ANTSProductsDto;
import com.eleks.academy.pharmagator.dataproviders.dto.ants.ANTSProductsResponse;
import com.eleks.academy.pharmagator.dataproviders.dto.ants.CategoriesResponse;
import com.eleks.academy.pharmagator.dataproviders.dto.ants.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Qualifier("pharmacyANTSDataProvider")
public class PharmacyDataProvider implements DataProvider {

    private final WebClient dsClient;

    @Value("${pharmagator.data-providers.apteka-ants.category-fetch-url}")
    private String categoriesFetchUrl;


    @Override
    public Stream<MedicineDto> loadData() {
        return this.fetchCategories().stream()
                .filter(categoryDto -> categoryDto.getName().equals("Медикаменти"))
                .map(CategoryDto::getSubcategories)
                .flatMap(Collection::stream)
                .map(CategoryDto::getSubcategories)
                .flatMap(Collection::stream)
                .map(CategoryDto::getLink)
                .flatMap(this::fetchMedicinesBySubcategories);
    }

    private List<CategoryDto> fetchCategories() {
        return this.dsClient
                .get()
                .uri(categoriesFetchUrl)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<CategoriesResponse>() {
                })
                .block()
                .getCategories();
    }

    private Stream<MedicineDto> fetchMedicinesBySubcategories(String subcategory) {
        ANTSProductsResponse antsProductsResponse = this.dsClient
                .get()
                .uri(categoriesFetchUrl + "/" + subcategory)
                .retrieve()
                .bodyToMono(ANTSProductsResponse.class)
                .block();

        if(antsProductsResponse != null){
           return antsProductsResponse.getProducts().stream()
                   .map(this::mapToMedicineDto);
        }
        return Stream.of();
    }

    private MedicineDto mapToMedicineDto(ANTSProductsDto dsMedicineDto) {
        return MedicineDto.builder()
                .externalId(dsMedicineDto.getId())
                .price(dsMedicineDto.getPrice())
                .title(dsMedicineDto.getName())
                .build();
    }
}
