package com.example.supermarketmanagementapi.repository;

import com.example.supermarketmanagementapi.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IAccountRepository extends JpaRepository<Account, Integer> {
    @Query(value = "select * from account where username = :username", nativeQuery = true)
    Account findAccountByUsername(@Param("username") String username);

    @Query(value = "select * from account where id = :id", nativeQuery = true)
    Account findAccountById(@Param("id") Integer id);
}
