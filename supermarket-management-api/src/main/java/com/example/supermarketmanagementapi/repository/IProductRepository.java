package com.example.supermarketmanagementapi.repository;

import com.example.supermarketmanagementapi.dto.IProductDto;
import com.example.supermarketmanagementapi.dto.OrderDto;
import com.example.supermarketmanagementapi.dto.RequestDto;
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

    @Query(value = "select p from Product as p " +
            "join Category as c on p.category.id = c.id " +
            "where p.name like concat('%', :#{#requestDto.name} ,'%') and " +
            "((p.price between :#{#requestDto.priceFrom} and :#{#requestDto.priceTo}) or :#{#requestDto.priceFrom} is null or :#{#requestDto.priceTo} is null ) " +
            "and (p.category.id = :#{#requestDto.categoryId} or :#{#requestDto.categoryId} = -1)")
    Page<Product> findAllPageProduct(Pageable pageable, @Param("requestDto") RequestDto requestDto);


    @Query(value = " select p from Product p " +
            "join ProductDish pd on p.id = pd.product.id " +
            "join Dish d on d.id = pd.dish.id where d.id = :id")
    List<Product> findAllByDishId(@Param("id") Integer id);

    @Query(value ="select p.*,sum(po.quantity) as quantityProductOrder " +
            "from product_order as po " +
            "left join product as p on po.product_id = p.id " +
            "left join category as c on p.id_cate = c.id " +
            "group by p.id ,p.name order by quantityProductOrder desc limit 8",nativeQuery = true)
    List<IProductDto> findTopSelling();
}
