package com.example.supermarketmanagementapi.service;


import com.example.supermarketmanagementapi.dto.OrderDto;
import com.example.supermarketmanagementapi.dto.PaymentDto;
import com.example.supermarketmanagementapi.dto.RequestDto;
import com.example.supermarketmanagementapi.model.Account;
import com.example.supermarketmanagementapi.model.Bill;
import com.example.supermarketmanagementapi.model.Product;
import com.example.supermarketmanagementapi.model.ProductOrder;
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.util.List;

public interface IProductService {
    List<Product> findNewProduct();

    List<Product> findAllProduct();

    Product findProductById(Integer id);

    List<Product> findProductByCate(Integer id);

    List<ProductOrder> addProductsToCart(OrderDto orderDto, String username);

    void addProductToCart(Product product, Account account);

    Bill addNewBill(String username);

    Page<Product> getAllProductPage(RequestDto requestDto);
}
