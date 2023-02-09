package com.dronecourier.management.mediflight.service;

import com.dronecourier.management.mediflight.dto.ImageDto;
import com.dronecourier.management.mediflight.model.Medication;
import com.dronecourier.management.mediflight.repository.MedicationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MedicationServiceImplTest {
    @Mock
    private MedicationRepository medicationRepository;

    @InjectMocks
    private MedicationServiceImpl medicationService;

    @Test
    void setImage() throws IOException {
        ImageDto imageDto = new ImageDto();
        imageDto.setUuid("8d9f9b3a-3b3d-4a30-90a0-2f2e21f0b2a8");
        imageDto.setImageNumber("1");
        Medication medication = new Medication();
        medication.setId(UUID.fromString(imageDto.getUuid()));
        when(medicationRepository.findById(UUID.fromString(imageDto.getUuid()))).thenReturn(Optional.of(medication));
        medicationService.setImage(imageDto);
        verify(medicationRepository, times(1)).save(medication);
    }

    @Test
    void getMedicationList() {
        List<String> ids = Arrays.asList("8d9f9b3a-3b3d-4a30-90a0-2f2e21f0b2a8",
                "8d9f9b3a-3b3d-4a30-90a0-2f2e21f0b2b8");
        Medication medication1 = new Medication();
        medication1.setId(UUID.fromString(ids.get(0)));
        Medication medication2 = new Medication();
        medication2.setId(UUID.fromString(ids.get(1)));
        List<Medication> medications = Arrays.asList(medication1, medication2);
        when(medicationRepository.findAllById(any())).thenReturn(medications);
        List<Medication> result = medicationService.getMedicationList(ids);
        assertEquals(medications, result);
    }

    @Test
    void setSupplierForMedication() {
        List<Medication> medications = Arrays.asList(
                new Medication(),
                new Medication());
        medicationService.setSupplierForMedication(medications);
        verify(medicationRepository, times(1)).saveAll(medications);
    }

    @Test
    void getTransferedMedications() {
        List<Medication> medications = Arrays.asList(
                new Medication(),
                new Medication());
        String droneID = "8d9f9b3a-3b3d-4a30-90a0-2f2e21f0b2a8";
        when(medicationRepository.findAllByDroneId(UUID.fromString(droneID))).thenReturn(medications);
        List<Medication> result = medicationService.getTransferedMedications(droneID);
        assertEquals(medications, result);
    }

    @Test
    void save() {
        List<Medication> medications = Arrays.asList(
                new Medication(),
                new Medication());
        medicationService.save(medications);
        verify(medicationRepository, times(1)).saveAll(medications);
    }
}