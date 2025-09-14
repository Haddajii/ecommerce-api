package com.codewithmosh.store.services;

import com.codewithmosh.store.entities.Order;
import com.codewithmosh.store.entities.OrderStatus;
import com.codewithmosh.store.exceptions.PaymentException;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class StripePaymentGateway implements PaymentGateway {
    @Value("${websiteUrl}")
    private String websiteUrl ;
    @Value("${stripe.webhookSecretKey}")
    private String webhookSecretKey ;
    @Override
    public CheckoutSession CreateCheckoutSession(Order order) {
       try{
           var builder = SessionCreateParams.builder()
                   .setMode(SessionCreateParams.Mode.PAYMENT)
                   .setSuccessUrl(websiteUrl + "/checkout-success?orderId=" + order.getId())
                   .setCancelUrl(websiteUrl + "/checkout-cancel")
                   .putMetadata("order_id" , order.getId().toString());


           order.getItems().forEach(item -> {
               var LineItem = SessionCreateParams.LineItem.builder()
                       .setQuantity(Long.valueOf(item.getQuantity()))
                       .setPriceData(
                               SessionCreateParams.LineItem.PriceData.builder()
                                       .setCurrency("USD")
                                       .setUnitAmountDecimal(item.getUnitPrice().multiply(BigDecimal.valueOf(100)))
                                       .setProductData(
                                               SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                       .setName(item.getProduct().getName())
                                                       .build()
                                       ).build()
                       ).build();
               builder.addLineItem(LineItem) ;
           }) ;

           var session = Session.create(builder.build()) ;
           return new CheckoutSession(session.getUrl()) ;
       }
       catch (StripeException ex){
           throw new PaymentException();
       }
    }

    @Override
    public Optional<PaymentResult> parseWebhookRequest(WebhookRequest request) {
        try {
            var payload = request.getPayload() ;
            var signature = request.getHeaders().get("stripe-signature");
            var event = Webhook.constructEvent(signature , payload ,webhookSecretKey ) ;
            switch (event.getType()){
                case "payment_intent.succeeded"-> {

                        return Optional.of(new PaymentResult(extractOrderId(event), OrderStatus.PAID)) ;
                    }
                case "payment_intent.payment_failed"-> {
                    return Optional.of(new PaymentResult(extractOrderId(event), OrderStatus.FAILED)) ;
                }
                default -> {
                    return Optional.empty() ;
                }

            }

            }
        catch (SignatureVerificationException e) {
            throw new PaymentException("Signature verification failed") ;
        }

        }
    private Long extractOrderId(Event event){
        var stripeObject = event.getDataObjectDeserializer().getObject().orElseThrow(() -> new PaymentException("could not deserialize stripe event")) ;
        var paymentIntent = (PaymentIntent) stripeObject ;

            return Long.valueOf(paymentIntent.getMetadata().get("order_id" )) ;
        }
    }

