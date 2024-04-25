package com.example.supermarketmanagementapi.controller.Public;

import com.example.supermarketmanagementapi.dto.ResponseDto;
import com.example.supermarketmanagementapi.model.Product;
import com.example.supermarketmanagementapi.service.IProductDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("public")
public class RecommendedDishesController {

    @Autowired
    private IProductDishService iProductDishService;

    @GetMapping("/product-dish/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Integer id) {
        ResponseDto responseDto = this.iProductDishService.GetAllProductsThatCanBeDishesForId(id);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
