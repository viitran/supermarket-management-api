package com.example.supermarketmanagementapi.controller;


import com.example.supermarketmanagementapi.dto.OrderDto;
import com.example.supermarketmanagementapi.model.Account;
import com.example.supermarketmanagementapi.model.Product;
import com.example.supermarketmanagementapi.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class ProductController {

    @Autowired
    private IProductService iProductService;

    @GetMapping()
    public ResponseEntity<?> showNewProduct() {
        List<Product> products = this.iProductService.findNewProduct();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/collections")
    public ResponseEntity<?> showAllProduct() {
        List<Product> products = this.iProductService.findAllProduct();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Integer id) {
        Product product = this.iProductService.findProductById(id);
        List<Product> products = this.iProductService.findProductByCate(product.getCategory().getId());
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/product/category/{id}")
    public ResponseEntity<?> getProductByIdCate(@PathVariable Integer id) {
        List<Product> products = this.iProductService.findProductByCate(id);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> addProductToCart(@RequestBody OrderDto orderDto, Account account) {
        if (orderDto.getProductId() == null || orderDto == null || orderDto.getQuantity() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        this.iProductService.addProductsToCart(orderDto, account.getUsername());
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }

//    @GetMapping("/product/{name}")
//    public ResponseEntity<?> findAllProductByNameCategory(Integer id){
//        List<Product> list = this.iProductService.findProductByCate(id);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

}
