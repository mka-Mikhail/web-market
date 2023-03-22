package com.mka.webmarket.carts.converters;

import com.mka.webmarket.api.CartDto;
import com.mka.webmarket.api.CartItemDto;
import com.mka.webmarket.carts.models.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CartConverter {
    private final CartItemConverter cartItemConverter;

    public CartDto modelToDto(Cart cart) {
        CartDto cartDto = new CartDto();
        cartDto.setTotalPrice(cart.getTotalPrice());
        cartDto.setItems(cart.getItems().stream().map(cartItemConverter::modelToDto).collect(Collectors.toList()));
        return cartDto;
    }
}
