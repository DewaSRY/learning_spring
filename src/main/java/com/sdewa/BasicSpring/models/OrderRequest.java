package com.sdewa.BasicSpring.models;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    private String number;
    private String notes;
    private Long accountId;
    private List<OrderItemRequest> items;
}
