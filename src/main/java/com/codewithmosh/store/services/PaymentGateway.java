package com.codewithmosh.store.services;

import com.codewithmosh.store.dtos.CheckoutRequest;
import com.codewithmosh.store.entities.Order;

public interface PaymentGateway {
    CheckoutSession CreateCheckoutSession(Order order);
}
