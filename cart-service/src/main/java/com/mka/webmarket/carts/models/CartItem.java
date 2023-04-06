package com.mka.webmarket.carts.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private Long productId;
    private String productTitle;
    private int quantity;
    private BigDecimal price;
    private BigDecimal pricePerProduct;

    public void changeQuantity(int delta) {
        quantity += delta;
        price = pricePerProduct.multiply(BigDecimal.valueOf(quantity));
    }
}
