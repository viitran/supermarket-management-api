package com.example.supermarketmanagementapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Data
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date date;
    private Double total;

    @ManyToOne
    @JoinColumn(name = "id_payment_status",referencedColumnName = "id")
    private PaymentStatus paymentStatus;
}
