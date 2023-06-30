package com.raul.controller;

import com.raul.service.ProductService;
import domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/products")
public class ProductController {

    private final ProductService service;

    @PostMapping
    public ResponseEntity<Product> products(@RequestBody Product product){
        service.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/sendA")
    public ResponseEntity<Product> sendA(@RequestBody Product product){
        service.sendA(product);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sendB")
    public ResponseEntity<Product> sendB(@RequestBody Product product){
        service.sendB(product);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sendFanout")
    public ResponseEntity<Product> sendC(@RequestBody Product product){
        service.sendFanout(product);
        return ResponseEntity.ok().build();
    }
}
