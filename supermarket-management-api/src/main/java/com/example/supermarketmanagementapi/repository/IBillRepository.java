package com.example.supermarketmanagementapi.repository;

import com.example.supermarketmanagementapi.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IBillRepository extends JpaRepository<Bill,Integer> {
    @Query(value = "select po.* from bill b " +
            " join product_order po on b.id = po.id_bill " +
            " join account a on po.id_account = a.id " +
            " join product p on po.product_id = p.id " +
            " where a.username = :username and b.id_payment_status = 2", nativeQuery = true)
    List<Bill> getAllProductAfterPayment(@Param("username") String username);

}
