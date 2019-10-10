package com.beehyv.case_study.repositories;

import com.beehyv.case_study.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem,Long> {
    CartItem findByCartItemId(long cartItemId);
}
