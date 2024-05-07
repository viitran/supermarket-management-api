package com.example.supermarketmanagementapi.controller.User;

import com.example.supermarketmanagementapi.dto.OrderDto;
import com.example.supermarketmanagementapi.model.Account;
import com.example.supermarketmanagementapi.model.ProductOrder;
import com.example.supermarketmanagementapi.repository.IProductOrderRepository;
import com.example.supermarketmanagementapi.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("user")
public class UserProductController {
    @Autowired
    private IProductService iProductService;

    @Autowired
    IProductOrderRepository iProductOrderRepository;

    @PostMapping()
    public ResponseEntity<?> addProductToCart(@RequestBody OrderDto orderDto, Principal principal) {
        if (orderDto.getProductId() == null || orderDto == null || orderDto.getQuantity() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        this.iProductService.addProductsToCart(orderDto, principal.getName());
        System.out.println(principal.getName() + "them sp vao gio hang thanh cong");
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }

}
