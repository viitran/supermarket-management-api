package com.example.supermarketmanagementapi.service;



import com.example.supermarketmanagementapi.model.Product;

import java.util.List;

public interface IProductService {
    List<Product> findNewProduct();
    List<Product> findAllProduct();
    Product findProductById(Integer id);
    List<Product> findProductByCate(Integer id);
}
