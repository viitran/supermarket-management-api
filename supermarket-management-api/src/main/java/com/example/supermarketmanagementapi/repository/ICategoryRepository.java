package com.example.supermarketmanagementapi.repository;


import com.example.supermarketmanagementapi.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICategoryRepository extends JpaRepository<Category, Integer> {
    @Query(value = "select * from category", nativeQuery = true)
    List<Category> getAllCate();

    @Query(value = "select * from category where id = :id",nativeQuery = true)
    Category findCateById(@Param("id") Integer id);

//    @Query(value = "select * from product ")
//    List<Category> getAllProductsByCategoryId(@Param("id") Integer id);
}
