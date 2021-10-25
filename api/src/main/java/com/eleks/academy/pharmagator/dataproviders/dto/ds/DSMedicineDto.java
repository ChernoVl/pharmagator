package com.eleks.academy.pharmagator.dataproviders.dto.ds;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DSMedicineDto {
    private String id;
    private String name;
    //@JsonProperty("ціна")
    private BigDecimal price;
    private String manufacture;
}
