package com.mka.webmarket.carts.services;

import com.mka.webmarket.api.ProductDto;
import com.mka.webmarket.carts.integrations.ProductServiceIntegration;
import com.mka.webmarket.carts.models.Cart;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductServiceIntegration productServiceIntegrations;
    @Value("${cart-service.cart-prefix}")
    private String cartPrefix;
    private Map<String, Cart> carts;

    @PostConstruct
    public void init() {
        carts = new HashMap<>();
    }

    public Cart getCurrentCart(String uuid) {
        String targetUuid = cartPrefix + uuid;
        if (!carts.containsKey(uuid)) {
            carts.put(uuid, new Cart());
        }
        return carts.get(uuid);
    }

    public void add(String uuid, Long productId) {
        ProductDto productDto = productServiceIntegrations.getProductById(productId);
        getCurrentCart(uuid).add(productDto);
    }

    public void remove(String uuid, Long productId) {
        getCurrentCart(uuid).remove(productId);
    }

    public void clear(String uuid) {
        getCurrentCart(uuid).clear();
    }
}