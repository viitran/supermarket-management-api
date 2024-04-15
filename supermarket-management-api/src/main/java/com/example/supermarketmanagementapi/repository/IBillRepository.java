package com.example.supermarketmanagementapi.repository;

import com.example.supermarketmanagementapi.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBillRepository extends JpaRepository<Bill,Integer> {
}
