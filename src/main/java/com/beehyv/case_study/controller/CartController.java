package com.beehyv.case_study.controller;

import com.beehyv.case_study.dto.ProductDTO;
import com.beehyv.case_study.dto.QuantityDTO;
import com.beehyv.case_study.entities.Cart;
import com.beehyv.case_study.entities.CartItem;
import com.beehyv.case_study.services.CartManager;
import com.beehyv.case_study.utilities.ObjectMapperImpl;
import com.beehyv.case_study.utilities.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartManager cartManager;

    @GetMapping("/{userId}/getCart")
    public ResponseEntity getCart(@PathVariable String userId) {
        try {
            Cart cart = cartManager.getUserCart(Long.parseLong(userId));
            if (Objects.nonNull(cart)) {
                return ResponseEntity.ok().body(cart);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{userId}/getCartItem/{cartItemId}")
    public ResponseEntity getCartItem(@PathVariable String userId, @PathVariable String cartItemId) {
        try {
            CartItem cartItem = cartManager.getCartItem(
                    Long.parseLong(userId),
                    Long.parseLong(cartItemId));
            if (Objects.nonNull(cartItem)) {
                return ResponseEntity.ok().body(cartItem);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{userId}/add/{productId}")
    public ResponseEntity addProductToCart(@PathVariable String userId, @PathVariable String productId) {
        try {
            CartItem cartItem = cartManager.addProductToCart(
                    Long.parseLong(userId),
                    Long.parseLong(productId));
            if (Objects.nonNull(cartItem)) {
                return ResponseEntity.ok().body(cartItem);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (UnauthorizedException e) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{userId}/remove/{productId}")
    public ResponseEntity removeProductFromCart(@PathVariable String userId, @PathVariable String productId) {
        try {
            ProductDTO productDTO = cartManager.removeProductFromCart(
                    Long.parseLong(userId),
                    Long.parseLong(productId)
            );
            if (Objects.nonNull(productDTO)) {
                String response = productDTO.getName() + " removed from cart.";
                return ResponseEntity.ok().body(response);
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.badRequest().build();

    }

    @PostMapping(value = {"/{userId}/changeQuantity/{productId}", "/changeQuantity/{cartItemId}"})
    public ResponseEntity changeProductQuantityInCart(
            @PathVariable(required = false) String userId,
            @PathVariable(required = false) String productId,
            @PathVariable(required = false) String cartItemId,
            @RequestBody String json) {

        QuantityDTO quantityDTO;
        try {
            quantityDTO = ObjectMapperImpl.getObjectFromJson(json, QuantityDTO.class);

            //Check if Quantity DTO is actually a valid one (non - negative)
            if (quantityDTO.isValid()) {

                //if - else block deals with two methods of making post request
                if (Objects.nonNull(cartItemId)) {
                    try {
                        CartItem cartItem = cartManager.changeCartItemQuantity(
                                Long.parseLong(cartItemId),
                                quantityDTO);
                        if (Objects.nonNull(cartItem)) {
                            return ResponseEntity.ok().body(cartItem);
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    } catch (UnauthorizedException e) {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                    }
                } else if (Objects.nonNull(userId) && Objects.nonNull(productId)) {
                    try {
                        CartItem cartItem = cartManager.changeCartItemQuantity(
                                Long.parseLong(userId),
                                Long.parseLong(productId),
                                quantityDTO);
                        if (Objects.nonNull(cartItem)) {
                            return ResponseEntity.ok().body(cartItem);
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    } catch (UnauthorizedException e) {
                        ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }
}
