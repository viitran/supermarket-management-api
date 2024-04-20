package com.example.supermarketmanagementapi.repository;

import com.example.supermarketmanagementapi.model.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IProductOrderRepository extends JpaRepository<ProductOrder, Integer> {
    @Query(value = "select po.* from product_order as po left join account as a on po.id_account = a.id " +
            " left join product as p on po.product_id = p.id " +
            " where a.username = :username and p.id = :id and po.id_bill is null and po.is_delete = 0", nativeQuery = true)
    ProductOrder findOrderByIdProductOrUsername(@Param("id") Integer id, String username);

    @Query(value = "select po.* from product_order as po left join account as acc on po.id_account = acc.id " +
            "where acc.username = :username and po.id_bill is null and po.is_delete = 0", nativeQuery = true)
    List<ProductOrder> getAllProductOrderOfUser(@Param("username") String username);

    @Transactional
    @Modifying
    @Query(value = "update product_order set is_delete = 1 where id = :id", nativeQuery = true)
    void removeProductOrderOfUser(Integer id);
}
