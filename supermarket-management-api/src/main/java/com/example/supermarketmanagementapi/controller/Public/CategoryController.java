package com.example.supermarketmanagementapi.controller.Public;

import com.example.supermarketmanagementapi.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("public")
public class CategoryController {
    @Autowired
    private ICategoryService iCategoryService;

    @GetMapping("/cate")
    public ResponseEntity<?> showCategories() {
        return new ResponseEntity<>(this.iCategoryService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/cate/{id}")
    public ResponseEntity<?> findCate(@PathVariable Integer id){
        return new ResponseEntity<>(this.iCategoryService.findCategoryById(id),HttpStatus.OK);
    }
}
