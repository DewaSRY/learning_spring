package com.sdewa.BasicSpring.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdewa.BasicSpring.models.OrderEntity;
import com.sdewa.BasicSpring.models.OrderItemRequest;
import com.sdewa.BasicSpring.models.OrderRequest;
import com.sdewa.BasicSpring.models.MetaDataResponse;
import com.sdewa.BasicSpring.models.PaginationDataResponse;
import com.sdewa.BasicSpring.services.OrderBeverageServices;

@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc(addFilters = false)
class OrderControllerTest {

    private static final String BASE_URL = "/api/v1/orders";

    @MockitoBean
    private OrderBeverageServices orderBeverageServices;

    @Autowired
    private MockMvc mockMvc;

    
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getOrdersReturnsPaginatedResult() throws Exception {
        OrderEntity orderEntity = OrderEntity.builder()
                .id(10L)
                .number("ORD-001")
                .notes("Test order")
                .accountId(1L)
                .createdAt(LocalDateTime.of(2026, 7, 31, 10, 0))
                .build();

        PaginationDataResponse<OrderEntity> response = PaginationDataResponse.<OrderEntity>builder()
                .data(List.of(orderEntity))
                .meta(MetaDataResponse.builder()
                        .page(0)
                        .size(10)
                        .totalAmount(1L)
                        .totalPages(1)
                        .last(true)
                        .build())
                .build();

        when(orderBeverageServices.getOrders(any())).thenReturn(response);

        mockMvc.perform(get(BASE_URL)
                .param("page", "0")
                .param("size", "10")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(10))
                .andExpect(jsonPath("$.meta.page").value(0))
                .andExpect(jsonPath("$.meta.totalAmount").value(1));
    }

    @Test
    void createOrderReturnsCreatedWhenServiceSucceeds() throws Exception {
        OrderRequest request = OrderRequest.builder()
                .number("ORD-001")
                .notes("Test order")
                .accountId(1L)
                .items(List.of(OrderItemRequest.builder().beverageId(2L).qty(2).build()))
                .build();

        OrderEntity createdOrder = OrderEntity.builder()
                .id(10L)
                .number("ORD-001")
                .notes("Test order")
                .accountId(1L)
                .createdAt(LocalDateTime.of(2026, 7, 31, 10, 0))
                .build();

        when(orderBeverageServices.createOrder(any(OrderRequest.class))).thenReturn(Optional.of(createdOrder));

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10));
    }

    @Test
    void createOrderReturnsBadRequestWhenServiceReturnsEmpty() throws Exception {
        OrderRequest request = OrderRequest.builder()
                .number("ORD-002")
                .notes("Invalid order")
                .accountId(999L)
                .items(List.of(OrderItemRequest.builder().beverageId(99L).qty(1).build()))
                .build();

        when(orderBeverageServices.createOrder(any(OrderRequest.class))).thenReturn(Optional.empty());

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
