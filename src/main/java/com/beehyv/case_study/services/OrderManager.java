package com.beehyv.case_study.services;

import com.beehyv.case_study.entities.*;
import com.beehyv.case_study.repositories.CartItemRepo;
import com.beehyv.case_study.repositories.CartRepo;
import com.beehyv.case_study.repositories.OrderItemRepo;
import com.beehyv.case_study.repositories.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderManager {
    @Autowired
    UserManager userManager;
    @Autowired
    CartManager cartManager;
    @Autowired
    OrderRepo orderRepo;
    @Autowired
    OrderItemRepo orderItemRepo;
    @Autowired
    CartItemRepo cartItemRepo;

    public List<Order> getUserOrders(long userId){
        if (userManager.isAuthorized(userId)){
            MyUser myUser = userManager.getUserById(userId);
            List<Order> orders = myUser.getOrders();
            return orders;
        }
        return null;
    }

    public Order createOrder(long userId) {
        if (!userManager.isAuthorized(userId)){
            return null;
        }
        MyUser myUser = userManager.getUserById(userId);
        List<OrderItem> orderItems = myUser.getCart().getProducts()
                                            .stream()
                                            .map(cartItem -> {
                                                    OrderItem orderItem = new OrderItem();
                                                    orderItem.setProduct(cartItem.getProduct());
                                                    orderItem.setQuantity(cartItem.getQuantity());
                                                    orderItemRepo.save(orderItem);
                                                    cartItemRepo.delete(cartItem);
                                                    return orderItem;
                                                })
                                            .collect(Collectors.toList());
        Order order = new Order();
        order.setOrderStatus(OrderStatus.PROCESSING);
        order.setProducts(orderItems);
        orderRepo.save(order);
        myUser.getOrders().add(order);
        userManager.updateUser(myUser);
        return order;
    }
}
