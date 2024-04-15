package com.example.supermarketmanagementapi.service.impl;

import com.example.supermarketmanagementapi.model.ProductOrder;
import com.example.supermarketmanagementapi.repository.IProductOrderRepository;
import com.example.supermarketmanagementapi.service.IProductOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductOrderService implements IProductOrderService {
    @Autowired
    private IProductOrderRepository iProductOrderRepository;

    @Override
    public ProductOrder findOrderByIdProduct(Integer id) {
        return this.iProductOrderRepository.findOrderByIdProduct(id);
    }
}
