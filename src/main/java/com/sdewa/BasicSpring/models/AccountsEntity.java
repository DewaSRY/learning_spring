package com.sdewa.BasicSpring.models;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountsEntity {
    private String number;
    private String name;
    private Long id;
    private LocalDateTime createdAt;
}



