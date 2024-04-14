package com.example.supermarketmanagementapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String code;
    private String name;
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;
    @Column(columnDefinition = "longtext")
    private String description;
    private Integer quantity;
    private Double price;
    private Date startDate;

    @ManyToOne
    @JoinColumn(name = "id_status",referencedColumnName = "id")
    private Status status;

    @OneToMany(mappedBy = "product")
    @JsonBackReference
    private List<ProductOrder> productOrders;

    @ManyToOne
    @JoinColumn(name = "id_cate", referencedColumnName = "id")
    private Category category;
}
