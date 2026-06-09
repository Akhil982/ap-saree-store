package com.ap.saree.controller;

import com.ap.saree.dto.SareeProductDTO;
import com.ap.saree.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<SareeProductDTO>> fetchAllProducts() {
        return ResponseEntity.ok(productService.getAllCatalogProducts());
    }
}
