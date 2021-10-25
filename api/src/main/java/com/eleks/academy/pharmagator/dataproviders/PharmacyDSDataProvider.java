package com.eleks.academy.pharmagator.dataproviders;

import com.eleks.academy.pharmagator.dataproviders.dto.MedicineDto;
import com.eleks.academy.pharmagator.dataproviders.dto.ds.CategoryDto;
import com.eleks.academy.pharmagator.dataproviders.dto.ds.DSMedicineDto;
import com.eleks.academy.pharmagator.dataproviders.dto.ds.DSMedicineResponse;
import com.eleks.academy.pharmagator.dataproviders.dto.ds.FilterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
// каже спрінгу що цей бін має конкретне імя, і по цьому імені можна до нього звертатись
// І ми маємо теж позначити Кваліфаєром цей бін який конкретно ми хочемо, або використати список провайдерів
@Qualifier("pharmacyDSDataProvider")
public class PharmacyDSDataProvider implements DataProvider {

    //В нас буде конструкор з цим полем и Спринг сюда нам заинжектыть наш бин, якшо його знайде
    // А знайде вын його в класы DataProviderConfig
    private final WebClient dsClient;

    @Value("${pharmagator.data-providers.apteka-ds.category-fetch-url}")
    private String categoriesFetchUrl;

    @Value("${pharmagator.data-providers.apteka-ds.category-path}")
    private String categoryPath;

    @Override
    public Stream<MedicineDto> loadData() {
        return this.fetchCategories().stream()
                .filter(categoryDto -> categoryDto.getName().equals("Медикаменти"))
                .flatMap(categoryDto -> categoryDto.getChildren().stream())
                .flatMap(categoryDto -> fetchMedicinesByCategory(categoryDto.getSlug()));
                //.map(CategoryDto::getSlug)
                //.flatMap(this::fetchMedicinesByCategory);
    }

    private List<CategoryDto> fetchCategories() {
        return this.dsClient
                .get()
                .uri(categoriesFetchUrl)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<CategoryDto>>() {
                })
                .block();
    }

    private Stream<MedicineDto> fetchMedicinesByCategory(String category) {
        Long pageSize = 100L;
        Long page = 1L;
        DSMedicineResponse dsMedicineResponse = this.dsClient
                .post()
                .uri(categoryPath + "/" + category)
                .body(Mono.just(FilterRequest.builder()
                        .page(page)
                        .per(pageSize)
                        .build()), FilterRequest.class)
                .retrieve()
                .bodyToMono(DSMedicineResponse.class)
                .block();
        Long total = dsMedicineResponse.getTotal();
        Long pageCount = total / pageSize;

        if(dsMedicineResponse !=null) {
            List<DSMedicineResponse> dsMedicineResponseList = new ArrayList<>();
            while (page <= pageCount) {
                dsMedicineResponseList.add(
                        this.dsClient
                                .post()
                                .uri(categoryPath + "/" + category)
                                .body(Mono.just(FilterRequest.builder()
                                        .page(page)
                                        .per(pageSize)
                                        .build()), FilterRequest.class)
                                .retrieve()
                                .bodyToMono(DSMedicineResponse.class)
                                .block());
                page++;
            }

            return dsMedicineResponseList.stream()
                    .map(DSMedicineResponse::getProducts)
                    .flatMap(Collection::stream)
                    .map(this::mapToMedicineDto);
        }
        return Stream.of();
    }

    private MedicineDto mapToMedicineDto(DSMedicineDto dsMedicineDto) {
        return MedicineDto.builder()
                .externalId(dsMedicineDto.getId())
                .price(dsMedicineDto.getPrice())
                .title(dsMedicineDto.getName())
                .build();
    }
}
