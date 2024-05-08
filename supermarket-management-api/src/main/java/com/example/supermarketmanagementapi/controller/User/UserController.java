package com.example.supermarketmanagementapi.controller.User;

import com.example.supermarketmanagementapi.model.ProductOrder;
import com.example.supermarketmanagementapi.repository.IAccountRepository;
import com.example.supermarketmanagementapi.service.IProductOrderService;
import com.example.supermarketmanagementapi.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private IAccountRepository iAccountRepository;

    @Autowired
    private IProductOrderService iProductOrderService;
    @Autowired
    private IProductService iProductService;

    @GetMapping("/info")
    public ResponseEntity<?> showInfoUser(Principal principal) {
        return new ResponseEntity<>(this.iAccountRepository.findAccountByUsername(principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/history")
    public ResponseEntity<?> getHistoryOrder(Principal principal) {
        List<ProductOrder> productOrders = this.iProductOrderService.getHistoryOrder(principal.getName());
        return new ResponseEntity<>(productOrders, HttpStatus.OK);
    }

//    @GetMapping("/test/{id}")
//    public ResponseEntity<?> getQuantity(@PathVariable Integer id,Principal principal){
//        this.iProductOrderService.getQuantityThisProductOnCart((Integer) this.iProductService.findProductById(id), principal.getName());
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
