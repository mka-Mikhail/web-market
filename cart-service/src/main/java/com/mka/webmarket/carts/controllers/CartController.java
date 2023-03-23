package com.mka.webmarket.carts.controllers;

import com.mka.webmarket.api.CartDto;
import com.mka.webmarket.carts.converters.CartConverter;
import com.mka.webmarket.carts.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@CrossOrigin("*")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final CartConverter cartConverter;

    @GetMapping
    public CartDto getCurrentCart() {
        return cartConverter.modelToDto(cartService.getCurrentCart());
    }

    @GetMapping("/add/{id}")
    public void addToCart(@PathVariable Long id) {
        cartService.add(id);
    }

    @GetMapping("/remove/{id}")
    public void removeFromCart(@PathVariable Long id) {
        cartService.remove(id);
    }

    @GetMapping("/clear")
    public void clearCart() {
        cartService.clear();
    }
}
