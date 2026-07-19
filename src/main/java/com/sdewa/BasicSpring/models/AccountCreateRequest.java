package com.sdewa.BasicSpring.models;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountCreateRequest {
    private String name;
    private String number;
}
