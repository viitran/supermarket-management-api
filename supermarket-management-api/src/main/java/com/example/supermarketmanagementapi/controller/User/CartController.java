package com.example.supermarketmanagementapi.controller.User;

import com.example.supermarketmanagementapi.model.Account;
import com.example.supermarketmanagementapi.service.IProductOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("user")
public class CartController {
    @Autowired
    private IProductOrderService iProductOrderService;

    @GetMapping("/cart")
    public ResponseEntity<?> findAllOrderByUsername(Principal principal){
        return new ResponseEntity<>(this.iProductOrderService.getAllOrderOfUser(principal.getName()),HttpStatus.OK);
    }

    @PostMapping("remove/{id}")
    public ResponseEntity<?> removeProductOnCart(@PathVariable Integer id){
        this.iProductOrderService.removeProductOrder(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

