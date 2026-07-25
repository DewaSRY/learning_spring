package com.sdewa.BasicSpring.models;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeverageCreateRequest {
    private String name;
    private String description;
    private String number;
}
