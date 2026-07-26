package com.sdewa.BasicSpring.controllers;

import org.springframework.http.MediaType;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdewa.BasicSpring.models.AccountsEntity;
import com.sdewa.BasicSpring.models.AccountCreateRequest;
import com.sdewa.BasicSpring.services.AccountServices;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(AccountController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AccountControllerTest {

    private static final String BASE_URL = "/api/v1/accounts";

    @MockitoBean
    private AccountServices accountServices;

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testGetAccounts() throws Exception {

        when(accountServices.getAccounts()).thenReturn(Collections.emptyList());
        mockMvc.perform(get(BASE_URL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAccountById() throws Exception {
        Long accountId = 1L;
        AccountsEntity mockAccount = AccountControllerMock.getMockAccountControllerWithIdAndNumberAndName(
                AccountControllerMock.getMockAccountController("Mock User", "12345"), accountId);
        when(accountServices.getAccountById(accountId)).thenReturn(Optional.of(mockAccount));

        mockMvc.perform(get(BASE_URL + "/" + accountId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAccountByIdNotFound() throws Exception {
            Long accountId = 1L;
            when(accountServices.getAccountById(accountId)).thenReturn(Optional.empty());

            mockMvc.perform(get(BASE_URL + "/" + accountId)
                            .accept(MediaType.APPLICATION_JSON))
                            .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateAccount() throws Exception {
        AccountCreateRequest mockAccountRequest = AccountControllerMock.getMockAccountController("Mock User", "12345");
        AccountsEntity mockAccountEntity = AccountControllerMock
                .getMockAccountControllerWithIdAndNumberAndName(mockAccountRequest, 1L);

        when(accountServices.createAccount(any(AccountCreateRequest.class))).thenReturn(Optional.of(mockAccountEntity));

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockAccountRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateAccount() throws Exception {
        Long accountId = 1L;
        AccountCreateRequest mockAccountRequest = AccountControllerMock.getMockAccountController("Updated User",
                "67890");
        AccountsEntity mockAccountEntity = AccountControllerMock
                .getMockAccountControllerWithIdAndNumberAndName(mockAccountRequest, accountId);

        when(accountServices.updateAccount(eq(accountId), any(AccountCreateRequest.class)))
                .thenReturn(Optional.of(mockAccountEntity));

        mockMvc.perform(put(BASE_URL + "/" + accountId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockAccountRequest)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteAccount() throws Exception {
        Long accountId = 1L;
        AccountsEntity mockAccountEntity = AccountControllerMock.getMockAccountControllerWithIdAndNumberAndName(
                AccountControllerMock.getMockAccountController("Mock User", "12345"), accountId);
        when(accountServices.deleteAccount(accountId)).thenReturn(Optional.of(mockAccountEntity));

        mockMvc.perform(delete(BASE_URL + "/" + accountId))
                .andExpect(status().isNoContent());
    }
}