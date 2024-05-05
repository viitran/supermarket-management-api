package com.example.supermarketmanagementapi.controller.Public;


import com.example.supermarketmanagementapi.dto.OrderDto;
import com.example.supermarketmanagementapi.dto.RequestDto;
import com.example.supermarketmanagementapi.model.Product;
import com.example.supermarketmanagementapi.model.ProductOrder;
import com.example.supermarketmanagementapi.repository.IProductOrderRepository;
import com.example.supermarketmanagementapi.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @GetMapping()
    public ResponseEntity<?> showNewProduct() {
        List<Product> products = this.iProductService.findNewProduct();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/collections")
    public ResponseEntity<?> showAllProduct(@RequestBody RequestDto requestDto) {
        Page<Product> productPage = this.iProductService.getAllProductPage(requestDto);
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

}
