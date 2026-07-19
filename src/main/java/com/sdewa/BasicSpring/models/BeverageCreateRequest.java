package com.sdewa.BasicSpring.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BeverageCreateRequest {
    private String name;
    private String description;
    private String number;
}
