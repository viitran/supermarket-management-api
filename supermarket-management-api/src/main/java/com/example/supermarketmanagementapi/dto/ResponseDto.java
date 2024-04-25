package com.example.supermarketmanagementapi.dto;

import com.example.supermarketmanagementapi.model.Dish;
import com.example.supermarketmanagementapi.model.Product;
import lombok.Data;

import java.util.List;

@Data
public class ResponseDto {

    List<Product> products;

    List<Dish> dish;
}
