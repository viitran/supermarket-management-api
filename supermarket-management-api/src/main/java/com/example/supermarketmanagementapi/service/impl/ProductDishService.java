package com.example.supermarketmanagementapi.service.impl;

import com.example.supermarketmanagementapi.dto.ResponseDto;
import com.example.supermarketmanagementapi.model.Dish;
import com.example.supermarketmanagementapi.model.Product;
import com.example.supermarketmanagementapi.repository.IProductDishRepository;
import com.example.supermarketmanagementapi.repository.IProductRepository;
import com.example.supermarketmanagementapi.service.IProductDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductDishService implements IProductDishService {
    @Autowired
    private IProductDishRepository iProductDishRepository;

    @Autowired
    private IProductRepository productRepository;

    @Override
    public ResponseDto GetAllProductsThatCanBeDishesForId(Integer id) {
        List<Dish> dishes = this.iProductDishRepository.findDishByProductId(id);
        if (dishes.isEmpty()) {
            return new ResponseDto();
        }
        List<Product> products = this.productRepository.findAllByDishId(dishes.get(0).getId());
        ResponseDto responseDto = new ResponseDto();
        responseDto.setProducts(products);
        responseDto.setDish(dishes);
        return responseDto;
    }
}
