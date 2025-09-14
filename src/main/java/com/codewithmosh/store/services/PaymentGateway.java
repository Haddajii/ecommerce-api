package com.codewithmosh.store.services;

import com.codewithmosh.store.entities.Order;

import java.util.Optional;

public interface PaymentGateway {
    CheckoutSession CreateCheckoutSession(Order order);
    Optional<PaymentResult> parseWebhookRequest(WebhookRequest request);
}
