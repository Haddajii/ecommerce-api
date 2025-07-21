package com.codewithmosh.store.services;

import com.codewithmosh.store.dtos.CartDto;
import com.codewithmosh.store.dtos.CartItemDto;
import com.codewithmosh.store.dtos.UpdateCartItemRequest;
import com.codewithmosh.store.entities.Cart;
import com.codewithmosh.store.exceptions.CartNotFoundException;
import com.codewithmosh.store.exceptions.ProductNotFoundException;
import com.codewithmosh.store.mappers.CartMapper;
import com.codewithmosh.store.repositories.CartRepository;
import com.codewithmosh.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ProductRepository productRepository;

    public CartDto createCart() {
        var cart = new Cart() ;
        cartRepository.save(cart);
        return cartMapper.toCartDto(cart);
    }

    public CartItemDto addToCart(UUID cartId , Long productId) {
        var cart = cartRepository.getCartWithItems(cartId).orElse(null) ;
        if(cart == null){
            throw  new CartNotFoundException() ;
        }
        var item = productRepository.findById(productId).orElse(null) ;
        if(item == null){
            throw new ProductNotFoundException() ;
        }
        var cartItem = cart.addItem(item);
        cartRepository.save(cart);
        return cartMapper.toCartItemDto(cartItem) ;
    }

    public CartDto getCart(UUID cartId) {
        var cart = cartRepository.getCartWithItems(cartId).orElse(null) ;
        if(cart == null){
            throw  new CartNotFoundException() ;
        }
        return cartMapper.toCartDto(cart) ;
    }

    public void removeItem(UUID cartId , Long productId) {
        var cart = cartRepository.getCartWithItems(cartId).orElse(null) ;
        if(cart == null){
            throw  new CartNotFoundException() ;
        }

        cart.removeItem(productId);
        cartRepository.save(cart) ;
    }

    public CartItemDto updateCart(UUID cartId , Long productId , UpdateCartItemRequest request) {
        var cart = cartRepository.getCartWithItems(cartId).orElse(null) ;
        if(cart == null){
            throw  new CartNotFoundException() ;
        }

        var cartItem = cart.getItem(productId) ;

        if(cartItem == null){
            throw new ProductNotFoundException() ;
        }

        cartItem.setQuantity(request.getQuantity());
        cartRepository.save(cart);
        return cartMapper.toCartItemDto(cartItem) ;
    }

    public void clearCart(UUID cartId) {
        var cart = cartRepository.getCartWithItems(cartId).orElse(null) ;
        if(cart == null){
            throw  new CartNotFoundException() ;
        }
        cart.clearCart();
        cartRepository.save(cart) ;
    }
}
