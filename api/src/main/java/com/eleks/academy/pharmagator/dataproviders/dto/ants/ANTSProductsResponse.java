package com.eleks.academy.pharmagator.dataproviders.dto.ants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ANTSProductsResponse {
    private Long total;
    private List<ANTSProductsDto> products;
}
