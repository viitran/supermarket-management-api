package com.example.supermarketmanagementapi.repository;

import com.example.supermarketmanagementapi.dto.IProductDto;
import com.example.supermarketmanagementapi.dto.OrderDto;
import com.example.supermarketmanagementapi.dto.RequestDto;
import com.example.supermarketmanagementapi.model.Bill;
import com.example.supermarketmanagementapi.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IProductRepository extends JpaRepository<Product, Integer> {
    @Query(value = "select *, FORMAT(price, 0)  from product order by start_date desc limit 8", nativeQuery = true)
    List<Product> getNewProduct();

    @Query(value = "select *,FORMAT(price, 0) from product", nativeQuery = true)
    List<Product> getAllProduct();

    @Query(value = "select * from product as p where p.id = :id", nativeQuery = true)
    Product findProductById(@Param("id") Integer id);

    @Query(value = "select p.* from product as p" +
            " left join category as c on p.id_cate = :id " +
            "where c.id = :id ", nativeQuery = true)
    List<Product> findProductByCate(@Param("id") Integer id);

    @Query(value = "select p.* from product as p " +
            "join category as c on p.id_cate = c.id " +
            "where lower(p.name)  collate utf8mb4_bin like lower(concat('%',:name,'%')) and " +
            "((p.price between :priceFrom and :priceTo) or :priceFrom is null or :priceTo is null) " +
            "and (p.id_cate = :categoryId or :categoryId = -1)", nativeQuery = true)
    Page<Product> getAllPageProduct(Pageable pageable, @Param("name") String name,
                                    @Param("priceFrom") Double priceFrom,
                                    @Param("priceTo") Double priceTo,
                                    @Param("categoryId") Integer categoryId);

    @Query(value = " select p from Product p " +
            "join ProductDish pd on p.id = pd.product.id " +
            "join Dish d on d.id = pd.dish.id where d.id = :id")
    List<Product> findAllByDishId(@Param("id") Integer id);

    @Query(value = "select p.*,sum(po.quantity) as quantityProductOrder " +
            "from product_order as po " +
            "left join product as p on po.product_id = p.id " +
            "left join category as c on p.id_cate = c.id " +
            "group by p.id ,p.name order by quantityProductOrder desc limit 8", nativeQuery = true)
    List<IProductDto> findTopSelling();



}
