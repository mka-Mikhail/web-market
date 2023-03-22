package com.mka.webmarket.core.services;

import com.mka.webmarket.api.ProductDto;
import com.mka.webmarket.api.ResourceNotFoundException;
import com.mka.webmarket.core.entities.Category;
import com.mka.webmarket.core.entities.Product;
import com.mka.webmarket.core.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public Product createNewProduct(ProductDto productDto) {
        Product product = new Product();
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        Category category = categoryService.findByTitle(productDto.getCategoryTitle()).orElseThrow(() -> new ResourceNotFoundException("Категория " + productDto.getCategoryTitle() + " не найдена"));
        product.setCategory(category);
        productRepository.save(product);
        return product;
    }
}
