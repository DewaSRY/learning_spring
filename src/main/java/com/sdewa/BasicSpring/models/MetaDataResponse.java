package com.sdewa.BasicSpring.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetaDataResponse {
    private Integer page;
    private Integer size;
    private Long totalAmount;
    private Integer totalPages;
    private Boolean last;
}
