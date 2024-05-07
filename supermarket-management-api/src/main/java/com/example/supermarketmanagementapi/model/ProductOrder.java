package com.example.supermarketmanagementapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class ProductOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date date;
    private Integer quantity;
    @Column(columnDefinition = "bit(1) default 0")
    private boolean isDelete;

    @ManyToOne
    @JoinColumn(name = "id_account", referencedColumnName = "id")
    private Account account;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "id_bill",referencedColumnName = "id")
    private Bill bill;

}
