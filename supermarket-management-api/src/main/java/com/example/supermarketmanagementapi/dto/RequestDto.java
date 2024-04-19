package com.example.supermarketmanagementapi.dto;

import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
public class RequestDto {
    private Integer page;
    private Integer size;
    private String name;
    private Integer categoryId;
    private String sortBy;
    private Sort.Direction sortDirection;
    private Double priceFrom;
    private Double priceTo;

}
