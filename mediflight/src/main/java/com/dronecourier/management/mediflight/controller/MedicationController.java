package com.dronecourier.management.mediflight.controller;

import com.dronecourier.management.mediflight.dto.ImageDto;
import com.dronecourier.management.mediflight.service.MedicationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/medication")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class MedicationController {

    MedicationService medicationService;

    @PostMapping("/image")
    public ResponseEntity<String> setImage(@RequestBody ImageDto imageDto) throws IOException {
        medicationService.setImage(imageDto);
        return ResponseEntity.ok("Ok");
    }
}