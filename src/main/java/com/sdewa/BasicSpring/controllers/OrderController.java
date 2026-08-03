package com.sdewa.BasicSpring.controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sdewa.BasicSpring.models.OrderEntity;
import com.sdewa.BasicSpring.models.OrderQuery;
import com.sdewa.BasicSpring.models.OrderRequest;
import com.sdewa.BasicSpring.models.PaginationDataResponse;
import com.sdewa.BasicSpring.services.OrderBeverageServices;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderBeverageServices orderBeverageServices;

    @GetMapping
    public ResponseEntity<PaginationDataResponse<OrderEntity>> getOrders(@ModelAttribute OrderQuery query) {
        return ResponseEntity.ok(orderBeverageServices.getOrders(query));
    }

    @PostMapping
    public ResponseEntity<OrderEntity> createOrder(@Valid @RequestBody OrderRequest orderRequest) {
        Optional<OrderEntity> createdOrder = orderBeverageServices.createOrder(orderRequest);
        return createdOrder
                .map(order -> ResponseEntity.status(HttpStatus.CREATED).body(order))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
