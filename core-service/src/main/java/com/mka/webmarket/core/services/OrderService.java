package com.mka.webmarket.core.services;

import com.mka.webmarket.api.CartDto;
import com.mka.webmarket.core.entities.Order;
import com.mka.webmarket.core.entities.OrderItem;
import com.mka.webmarket.core.entities.User;
import com.mka.webmarket.core.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ProductService productService;
    private final OrderRepository orderRepository;

    @Transactional
    public void createOrder(User user) {
        CartDto cartDto = null;
        Order order = new Order();
        order.setUser(user);
        order.setTotalPrice(cartDto.getTotalPrice());
        order.setItems(cartDto.getItems().stream().map(
                cartItemDto -> new OrderItem(
                        productService.findById(cartItemDto.getProductId()).get(),
                        order,
                        cartItemDto.getQuantity(),
                        cartItemDto.getPricePerProduct(),
                        cartItemDto.getPrice()
                )
        ).collect(Collectors.toList()));
        orderRepository.save(order);
    }
}