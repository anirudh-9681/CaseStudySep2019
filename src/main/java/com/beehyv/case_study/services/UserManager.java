package com.beehyv.case_study.services;

import com.beehyv.case_study.dto.SignUpDTO;
import com.beehyv.case_study.dto.UserProfileDTO;
import com.beehyv.case_study.entities.*;
import com.beehyv.case_study.repositories.*;
import com.beehyv.case_study.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserManager {

    @Autowired
    MyUserRepo myUserRepo;
    @Autowired
    CartRepo cartRepo;
    @Autowired
    CartItemRepo cartItemRepo;
    @Autowired
    ProductRepo productRepo;
    @Autowired
    MyUserCredentialsRepo myUserCredentialsRepo;
    @Autowired
    PasswordEncoder passwordEncoder;


    public int addUser(SignUpDTO signUpDTO) {
        if (!signUpDTO.isValid()) {
            return -1;
        }
        if (myUserCredentialsRepo.existsByEmail(signUpDTO.getEmail())) {
            return -1;
        }

        MyUser tmp = new MyUser();
        tmp.setName(signUpDTO.getName());
        tmp.setEmail(signUpDTO.getEmail());
        tmp.setCart(cartRepo.save(new Cart()));
        tmp = myUserRepo.save(tmp);
        MyUserCredentials myUserCredentials = new MyUserCredentials();
        myUserCredentials.setEmail(signUpDTO.getEmail());
        //TODO make sure javascript encodes into base64 and password is never decoded from base64
        myUserCredentials.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
        myUserCredentials.setAuthorities("USER");
        myUserCredentials.setMyUser(tmp);
        myUserCredentialsRepo.save(myUserCredentials);
        return tmp.getUserId();
    }

    public int getLoggedInUserId() {
        return ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMyUserCredentials().getUserId();
    }

    public MyUser getLoggedInUser() {
        return myUserRepo.getByUserId(getLoggedInUserId());
    }

    public boolean updateUser(UserProfileDTO userProfileDTO) {
        if (userProfileDTO.getUserId() != getLoggedInUserId()) {
            return false;
        }
        MyUser myUser = getLoggedInUser();
        myUser.setDTO(userProfileDTO);
        myUserRepo.save(myUser);
        return true;
    }

    public UserProfileDTO getUserProfileById(int id) {
        if (id == getLoggedInUserId() && myUserRepo.existsByUserId(id)) {
            return myUserRepo.getByUserId(id).getDTO();
        }
        return null;
    }

    public Cart getUserCart(int userId) {
        if (userId != getLoggedInUserId()) {
            return null;
        }
        return myUserRepo.getByUserId(userId).getCart();
    }

    public CartItem getCartItem(int userId, int cartItemId) {
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

    public CartItem addProductToCart(int userId, int productId) {
        if (userId != getLoggedInUserId()) {
            return null;
        }
        if (!productRepo.existsByProductId(productId)) {
            return null;
        }
        boolean found = false;
        CartItem cartItemFound = null;
        MyUser myUser = getLoggedInUser();
        Cart cart = myUser.getCart();
        Product product = productRepo.findByProductId(productId);
        for (CartItem cartItem : cart.getProducts()) {
            if (cartItem.getProduct().equals(product)) {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                cartItemFound = cartItemRepo.save(cartItem);
                found = true;
                break;
            }
        }
        if (!found) {
            cartItemFound = new CartItem();
            cartItemFound.setProduct(product);
            cartItemFound.setQuantity(1);
            cart.getProducts().add(cartItemFound);
            cartItemFound = cartItemRepo.save(cartItemFound);
        }


        cartRepo.save(cart);
        myUserRepo.save(myUser);
        return cartItemFound;
    }
}
