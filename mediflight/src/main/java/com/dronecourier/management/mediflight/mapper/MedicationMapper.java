package com.dronecourier.management.mediflight.mapper;

import com.dronecourier.management.mediflight.dto.MedicationWithoutImageDto;
import com.dronecourier.management.mediflight.model.Medication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MedicationMapper {
    MedicationWithoutImageDto medicationToMedicationWithoutImageDto(Medication medication);

    Medication medicationWithoutImageDtoToMedication(MedicationWithoutImageDto medicationWithoutImageDto);
}