package com.codewithmosh.store.controllers;

import com.codewithmosh.store.dtos.AddItemToCartRequest;
import com.codewithmosh.store.dtos.CartDto;
import com.codewithmosh.store.dtos.CartItemDto;
import com.codewithmosh.store.entities.Cart;
import com.codewithmosh.store.entities.CartItem;
import com.codewithmosh.store.mappers.CartMapper;
import com.codewithmosh.store.repositories.CartRepository;
import com.codewithmosh.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/carts")
public class CartController {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ProductRepository productRepository;

    @PostMapping
    public ResponseEntity<CartDto> createCart(UriComponentsBuilder uriBuilder) {
        var cart = new Cart() ;
        cartRepository.save(cart);
        var cartDto = cartMapper.toCartDto(cart);
        var uri = uriBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri() ;
        return ResponseEntity.created(uri).body(cartDto) ;

    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartItemDto> addToCart(@PathVariable UUID cartId ,@RequestBody AddItemToCartRequest request){
        var cart = cartRepository.getCartWithItems(cartId).orElse(null) ;
        if(cart == null){
            return ResponseEntity.notFound().build();
        }
        var item = productRepository.findById(request.getProductId()).orElse(null) ;
        if(item == null){
            return ResponseEntity.badRequest().build() ;
        }
        var cartItem = cart.getCartItems().stream()
                .filter(product -> product.getProduct().getId().equals(item.getId()))
                .findFirst()
                .orElse(null) ;

        if(cartItem != null){
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        }
        else{
            cartItem = new CartItem() ;
            cartItem.setQuantity(1);
            cartItem.setProduct(item);
            cartItem.setCart(cart);
            cart.getCartItems().add(cartItem);
        }
        cartRepository.save(cart);
        var cartItemDto = cartMapper.toCartItemDto(cartItem) ;
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto) ;
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> getCart(@PathVariable UUID cartId){
        var cart = cartRepository.getCartWithItems(cartId).orElse(null) ;
        if(cart == null){
            return ResponseEntity.notFound().build();
        }
        var cartDto = cartMapper.toCartDto(cart) ;
        return ResponseEntity.ok(cartDto) ;

    }
}
