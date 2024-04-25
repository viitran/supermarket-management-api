package com.example.supermarketmanagementapi.repository;

import com.example.supermarketmanagementapi.model.Dish;
import com.example.supermarketmanagementapi.model.ProductDish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface IProductDishRepository extends JpaRepository<ProductDish,Integer> {

    @Query(value = "select d from Dish d " +
            "join ProductDish pd on d.id = pd.dish.id " +
            "join Product p on p.id = pd.product.id where p.id = :id")
    List<Dish> findDishByProductId(@Param("id") Integer id);
}
