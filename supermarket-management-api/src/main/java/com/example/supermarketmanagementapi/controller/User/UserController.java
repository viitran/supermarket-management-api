package com.example.supermarketmanagementapi.controller.User;

import com.example.supermarketmanagementapi.repository.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private IAccountRepository iAccountRepository;

    @GetMapping("/info")
    public ResponseEntity<?> showInfoUser(Principal principal) {
        return new ResponseEntity<>(this.iAccountRepository.findAccountByUsername(principal.getName()), HttpStatus.OK);
    }
}
