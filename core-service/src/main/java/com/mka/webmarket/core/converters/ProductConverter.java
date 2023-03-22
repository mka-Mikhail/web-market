package com.mka.webmarket.core.converters;

import com.mka.webmarket.core.entities.Category;
import com.mka.webmarket.core.entities.Product;
import com.mka.webmarket.core.services.CategoryService;
import com.mka.webmarket.core.dtos.ProductDto;
import com.mka.webmarket.core.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductConverter {
    private final CategoryService categoryService;

    public ProductDto entityToDto(Product product) {
        return new ProductDto(product.getId(), product.getTitle(), product.getPrice(), product.getCategory().getTitle());
    }

    public Product dtoToEntity(ProductDto productDto) {
        Product product = new Product();
        product.setId(product.getId());
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        Category category = categoryService.findByTitle(productDto.getCategoryTitle()).orElseThrow(() -> new ResourceNotFoundException("Категория не найдена"));
        product.setCategory(category);
        return product;
    }
}
