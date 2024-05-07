package com.example.supermarketmanagementapi.service.impl;

import com.example.supermarketmanagementapi.model.ProductOrder;
import com.example.supermarketmanagementapi.repository.IProductOrderRepository;
import com.example.supermarketmanagementapi.service.IProductOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductOrderService implements IProductOrderService {
    @Autowired
    private IProductOrderRepository iProductOrderRepository;

    @Override
    public ProductOrder findOrderByIdProduct(Integer id,String username) {
        return this.iProductOrderRepository.findOrderByIdProductOrUsername(id,username);
    }

    @Override
    public List<ProductOrder> getAllOrderOfUser(String username) {
        return this.iProductOrderRepository.getAllProductOrderOfUser(username);
    }

    @Override
    public void removeProductOrder(Integer id) {
        this.iProductOrderRepository.removeProductOrderOfUser(id);
    }

    @Override
    public List<ProductOrder> getHistoryOrder(String username) {
        return this.iProductOrderRepository.getOrderHistoryByUsername(username);
    }


}
