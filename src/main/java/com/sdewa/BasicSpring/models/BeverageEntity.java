package com.sdewa.BasicSpring.models;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BeverageEntity {
    private String name;
    private Long id;
    private String description;
    private String number;

    private LocalDateTime createdAt;
}   
