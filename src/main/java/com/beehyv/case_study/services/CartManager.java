package com.beehyv.case_study.services;

import com.beehyv.case_study.dto.ProductDTO;
import com.beehyv.case_study.dto.QuantityDTO;
import com.beehyv.case_study.entities.Cart;
import com.beehyv.case_study.entities.CartItem;
import com.beehyv.case_study.entities.MyUser;
import com.beehyv.case_study.entities.Product;
import com.beehyv.case_study.repositories.CartItemRepo;
import com.beehyv.case_study.repositories.CartRepo;
import com.beehyv.case_study.repositories.ProductRepo;
import com.beehyv.case_study.utilities.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CartManager {
    @Autowired
    UserManager userManager;
    @Autowired
    ProductRepo productRepo;
    @Autowired
    CartRepo cartRepo;
    @Autowired
    CartItemRepo cartItemRepo;

    public void updateCart(Cart cart) {
        cartRepo.save(cart);
    }

    public Cart getUserCart(long userId) throws UnauthorizedException {
        if (!userManager.isAuthorized(userId)) {
            return null;
        }
        if (!userManager.existsById(userId)) {
            return null;
        }
        return userManager.getUserById(userId).getCart();
    }

    public CartItem getCartItem(long userId, long cartItemId) throws UnauthorizedException {
        Cart cart = getUserCart(userId);
        if (Objects.nonNull(cart)) {
            for (CartItem cartItem : cart.getProducts()) {
                if (cartItem.getCartItemId() == cartItemId) {
                    return cartItem;
                }
            }
        }
        return null;
    }

    public CartItem addProductToCart(long userId, long productId) throws UnauthorizedException {
        if (!userManager.isAuthorized(userId)) {
            return null;
        }
        if (!productRepo.existsByProductId(productId)) {
            return null;
        }

        MyUser myUser = userManager.getUserById(userId);
        Cart cart = myUser.getCart();
        Product product = productRepo.findByProductId(productId);
        for (CartItem cartItem : cart.getProducts()) {
            if (cartItem.getProduct().equals(product)) {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                cartItemRepo.save(cartItem);
                updateCart(cart);
                return cartItem;
            }
        }

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(1);
        cart.getProducts().add(cartItem);
        cartItemRepo.save(cartItem);
        updateCart(cart);
        userManager.updateUser(myUser);
        return cartItem;
    }

    public ProductDTO removeProductFromCart(long userId, long productId) throws UnauthorizedException {
        if (!userManager.isAuthorized(userId)) {
            return null;
        }
        if (!productRepo.existsByProductId(productId)) {
            return null;
        }
        Product product = productRepo.findByProductId(productId);
        Cart cart = getUserCart(userId);
        for (CartItem cartItem : cart.getProducts()) {
            if (cartItem.getProduct().equals(product)) {

                cartItemRepo.delete(cartItem);
                cart.getProducts().remove(cartItem);
                updateCart(cart);
                return product.getDTO();

            }
        }
        return null;
    }

    public CartItem changeCartItemQuantity(long cartItemId, QuantityDTO quantityDTO) throws UnauthorizedException {
        if (userManager.isAdmin()) {
            CartItem cartItem = cartItemRepo.findByCartItemId(cartItemId);
            cartItem.setQuantity(quantityDTO.getQuantity());
            if (quantityDTO.getQuantity() == 0) {
                cartItemRepo.delete(cartItem);
            } else {
                cartItemRepo.save(cartItem);
            }
            return cartItem;
        }
        long userId = userManager.getLoggedInUserId();
        Cart cart = getUserCart(userId);
        for (CartItem cartItem : cart.getProducts()) {
            if (cartItem.getCartItemId() == cartItemId) {
                cartItem.setQuantity(quantityDTO.getQuantity());
                if (quantityDTO.getQuantity() == 0) {
                    cartItemRepo.delete(cartItem);
                } else {
                    cartItemRepo.save(cartItem);
                }
                return cartItem;
            }
        }
        return null;
    }

    public CartItem changeCartItemQuantity(long userId, long productId, QuantityDTO quantityDTO) throws UnauthorizedException {
        if (userManager.isAuthorized(userId)) {
            Cart cart = getUserCart(userId);
            for (CartItem cartItem : cart.getProducts()) {
                if (cartItem.getProduct().getProductId() == productId) {
                    cartItem.setQuantity(quantityDTO.getQuantity());
                    if (quantityDTO.getQuantity() == 0) {
                        cartItemRepo.delete(cartItem);
                    } else {
                        cartItemRepo.save(cartItem);
                    }
                    return cartItem;
                }
            }
        }
        return null;
    }
}
