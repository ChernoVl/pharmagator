package com.eleks.academy.pharmagator.dataproviders.dto.ants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriesResponse {
    private Long total;
    private List<CategoryDto> categories;
}
