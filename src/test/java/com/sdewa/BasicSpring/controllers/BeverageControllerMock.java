package com.sdewa.BasicSpring.controllers;

import com.sdewa.BasicSpring.models.BeverageCreateRequest;

public class BeverageControllerMock {
    
    public static BeverageCreateRequest getMockBeverageController() {
        return BeverageCreateRequest.builder()
                .name("Mock Beverage")
                .description("This is a mock beverage for testing purposes.")
                .build();
    }
}