package com.example.supermarketmanagementapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;
    @OneToMany(mappedBy = "category")
    @JsonBackReference
    private List<Product> product;


}
