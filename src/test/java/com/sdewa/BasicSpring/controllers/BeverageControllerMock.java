package com.sdewa.BasicSpring.controllers;

import com.sdewa.BasicSpring.models.BeverageCreateRequest;

import com.sdewa.BasicSpring.models.BeverageEntity;

public class BeverageControllerMock {
    
    public static BeverageCreateRequest getMockBeverageController() {
        return BeverageCreateRequest.builder()
                .name("Mock Beverage")
                .description("This is a mock beverage for testing purposes.")
                .build();
    }

    public static BeverageCreateRequest getMockBeverageControllerWithId(Long id) {
        return BeverageCreateRequest.builder()
                .name("Mock Beverage with ID")
                .description("This is a mock beverage with ID for testing purposes.")
                .build();
    }

    public static BeverageEntity getMockBeverageControllerWithIdAndNumber(Long id, String number) {
        return BeverageEntity.builder()
                .name("Mock Beverage with ID and Number")
                .description("This is a mock beverage with ID and Number for testing purposes.")
                .number(number)
                .id(id)
                .build();
    }

    public static BeverageCreateRequest getMockBeverageControllerWithNumber(String number) {
        return BeverageCreateRequest.builder()
                .name("Mock Beverage with Number")
                .description("This is a mock beverage with Number for testing purposes.")
                .number(number)
                .build();
    }

    public static BeverageEntity getMockBeverageControllerWithIdAndNumberAndName(
            BeverageCreateRequest beverageCreateRequest, long id) {
        return BeverageEntity.builder()
                .name(beverageCreateRequest.getName())
                .description(beverageCreateRequest.getDescription())
                .number(beverageCreateRequest.getNumber())
                .id(id)
                .build();
    }

}