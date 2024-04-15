package com.example.supermarketmanagementapi.service.impl;

import com.example.supermarketmanagementapi.model.Account;
import com.example.supermarketmanagementapi.repository.IAccountRepository;
import com.example.supermarketmanagementapi.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements IAccountService {
    @Autowired
    private IAccountRepository iAccountRepository;

    @Override
    public Account findAccountByUsername(String username) {
        return this.iAccountRepository.findAccountByUsername(username);
    }
}
