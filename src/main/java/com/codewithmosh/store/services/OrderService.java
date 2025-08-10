package com.codewithmosh.store.services;

import com.codewithmosh.store.dtos.OrderDto;
import com.codewithmosh.store.entities.Order;
import com.codewithmosh.store.entities.User;
import com.codewithmosh.store.mappers.OrderMapper;
import com.codewithmosh.store.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@AllArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository ;
    private final OrderMapper orderMapper ;
    private final AuthService authService ;
    public List<OrderDto> getAllOrders() {
        var user = authService.getCurrentUser() ;
        var orders = orderRepository.findAllByCustomer(user);

        return orders.stream().map(orderMapper::toDto).toList() ;
    }
}
