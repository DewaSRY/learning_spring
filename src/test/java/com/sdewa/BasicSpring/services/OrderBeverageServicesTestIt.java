package com.sdewa.BasicSpring.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sdewa.BasicSpring.models.AccountsEntity;
import com.sdewa.BasicSpring.models.BeverageEntity;
import com.sdewa.BasicSpring.models.OrderEntity;
import com.sdewa.BasicSpring.models.OrderItemRequest;
import com.sdewa.BasicSpring.models.OrderRequest;
import com.sdewa.BasicSpring.repositories.AccountRepository;
import com.sdewa.BasicSpring.repositories.BeverageRepository;
import com.sdewa.BasicSpring.repositories.OrderItemRepository;
import com.sdewa.BasicSpring.repositories.OrderRepository;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.sdewa.BasicSpring.TestcontainersConfiguration;

@SpringBootTest
@Testcontainers
@Import(TestcontainersConfiguration.class)
class OrderBeverageServicesTestIt {

    @Autowired
    private OrderBeverageServices orderBeverageServices;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BeverageRepository beverageRepository;

    @BeforeEach
    void setup() {
    orderItemRepository.deleteAll();
    orderRepository.deleteAll();
    beverageRepository.deleteAll();
    accountRepository.deleteAll();
    }

    @Test
    void createOrderPersistsOrderAndItems() {
    AccountsEntity account = accountRepository.save(AccountsEntity.builder()
        .name("Integration Test Account")
        .number("ACC-ORDER-IT-001")
        .build());

    BeverageEntity espresso = beverageRepository.save(BeverageEntity.builder()
        .name("Espresso")
        .description("Single shot")
        .number("BEV-ESP-IT-001")
        .build());

    BeverageEntity latte = beverageRepository.save(BeverageEntity.builder()
        .name("Latte")
        .description("Milk coffee")
        .number("BEV-LAT-IT-001")
        .build());

        OrderRequest request = OrderRequest.builder()
                .number("ORD-100")
                .notes("Please prepare")
        .accountId(account.getId())
        .items(List.of(
            OrderItemRequest.builder().beverageId(espresso.getId()).qty(2).build(),
            OrderItemRequest.builder().beverageId(latte.getId()).qty(1).build()))
                .build();

    var result = orderBeverageServices.createOrder(request);

    assertThat(result).isPresent();
    OrderEntity savedOrder = result.get();

    assertThat(savedOrder.getId()).isNotNull();
    assertThat(savedOrder.getAccountId()).isEqualTo(account.getId());

    var persistedOrder = orderRepository.findById(savedOrder.getId());
    assertThat(persistedOrder).isPresent();
    assertThat(persistedOrder.get().getNumber()).isEqualTo("ORD-100");
    assertThat(persistedOrder.get().getNotes()).isEqualTo("Please prepare");

    var orderItems = orderItemRepository.findAll();
    assertThat(orderItems).hasSize(2);
    assertThat(orderItems).allMatch(item -> item.getOrderId().equals(savedOrder.getId()));
    assertThat(orderItems)
        .anyMatch(item -> item.getBeverageId().equals(espresso.getId()) && item.getQty().equals(2))
        .anyMatch(item -> item.getBeverageId().equals(latte.getId()) && item.getQty().equals(1));
    }

    @Test
    void createOrderWithInvalidRequestDoesNotPersist() {
    long beforeOrders = orderRepository.count();
    long beforeItems = orderItemRepository.count();

    OrderRequest invalidRequest = OrderRequest.builder()
        .number("ORD-INVALID")
        .notes("Missing account and items")
        .build();

    var result = orderBeverageServices.createOrder(invalidRequest);

    assertThat(result).isEmpty();
    assertThat(orderRepository.count()).isEqualTo(beforeOrders);
    assertThat(orderItemRepository.count()).isEqualTo(beforeItems);
    }
}
