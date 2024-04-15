package com.example.supermarketmanagementapi.repository;

import com.example.supermarketmanagementapi.model.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IProductOrderRepository extends JpaRepository<ProductOrder, Integer> {
    @Query(value = "select * from product_order where id=:id", nativeQuery = true)
    ProductOrder findOrderByIdProduct(@Param("id") Integer id);

}
