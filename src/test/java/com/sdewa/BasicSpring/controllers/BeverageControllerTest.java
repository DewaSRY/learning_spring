package com.sdewa.BasicSpring.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sdewa.BasicSpring.models.BeverageEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdewa.BasicSpring.models.BeverageCreateRequest;
import com.sdewa.BasicSpring.services.BeverageServices;


public class BeverageControllerTest {

        private MockMvc mockMvc;

        @Mock
        private BeverageServices beverageServices;

        private ObjectMapper objectMapper;


        @BeforeEach
        public void setUp() {
                MockitoAnnotations.openMocks(this);
                this.mockMvc = MockMvcBuilders.standaloneSetup(new BeverageController(beverageServices)).build();
                objectMapper = new ObjectMapper();
        }

        @Test
        public void testGetBeverages_Success() throws Exception {
                BeverageEntity beverage1 = BeverageEntity.builder()
                                .id(1L)
                                .name("Coca Cola")
                                .description("Carbonated soft drink")
                                .number("001")
                                .createdAt(LocalDateTime.now())
                                .build();

                BeverageEntity beverage2 = BeverageEntity.builder()
                                .id(2L)
                                .name("Sprite")
                                .description("Lemon-lime flavored soft drink")
                                .number("002")
                                .createdAt(LocalDateTime.now())
                                .build();

                List<BeverageEntity> beverages = Arrays.asList(beverage1, beverage2);

                when(beverageServices.getBeverages()).thenReturn(beverages);

                mockMvc.perform(get("/api/v1/beverages"))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
   
        }

        @Test
        public void testGetBeverages_Empty() throws Exception {
                when(beverageServices.getBeverages()).thenReturn(java.util.Collections.emptyList());

                mockMvc.perform(get("/api/v1/beverages"))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
                 
        }

        @Test
        public void testGetBeverageById_Found() throws Exception {
                BeverageEntity beverage = BeverageEntity.builder()
                                .id(1L)
                                .name("Coca Cola")
                                .description("Carbonated soft drink")
                                .number("001")
                                .createdAt(LocalDateTime.now())
                                .build();

                when(beverageServices.getBeverageById(1L)).thenReturn(Optional.of(beverage));

                mockMvc.perform(get("/api/v1/beverages/1"))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.id").value(1))
                                .andExpect(jsonPath("$.name").value("Coca Cola"))
                                .andExpect(jsonPath("$.description").value("Carbonated soft drink"))
                                .andExpect(jsonPath("$.number").value("001"));
        }

        @Test
        public void testGetBeverageById_NotFound() throws Exception {
                when(beverageServices.getBeverageById(999L)).thenReturn(Optional.empty());

                mockMvc.perform(get("/api/v1/beverages/999"))
                                .andExpect(status().isNotFound());
        }

        @Test
        public void testCreateBeverage_Success() throws Exception {
                BeverageCreateRequest requestBody = BeverageCreateRequest.builder()
                                .description("Fruit flavored drink")
                                .name("Fanta")
                                .number("003")
                                .build();

                BeverageEntity createdBeverage = BeverageEntity.builder()
                                .id(3L)
                                .name("Fanta")
                                .description("Fruit flavored drink")
                                .number("003")
                                .createdAt(LocalDateTime.now())
                                .build();

                when(beverageServices.createBeverage(any(BeverageCreateRequest.class))) 
                                .thenReturn(Optional.of(createdBeverage));

                mockMvc.perform(post("/api/v1/beverages")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestBody)))
                                .andExpect(status().isCreated())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.id").value(3))
                                .andExpect(jsonPath("$.name").value("Fanta"))
                                .andExpect(jsonPath("$.description").value("Fruit flavored drink"));
        }

        @Test
        public void testCreateBeverage_BadRequest() throws Exception {
                String requestBody = "{\"name\":\"InvalidBeverage\",\"description\":\"Invalid drink\",\"number\":\"999\"}";

                when(beverageServices.createBeverage(any(BeverageCreateRequest.class)))
                                .thenReturn(Optional.empty());

                mockMvc.perform(post("/api/v1/beverages")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                                .andExpect(status().isBadRequest());
        }

        @Test
        public void testUpdateBeverage_Success() throws Exception {
                String requestBody = "{\"name\":\"Coca Cola Updated\",\"description\":\"Updated description\",\"number\":\"001\"}";

                BeverageEntity updatedBeverage = BeverageEntity.builder()
                                .id(1L)
                                .name("Coca Cola Updated")
                                .description("Updated description")
                                .number("001")
                                .createdAt(LocalDateTime.now())
                                .build();

                when(beverageServices.updateBeverage(eq(1L), any(BeverageCreateRequest.class)))
                                .thenReturn(Optional.of(updatedBeverage));

                mockMvc.perform(put("/api/v1/beverages/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.id").value(1))
                                .andExpect(jsonPath("$.name").value("Coca Cola Updated"))
                                .andExpect(jsonPath("$.description").value("Updated description"));
        }

        @Test
        public void testUpdateBeverage_NotFound() throws Exception {
                String requestBody = "{\"name\":\"Non Existent\",\"description\":\"Does not exist\",\"number\":\"999\"}";

                when(beverageServices.updateBeverage(eq(999L), any(BeverageCreateRequest.class)))
                                .thenReturn(Optional.empty());

                mockMvc.perform(put("/api/v1/beverages/999")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                                .andExpect(status().isNotFound());
        }


        @Test
        public void testDeleteBeverage_Success() throws Exception {
                BeverageEntity deletedBeverage = BeverageEntity.builder()
                                .id(1L)
                                .name("Coca Cola")
                                .description("Carbonated soft drink")
                                .number("001")
                                .createdAt(LocalDateTime.now())
                                .build();

                when(beverageServices.deleteBeverage(1L))
                                .thenReturn(Optional.of(deletedBeverage));

                mockMvc.perform(delete("/api/v1/beverages/1"))
                                .andExpect(status().isNoContent());
        }

        @Test
        public void testDeleteBeverage_NotFound() throws Exception {
                when(beverageServices.deleteBeverage(999L))
                                .thenReturn(Optional.empty());

                mockMvc.perform(delete("/api/v1/beverages/999"))
                                .andExpect(status().isNotFound());
        }

}
