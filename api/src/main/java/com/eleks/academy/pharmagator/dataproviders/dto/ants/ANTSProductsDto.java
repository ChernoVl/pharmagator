package com.eleks.academy.pharmagator.dataproviders.dto.ants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ANTSProductsDto {
    private String id;
    private String name;
    private BigDecimal price;
}
