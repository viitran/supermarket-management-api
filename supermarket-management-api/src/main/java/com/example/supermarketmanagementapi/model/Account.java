package com.example.supermarketmanagementapi.model;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fullName;
    private String address;
    private String email;
    private String phoneNumber;
    private String username;
    private String password;
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "id_role",referencedColumnName = "id")
    private Role role;


}
