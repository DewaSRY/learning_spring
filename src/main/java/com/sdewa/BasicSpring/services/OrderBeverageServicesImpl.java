package com.sdewa.BasicSpring.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sdewa.BasicSpring.models.OrderEntity;
import com.sdewa.BasicSpring.models.OrderItemEntity;
import com.sdewa.BasicSpring.models.OrderItemRequest;
import com.sdewa.BasicSpring.models.OrderRequest;
import com.sdewa.BasicSpring.repositories.OrderItemRepository;
import com.sdewa.BasicSpring.repositories.OrderRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderBeverageServicesImpl implements OrderBeverageServices {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public Optional<OrderEntity> createOrder(OrderRequest orderRequest) {
        if (orderRequest == null || orderRequest.getAccountId() == null || orderRequest.getItems() == null
                || orderRequest.getItems().isEmpty()) {
            return Optional.empty();
        }

        boolean hasInvalidItem = orderRequest.getItems().stream().anyMatch(item -> item == null
                || item.getBeverageId() == null || item.getQty() == null || item.getQty() <= 0);
        if (hasInvalidItem) {
            return Optional.empty();
        }

        OrderEntity newOrder = OrderEntity.builder()
                .number(orderRequest.getNumber())
                .notes(orderRequest.getNotes())
                .accountId(orderRequest.getAccountId())
                .createdAt(LocalDateTime.now())
                .build();

        OrderEntity savedOrder = orderRepository.save(newOrder);

        List<OrderItemEntity> orderItems = orderRequest.getItems().stream()
                .map(this::toOrderItemEntity)
                .map(item -> item.toBuilder().orderId(savedOrder.getId()).build())
                .toList();

        orderItemRepository.saveAll(orderItems);
        return Optional.of(savedOrder);
    }

    private OrderItemEntity toOrderItemEntity(OrderItemRequest itemRequest) {
        return OrderItemEntity.builder()
                .beverageId(itemRequest.getBeverageId())
                .qty(itemRequest.getQty())
                .build();
    }
}
