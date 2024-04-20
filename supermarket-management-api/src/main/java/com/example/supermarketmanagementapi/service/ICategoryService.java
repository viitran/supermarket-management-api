package com.example.supermarketmanagementapi.service;


import com.example.supermarketmanagementapi.model.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> findAll();
    Category findCategoryById(Integer id);
}
