package com.example.supermarketmanagementapi.controller.Public;


import com.example.supermarketmanagementapi.dto.IProductDto;
import com.example.supermarketmanagementapi.dto.OrderDto;
import com.example.supermarketmanagementapi.dto.RequestDto;
import com.example.supermarketmanagementapi.model.Product;
import com.example.supermarketmanagementapi.model.ProductOrder;
import com.example.supermarketmanagementapi.repository.IAccountRepository;
import com.example.supermarketmanagementapi.repository.IProductOrderRepository;
import com.example.supermarketmanagementapi.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("public")
public class ProductController {

    @Autowired
    private IProductService iProductService;

    @Autowired
    IProductOrderRepository iProductOrderRepository;

    @Autowired
    IAccountRepository iAccountRepository;

    @GetMapping()
    public ResponseEntity<?> showNewProduct() {
        List<Product> products = this.iProductService.findNewProduct();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("selling")
    public ResponseEntity<?> showAllTopSelling(){
        List<IProductDto> topSelling = this.iProductService.findAllProductsTopSelling();
        return new ResponseEntity<>(topSelling,HttpStatus.OK);
    }

    @PostMapping("/collections")
    public ResponseEntity<?> showAllProduct(@RequestBody RequestDto requestDto) {
        Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getSize(), Sort.by(requestDto.getSortDirection(), requestDto.getSortBy()));
        Page<Product> productPage = this.iProductService.getAllProduct(pageable, requestDto.getName(), requestDto.getPriceFrom(), requestDto.getPriceTo(), requestDto.getCategoryId());
        return new ResponseEntity<>(productPage, HttpStatus.OK);
    }


    @GetMapping("product/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Integer id) {
        Product product = this.iProductService.findProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/product/category/{id}")
    public ResponseEntity<?> getProductByIdCate(@PathVariable Integer id) {
        List<Product> products = this.iProductService.findProductByCate(id);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("cart-detail")
    public ResponseEntity<?> getCartDetail(@RequestBody OrderDto orderDto, Principal principal) {
        ProductOrder productOrder = this.iProductOrderRepository.findOrderByIdProductOrUsername(orderDto.getProductId(), principal.getName());
        return new ResponseEntity<>(productOrder, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> showInfoUser() {
        return new ResponseEntity<>(this.iAccountRepository.findAccountByUsername("vtran123"),HttpStatus.OK);
    }


}
