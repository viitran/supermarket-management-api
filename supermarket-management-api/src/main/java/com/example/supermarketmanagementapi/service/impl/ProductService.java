package com.example.supermarketmanagementapi.service.impl;


import com.example.supermarketmanagementapi.dto.OrderDto;
import com.example.supermarketmanagementapi.model.Account;
import com.example.supermarketmanagementapi.model.Product;
import com.example.supermarketmanagementapi.model.ProductOrder;
import com.example.supermarketmanagementapi.repository.IAccountRepository;
import com.example.supermarketmanagementapi.repository.IProductOrderRepository;
import com.example.supermarketmanagementapi.repository.IProductRepository;
import com.example.supermarketmanagementapi.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProductService implements IProductService {
    @Autowired
    private IProductRepository iProductRepository;
    @Autowired
    IProductOrderRepository iProductOrderRepository;
    @Autowired
    IAccountRepository iAccountRepository;

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
    public List<ProductOrder> addProductsToCart(OrderDto orderDto,String username) {
        Product product = this.iProductRepository.findProductById(orderDto.getProductId());
        Account account = this.iAccountRepository.findAccountByUsername("vtran123");
        ProductOrder productOrder = this.iProductOrderRepository.findOrderByIdProduct(product.getId());
        if (productOrder == null) {
            addProductToCart(product,account);
        } else {
            if (orderDto.getQuantity() == 1) {
                if (orderDto.getQuantity() < product.getQuantity()) {
                    productOrder.setQuantity(productOrder.getQuantity() + 1);
                    this.iProductOrderRepository.save(productOrder);
                    System.out.println("tăng sl thành công");
                    System.out.println(productOrder.getQuantity());
                }
            } else if (orderDto.getQuantity() > 1 && orderDto.getQuantity() < product.getQuantity()) {
                productOrder.setQuantity(productOrder.getQuantity() - 1);
                this.iProductOrderRepository.save(productOrder);
                System.out.println("giảm sl thành công");
                System.out.println(productOrder.getQuantity());
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
}
