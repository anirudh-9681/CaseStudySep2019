package com.beehyv.case_study.repositories;

import com.beehyv.case_study.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepo extends JpaRepository<Cart,Integer> {
}
