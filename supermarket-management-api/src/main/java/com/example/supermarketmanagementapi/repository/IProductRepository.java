package com.example.supermarketmanagementapi.repository;

import com.example.supermarketmanagementapi.dto.OrderDto;
import com.example.supermarketmanagementapi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IProductRepository extends JpaRepository<Product, Integer> {
    @Query(value = "select *, FORMAT(price, 0)  from product order by start_date desc limit 8", nativeQuery = true)
    List<Product> getNewProduct();

    @Query(value = "select *,FORMAT(price, 0) from product",nativeQuery = true)
    List<Product> getAllProduct();

    @Query(value = "select * from product as p where p.id = :id",nativeQuery = true)
    Product findProductById(@Param("id") Integer id);

    @Query(value = "select p.* from product as p" +
            " left join category as c on p.id_cate = :id " +
            "where c.id = :id ",nativeQuery = true)
    List<Product> findProductByCate(@Param("id") Integer id);


}
