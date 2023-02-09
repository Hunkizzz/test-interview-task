package com.dronecourier.management.mediflight.controller;

import com.dronecourier.management.mediflight.dto.ImageDto;
import com.dronecourier.management.mediflight.service.MedicationServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MedicationController.class)
class MedicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicationServiceImpl medicationService;

    static String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testSetImage() throws Exception {
        ImageDto imageDto = new ImageDto();
        imageDto.setImageNumber("1");

        mockMvc.perform(post("/medication/image")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(imageDto)))
                .andExpect(status().isOk());


        verify(medicationService, times(1)).setImage(imageDto);

    }

}