package com.example.supermarketmanagementapi.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ProductDish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_product", referencedColumnName = "id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "id_dish", referencedColumnName = "id")
    private Dish dish;
}
