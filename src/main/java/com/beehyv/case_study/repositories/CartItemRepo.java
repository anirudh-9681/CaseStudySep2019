package com.beehyv.case_study.repositories;

import com.beehyv.case_study.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepo extends JpaRepository<CartItem,Integer> {
    CartItem findByCartItemId(int cartItemId);
}
