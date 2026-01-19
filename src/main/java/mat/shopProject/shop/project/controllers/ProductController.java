package mat.shopProject.shop.project.controllers;

import mat.shopProject.shop.project.dto.CreateProductRequest;
import mat.shopProject.shop.project.models.Product;
import mat.shopProject.shop.project.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/api/v1/products")
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @GetMapping("/api/v1/products/{productID}")
    public ResponseEntity<Product> getProduct(UUID productID) {
        return ResponseEntity.ok(productService.getProduct(productID));
    }

    @PostMapping("/api/v1/products")
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest createProductRequest) {
        return ResponseEntity.ok(productService.createProduct(createProductRequest));
    }

    @DeleteMapping("/api/v1/products/{productID}")
    public ResponseEntity<Product> deleteProduct(UUID productID) {
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/api/v1/products/{productID}")
    public ResponseEntity<Product> changeProduct(UUID productID, @RequestBody CreateProductRequest createProductRequest) {
        return ResponseEntity.ok(productService.changeProduct(productID, createProductRequest));
    }
}
