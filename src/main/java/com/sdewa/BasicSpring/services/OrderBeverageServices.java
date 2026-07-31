package com.sdewa.BasicSpring.services;

import java.util.Optional;

import com.sdewa.BasicSpring.models.OrderEntity;
import com.sdewa.BasicSpring.models.OrderRequest;

public interface OrderBeverageServices {
    Optional<OrderEntity> createOrder(OrderRequest orderRequest);
}
