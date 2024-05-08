package com.example.supermarketmanagementapi.service;

import com.example.supermarketmanagementapi.model.ProductOrder;

import java.util.List;

public interface IProductOrderService {
    ProductOrder findOrderByIdProduct(Integer id,String username);
    List<ProductOrder> getAllOrderOfUser(String username);
    void removeProductOrder(Integer id);
    List<ProductOrder> getHistoryOrder(String username);
    void deleteById(Integer id);
    List<ProductOrder> getQuantityThisProductOnCart(Integer id,String username);
}
