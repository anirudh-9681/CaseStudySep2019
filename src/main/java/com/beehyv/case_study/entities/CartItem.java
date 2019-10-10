package com.beehyv.case_study.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cartItemId;
    @ManyToOne
    private Product product;
    private int quantity;

    public long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return cartItemId == cartItem.cartItemId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartItemId);
    }
}
