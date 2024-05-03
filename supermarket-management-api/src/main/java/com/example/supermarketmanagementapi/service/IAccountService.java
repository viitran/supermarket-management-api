package com.example.supermarketmanagementapi.service;

import com.example.supermarketmanagementapi.model.Account;

public interface IAccountService {
    Account findAccountByUsername(String username);

    Account findAccountById(Integer id);
}
