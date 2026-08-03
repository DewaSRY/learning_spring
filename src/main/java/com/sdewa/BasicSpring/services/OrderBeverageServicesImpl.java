package com.sdewa.BasicSpring.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sdewa.BasicSpring.models.MetaDataResponse;
import com.sdewa.BasicSpring.models.OrderEntity;
import com.sdewa.BasicSpring.models.OrderItemEntity;
import com.sdewa.BasicSpring.models.OrderItemRequest;
import com.sdewa.BasicSpring.models.OrderQuery;
import com.sdewa.BasicSpring.models.OrderRequest;
import com.sdewa.BasicSpring.models.PaginationDataResponse;
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

        @Override
        public PaginationDataResponse<OrderEntity> getOrders(OrderQuery query) {
        OrderQuery safeQuery = query == null ? new OrderQuery() : query;

        String sortBy = safeQuery.getSortBy() == null || safeQuery.getSortBy().isBlank() ? "id" : safeQuery.getSortBy();
        String sortOrder = safeQuery.getSortOrder() == null || safeQuery.getSortOrder().isBlank() ? "asc"
            : safeQuery.getSortOrder();
        Integer pageNumber = safeQuery.getPage() == null || safeQuery.getPage() < 0 ? 0 : safeQuery.getPage();
        Integer pageSize = safeQuery.getSize() == null || safeQuery.getSize() <= 0 ? 10 : safeQuery.getSize();

        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name())
            ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<OrderEntity> page = orderRepository.findAll(OrderRepository.queryOrder(safeQuery), pageable);

        return PaginationDataResponse.<OrderEntity>builder()
            .data(page.getContent())
            .meta(MetaDataResponse.builder()
                .page(page.getNumber())
                .size(page.getSize())
                .totalAmount(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build())
            .build();
        }

    private OrderItemEntity toOrderItemEntity(OrderItemRequest itemRequest) {
        return OrderItemEntity.builder()
                .beverageId(itemRequest.getBeverageId())
                .qty(itemRequest.getQty())
                .build();
    }
}
