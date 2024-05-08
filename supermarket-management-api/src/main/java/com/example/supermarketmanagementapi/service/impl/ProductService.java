package com.example.supermarketmanagementapi.service.impl;


import com.example.supermarketmanagementapi.dto.IProductDto;
import com.example.supermarketmanagementapi.dto.OrderDto;
import com.example.supermarketmanagementapi.dto.PaymentDto;
import com.example.supermarketmanagementapi.dto.RequestDto;
import com.example.supermarketmanagementapi.model.*;
import com.example.supermarketmanagementapi.repository.IAccountRepository;
import com.example.supermarketmanagementapi.repository.IBillRepository;
import com.example.supermarketmanagementapi.repository.IProductOrderRepository;
import com.example.supermarketmanagementapi.repository.IProductRepository;
import com.example.supermarketmanagementapi.service.IAccountService;
import com.example.supermarketmanagementapi.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Service
public class ProductService implements IProductService {
    @Autowired
    private IProductRepository iProductRepository;
    @Autowired
    private IProductOrderRepository iProductOrderRepository;
    @Autowired
    private IAccountRepository iAccountRepository;

    @Autowired
    private IBillRepository billRepository;


    @Override
    public List<Product> findNewProduct() {
        return this.iProductRepository.getNewProduct();
    }

    @Override
    public List<Product> findAllProduct() {
        return this.iProductRepository.getAllProduct();
    }

    @Override
    public Product findProductById(Integer id) {
        return this.iProductRepository.findProductById(id);
    }

    @Override
    public List<Product> findProductByCate(Integer id) {
        return this.iProductRepository.findProductByCate(id);
    }

    @Override
    public List<ProductOrder> addProductsToCart(OrderDto orderDto, String username) {
        Product product = this.iProductRepository.findProductById(orderDto.getProductId());
        Account account = this.iAccountRepository.findAccountByUsername(username);
        ProductOrder productOrder = this.iProductOrderRepository.findOrderByIdProductOrUsername(product.getId(), username);
        if (productOrder == null) {
            addProductToCart(product, account);
        } else {
            if (orderDto.getQuantity() == 1 && product.getQuantity() > productOrder.getQuantity()) {
                productOrder.setQuantity(productOrder.getQuantity() + 1);
                this.iProductOrderRepository.save(productOrder);
//                product.setQuantity(product.getQuantity()-productOrder.getQuantity());
                System.out.println("tang sl thanh cong " + productOrder.getQuantity() + " id product la " + product.getId());
//                System.out.println("sl con lai trong kho la " + product.getQuantity());
            } else if (orderDto.getQuantity() == -1 && productOrder.getQuantity() < product.getQuantity()) {
                productOrder.setQuantity(productOrder.getQuantity() - 1);
                this.iProductOrderRepository.save(productOrder);
                System.out.println("giam sl thanh cong " + productOrder.getQuantity() + " id product la " + product.getId());
            } else if (orderDto.getQuantity() > 1) {
                if (product.getQuantity() >= (productOrder.getQuantity() + orderDto.getQuantity())) {
                    productOrder.setQuantity(productOrder.getQuantity() + orderDto.getQuantity());
                    this.iProductOrderRepository.save(productOrder);
                }
            }
        }
        return null;
    }


    @Override
    public void addProductToCart(Product product, Account account) {
        ProductOrder productOrder = new ProductOrder();
        productOrder.setProduct(product);
        productOrder.setDate(new Date(System.currentTimeMillis()));
        productOrder.setBill(null);
        productOrder.setQuantity(1);
        productOrder.setAccount(account);
        this.iProductOrderRepository.save(productOrder);
    }


    @Override
    public ResponseEntity<?> addNewBill(String username) {
        List<ProductOrder> productOrderList = this.iProductOrderRepository.getAllProductOrderOfUser(username);
        if (!productOrderList.isEmpty()) {
            Bill bill = new Bill();
            bill.setDate(new Date(System.currentTimeMillis()));

            PaymentStatus paymentStatus = new PaymentStatus();
            paymentStatus.setId(2);

            bill.setPaymentStatus(paymentStatus);

            double totalPrice = 0;
            for (ProductOrder productOrder : productOrderList) {
                totalPrice += (productOrder.getQuantity() * productOrder.getProduct().getPrice()) + 30000;
            }
            bill.setTotal(totalPrice);

            bill.setAddress("280 Trần Hưng Đạo");
            bill.setMessage("ok");

            Bill bill1 = this.billRepository.save(bill);

            for (ProductOrder po : productOrderList) {
                po.setBill(bill1);
                Product product = po.getProduct();
                product.setQuantity(product.getQuantity() - po.getQuantity());
                this.iProductRepository.save(product);
                this.iProductOrderRepository.save(po);
            }
            return new ResponseEntity<>("00", HttpStatus.OK);
        } else {
            System.out.println("don hang da dc thanh toan");
            return new ResponseEntity<>("paid", HttpStatus.OK);
        }
    }


    @Override
    public Page<Product> getAllProductPage(RequestDto requestDto) {
        Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getSize(), requestDto.getSortDirection(), requestDto.getSortBy());
        return this.iProductRepository.findAllPageProduct(pageable, requestDto);
    }

    @Override
    public List<IProductDto> findAllProductsTopSelling() {
        return this.iProductRepository.findTopSelling();
    }


}
