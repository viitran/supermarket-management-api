package com.example.supermarketmanagementapi.service;


import com.example.supermarketmanagementapi.model.Product;
import com.example.supermarketmanagementapi.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService{
    @Autowired
    private IProductRepository iProductRepository;

    @Override
    public List<Product> findNewProduct() {
        return this.iProductRepository.getNewProduct();
    }

    @Override
    public List<Product> findAllProduct() {
        return this.iProductRepository.getAllProduct();
    }

    @Override
    public Product findProductById(Integer id) {
        return this.iProductRepository.findProductById(id);
    }

    @Override
    public List<Product> findProductByCate(Integer id) {
        return this.iProductRepository.findProductByCate(id);
    }
}
