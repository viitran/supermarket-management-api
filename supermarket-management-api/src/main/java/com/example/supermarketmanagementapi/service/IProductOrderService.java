package com.example.supermarketmanagementapi.service;

import com.example.supermarketmanagementapi.model.ProductOrder;

public interface IProductOrderService {
    ProductOrder findOrderByIdProduct(Integer id);
}
