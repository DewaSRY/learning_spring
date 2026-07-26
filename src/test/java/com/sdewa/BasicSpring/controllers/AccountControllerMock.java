package com.sdewa.BasicSpring.controllers;

import com.sdewa.BasicSpring.models.AccountCreateRequest;
import com.sdewa.BasicSpring.models.AccountsEntity;

public class AccountControllerMock {
    
    public static AccountCreateRequest getMockAccountController(String username, String number) {
        return AccountCreateRequest.builder()
                .name(username)
                .number(number)
                .build();
    }

    public static AccountsEntity getMockAccountControllerWithIdAndNumber(Long id, String number) {
        return AccountsEntity.builder()
                .name("Mock Account with ID and Number")
                .number(number)
                .id(id)
                .build();
    }
    
    public static AccountsEntity getMockAccountControllerWithIdAndNumberAndName(
            AccountCreateRequest accountCreateRequest, long id) {
        return AccountsEntity.builder()
                .name(accountCreateRequest.getName())
                .number(accountCreateRequest.getNumber())
                .id(id)
                .build();
    }
}
