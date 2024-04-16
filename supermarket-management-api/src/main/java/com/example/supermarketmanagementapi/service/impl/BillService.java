package com.example.supermarketmanagementapi.service.impl;

import com.example.supermarketmanagementapi.repository.IBillRepository;
import com.example.supermarketmanagementapi.service.IBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillService implements IBillService {
    @Autowired
    private IBillRepository iBillRepository;

}
