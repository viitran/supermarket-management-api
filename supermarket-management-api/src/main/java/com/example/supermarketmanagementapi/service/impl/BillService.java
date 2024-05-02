package com.example.supermarketmanagementapi.service.impl;

import com.example.supermarketmanagementapi.model.Account;
import com.example.supermarketmanagementapi.model.Bill;
import com.example.supermarketmanagementapi.model.PaymentStatus;
import com.example.supermarketmanagementapi.model.ProductOrder;
import com.example.supermarketmanagementapi.repository.IBillRepository;
import com.example.supermarketmanagementapi.service.IBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BillService implements IBillService {
    @Autowired
    private IBillRepository iBillRepository;

//    public Bill addNewBill(Double totalPrice, Account account, List<ProductOrder> productOrders) {
//        Bill bill = new Bill();
//        bill.setDate(new Date(System.currentTimeMillis()));
//        PaymentStatus paymentStatus = new PaymentStatus();
//        return null;
//    }
}
