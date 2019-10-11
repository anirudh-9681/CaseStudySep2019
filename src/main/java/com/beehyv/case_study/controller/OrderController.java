package com.beehyv.case_study.controller;

import com.beehyv.case_study.entities.Order;
import com.beehyv.case_study.services.OrderManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderManager orderManager;

    @GetMapping("/{userId}/getOrders")
    public ResponseEntity getUserOrders(@PathVariable String userId) {
        try {
            List<Order> orders = orderManager.getUserOrders(
                    Long.parseLong(userId)
            );
            if (Objects.nonNull(orders)) {
                return ResponseEntity.ok().body(orders);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{userId}/createOrder")
    public ResponseEntity createOrder(@PathVariable String userId) {
        try {
            Order order = orderManager.createOrder(
                    Long.parseLong(userId)
            );
            if (Objects.nonNull(order)) {
                return ResponseEntity.ok().body(order);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }
}
