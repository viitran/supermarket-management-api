package com.example.supermarketmanagementapi.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PaymentDto implements Serializable {
    private String status;
    private String message;
    private String url;

}
