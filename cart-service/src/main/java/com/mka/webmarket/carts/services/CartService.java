package com.mka.webmarket.carts.services;

import com.mka.webmarket.api.ProductDto;
import com.mka.webmarket.carts.integrations.ProductServiceIntegration;
import com.mka.webmarket.carts.models.Cart;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductServiceIntegration productServiceIntegrations;
    private Cart tempCart;

    @PostConstruct
    public void init() {
        tempCart = new Cart();
    }

    public Cart getCurrentCart() {
        return tempCart;
    }

    public void add(Long productId) {
        ProductDto productDto = productServiceIntegrations.getProductById(productId);
        tempCart.add(productDto);
    }

    public void remove(Long productId) {
        tempCart.remove(productId);
    }

    public void clear() {
        tempCart.clear();
    }
}