package com.sdewa.BasicSpring.models;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BeverageQuery {

    @Builder.Default
    private List<Long> includesIds = new ArrayList<>();

    @Builder.Default
    private List<Long> excludesIds = new ArrayList<>();

    @Builder.Default
    private String keyword = "";

    @Builder.Default
    private String sortBy = "id";

    @Builder.Default
    private String sortOrder = "asc";

    @Builder.Default
    private Integer page = 0;

    @Builder.Default
    private Integer size = 10;
}
