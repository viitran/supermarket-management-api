package com.example.supermarketmanagementapi.controller;

import com.example.supermarketmanagementapi.dto.OrderDto;
import com.example.supermarketmanagementapi.model.Account;
import com.example.supermarketmanagementapi.service.IProductOrderService;
import com.example.supermarketmanagementapi.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class ProductOrderController {
    @Autowired
    private IProductOrderService iProductOrderService;
    @Autowired
    private IProductService iProductService;


}
