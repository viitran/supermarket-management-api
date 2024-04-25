package com.example.supermarketmanagementapi.service;

import com.example.supermarketmanagementapi.dto.ResponseDto;
import com.example.supermarketmanagementapi.model.Product;

import java.util.List;

public interface IProductDishService {
    ResponseDto GetAllProductsThatCanBeDishesForId(Integer id);
}
