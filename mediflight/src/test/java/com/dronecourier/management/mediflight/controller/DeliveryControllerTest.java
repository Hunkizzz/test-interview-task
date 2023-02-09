package com.dronecourier.management.mediflight.controller;

import com.dronecourier.management.mediflight.dto.DeliveryDto;
import com.dronecourier.management.mediflight.service.DeliveryServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DeliveryController.class)
class DeliveryControllerTest {
    @MockBean
    DeliveryServiceImpl deliveryService;
    @Autowired
    private MockMvc mockMvc;

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void loadDrone_withValidRequest_shouldReturn200() throws Exception {
        String droneId = UUID.randomUUID().toString();
        String medicationId = UUID.randomUUID().toString();
        List<String> medicationIds = new ArrayList<>();
        medicationIds.add(medicationId);
        DeliveryDto deliveryDto = new DeliveryDto(droneId, medicationIds);
        mockMvc.perform(post("/delivery/load")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(deliveryDto)))
                .andExpect(status().isOk());
    }

    @Test
    void deliverDrone_withValidRequest_shouldReturn200() throws Exception {
        String droneId = UUID.randomUUID().toString();
        mockMvc.perform(post("/delivery/deliver/{droneId}", droneId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void returnDrone_withValidRequest_shouldReturn200() throws Exception {
        String droneId = UUID.randomUUID().toString();
        mockMvc.perform(post("/delivery/return/{droneId}", droneId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}