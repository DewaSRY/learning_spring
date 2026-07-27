package com.sdewa.BasicSpring.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaginationDataResponse<T> {
    private List<T> data;
    private MetaDataResponse meta;
}
