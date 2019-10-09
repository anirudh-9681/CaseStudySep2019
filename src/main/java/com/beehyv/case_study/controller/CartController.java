package com.beehyv.case_study.controller;

import com.beehyv.case_study.entities.Cart;
import com.beehyv.case_study.entities.CartItem;
import com.beehyv.case_study.services.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    UserManager userManager;

    @GetMapping("/{userId}/getCart")
    public ResponseEntity getCart(@PathVariable String userId){
        try {
            Cart cart = userManager.getUserCart(Integer.parseInt(userId));
            if (Objects.nonNull(cart)){
                return ResponseEntity.ok().body(cart);
            }
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{userId}/getCartItem/{cartItemId}")
    public ResponseEntity getCartItem(@PathVariable String userId, @PathVariable String cartItemId){
        try{
            CartItem cartItem = userManager.getCartItem(
                    Integer.parseInt(userId),
                    Integer.parseInt(cartItemId));
            if (Objects.nonNull(cartItem)){
                return ResponseEntity.ok().body(cartItem);
            }
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{userId}/add/{productId}")
    public ResponseEntity addProductToCart(@PathVariable String userId, @PathVariable String productId){
        try{
            CartItem cartItem = userManager.addProductToCart(
                    Integer.parseInt(userId),
                    Integer.parseInt(productId));
            if (Objects.nonNull(cartItem)){
                return ResponseEntity.ok().body(cartItem);
            }
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }
}
