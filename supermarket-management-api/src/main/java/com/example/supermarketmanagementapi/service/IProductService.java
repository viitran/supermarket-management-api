package com.example.supermarketmanagementapi.service;


import com.example.supermarketmanagementapi.dto.IProductDto;
import com.example.supermarketmanagementapi.dto.OrderDto;
import com.example.supermarketmanagementapi.dto.PaymentDto;
import com.example.supermarketmanagementapi.dto.RequestDto;
import com.example.supermarketmanagementapi.model.Account;
import com.example.supermarketmanagementapi.model.Bill;
import com.example.supermarketmanagementapi.model.Product;
import com.example.supermarketmanagementapi.model.ProductOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.List;

public interface IProductService {
    List<Product> findNewProduct();

    List<Product> findAllProduct();

    Product findProductById(Integer id);

    List<Product> findProductByCate(Integer id);

    List<ProductOrder> addProductsToCart(OrderDto orderDto, String username);

    void addProductToCart(Product product, Account account);

    ResponseEntity<?> addNewBill(String username,String address,String message);

    List<IProductDto> findAllProductsTopSelling();
    Page<Product> getAllProduct(Pageable pageable,String name,Double priceFrom,Double priceTo,Integer categoryId);

}
