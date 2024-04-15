package com.example.supermarketmanagementapi.service;



import com.example.supermarketmanagementapi.dto.OrderDto;
import com.example.supermarketmanagementapi.model.Account;
import com.example.supermarketmanagementapi.model.Product;
import com.example.supermarketmanagementapi.model.ProductOrder;

import java.util.List;

public interface IProductService {
    List<Product> findNewProduct();
    List<Product> findAllProduct();
    Product findProductById(Integer id);
    List<Product> findProductByCate(Integer id);
    List<ProductOrder> addProductsToCart(OrderDto orderDto,String username);
    void addProductToCart(Product product, Account account);
}
