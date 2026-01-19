package mat.shopProject.shop.project.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mat.shopProject.shop.project.dto.CreateProductRequest;
import mat.shopProject.shop.project.models.Product;
import mat.shopProject.shop.project.repositories.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Transactional
@Service
public class ProductService {

    @Autowired
    ProductRepo productRepo;

    public List<Product> getProducts() {
        return productRepo.findAll();
    }

    public Product getProduct(UUID id) {
        return productRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Product with id " + id + " not found"
                ));
    }

    public Product createProduct(CreateProductRequest createProductRequest){
        Product product = Product.builder()
                .title(createProductRequest.title())
                .description(createProductRequest.description())
                .price(createProductRequest.price())
                .stockQuantity(createProductRequest.stockQuantity())
                .isActive(true)
                .category(createProductRequest.category())
                        .build();
        return productRepo.save(product);
    }

    public void deleteProduct(UUID id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Product with id " + id + " not found"
                ));
        productRepo.delete(product);
    }

    public Product changeProduct(UUID productId, CreateProductRequest createProductRequest){
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Product with id " + productId + " not found"
                ));
        product.setCategory(createProductRequest.category());
        product.setPrice(createProductRequest.price());
        product.setTitle(createProductRequest.title());
        product.setDescription(createProductRequest.description());
        product.setStockQuantity(createProductRequest.stockQuantity());
        return productRepo.save(product);
    }
}
