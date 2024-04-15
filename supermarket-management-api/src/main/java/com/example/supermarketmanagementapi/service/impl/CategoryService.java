package com.example.supermarketmanagementapi.service.impl;


import com.example.supermarketmanagementapi.model.Category;
import com.example.supermarketmanagementapi.repository.ICategoryRepository;
import com.example.supermarketmanagementapi.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private ICategoryRepository iCategoryRepository;
    @Override
    public List<Category> findAll() {
        return this.iCategoryRepository.getAllCate();
    }
}
