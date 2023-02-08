package com.dronecourier.management.mediflight.service;

import com.dronecourier.management.mediflight.dto.ImageDto;
import com.dronecourier.management.mediflight.model.Medication;

import java.io.IOException;
import java.util.List;

public interface MedicationService {
    void setImage(ImageDto imageDto) throws IOException;

    List<Medication> getMedicationList(List<String> ids);

    void setSupplierForMedication(List<Medication> medications);

    List<Medication> getTransferedMedications(String droneID);

    void save(List<Medication> medications);
}
