package com.example.supermarketmanagementapi.dto;

public interface IProductDto {
    String getName();
    Integer getId();
    Integer getQuantityProductOrder();
    byte[] getImage();
    Double getPrice();

}
