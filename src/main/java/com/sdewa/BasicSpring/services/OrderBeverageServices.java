package com.sdewa.BasicSpring.services;

import java.util.Optional;

import com.sdewa.BasicSpring.models.OrderEntity;
import com.sdewa.BasicSpring.models.OrderQuery;
import com.sdewa.BasicSpring.models.OrderRequest;
import com.sdewa.BasicSpring.models.PaginationDataResponse;

public interface OrderBeverageServices {
    Optional<OrderEntity> createOrder(OrderRequest orderRequest);

    PaginationDataResponse<OrderEntity> getOrders(OrderQuery query);
}
