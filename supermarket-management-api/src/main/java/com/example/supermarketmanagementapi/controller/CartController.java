package com.example.supermarketmanagementapi.controller;

import com.example.supermarketmanagementapi.model.Account;
import com.example.supermarketmanagementapi.service.IProductOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@CrossOrigin("*")
public class CartController {
    @Autowired
    private IProductOrderService iProductOrderService;

    @GetMapping("/cart")
    public ResponseEntity<?> findAllOrderByUsername(){
        return new ResponseEntity<>(this.iProductOrderService.getAllOrderOfUser("vtran123"),HttpStatus.OK);
    }
}
