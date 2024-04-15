package com.example.supermarketmanagementapi.controller;

import com.example.supermarketmanagementapi.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class CategoryController {
    @Autowired
    private ICategoryService iCategoryService;

    @GetMapping("/cate")
    public ResponseEntity<?> showCategories() {
        return new ResponseEntity<>(this.iCategoryService.findAll(), HttpStatus.OK);
    }
}
