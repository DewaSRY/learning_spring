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
import com.sdewa.BasicSpring.models.BeverageCreateRequest;
import com.sdewa.BasicSpring.models.BeverageEntity;
import com.sdewa.BasicSpring.services.BeverageServices;

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

@WebMvcTest(BeverageController.class)
@AutoConfigureMockMvc(addFilters = false)
public class BeverageControllerTest {

        private static final String BASE_URL = "/api/v1/beverages";

        @MockitoBean
        private BeverageServices beverageServices;

        @Autowired
        MockMvc mockMvc;

        ObjectMapper objectMapper = new ObjectMapper();

        @Test
        public void testGetBeverageById() throws Exception {
                Long beverageId = 1L;
                BeverageEntity mockBeverage = BeverageControllerMock.getMockBeverageControllerWithIdAndNumber(
                                beverageId,
                                "12345");

                when(beverageServices.getBeverageById(beverageId)).thenReturn(Optional.of(mockBeverage));
                mockMvc.perform(get(BASE_URL + "/" + beverageId)
                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk());
        }

        @Test
        void testGetBeverageByIdNotFound() throws Exception {
                Long beverageId = 1L;
                when(beverageServices.getBeverageById(beverageId)).thenReturn(Optional.empty());
                mockMvc.perform(get(BASE_URL + "/" + beverageId)
                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNotFound());
        }

        @Test
        public void testGetBeverages() throws Exception {
                when(beverageServices.getBeverages()).thenReturn(Collections.emptyList());
                mockMvc.perform(get(BASE_URL)
                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk());
        }

        @Test
        public void testCreateBeverage() throws Exception {
                long beverageId = 1L;
                BeverageCreateRequest mockBeverageRequest = BeverageControllerMock.getMockBeverageController();
                BeverageEntity mockBeverageEntity = BeverageControllerMock
                                .getMockBeverageControllerWithIdAndNumberAndName(mockBeverageRequest, beverageId);

                when(beverageServices.createBeverage(any(BeverageCreateRequest.class)))
                                .thenReturn(Optional.of(mockBeverageEntity));

                mockMvc.perform(post(BASE_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(mockBeverageRequest)))
                                .andExpect(status().isCreated());
        }

        @Test
        public void testUpdateBeverage() throws Exception {
                long beverageId = 1L;
                BeverageCreateRequest mockBeverageRequest = BeverageControllerMock.getMockBeverageController();
                BeverageEntity mockBeverageEntity = BeverageControllerMock
                                .getMockBeverageControllerWithIdAndNumberAndName(mockBeverageRequest, beverageId);

                when(beverageServices.updateBeverage(eq(beverageId), any(BeverageCreateRequest.class)))
                                .thenReturn(Optional.of(mockBeverageEntity));

                mockMvc.perform(put(BASE_URL + "/" + beverageId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(mockBeverageRequest)))
                                .andExpect(status().isOk());

        }

        @Test
        public void testDeleteBeverage() throws Exception {
                long beverageId = 1L;
                BeverageEntity mockBeverageEntity = BeverageControllerMock
                                .getMockBeverageControllerWithIdAndNumber(beverageId, "12345");

                when(beverageServices.deleteBeverage(beverageId)).thenReturn(Optional.of(mockBeverageEntity));

                mockMvc.perform(delete(BASE_URL + "/" + beverageId))
                                .andExpect(status().isNoContent());
        }
}
