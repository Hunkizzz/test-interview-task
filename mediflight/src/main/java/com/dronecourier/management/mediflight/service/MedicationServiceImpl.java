package com.dronecourier.management.mediflight.service;

import com.dronecourier.management.mediflight.dto.ImageDto;
import com.dronecourier.management.mediflight.exception.EntityNotFoundException;
import com.dronecourier.management.mediflight.model.Medication;
import com.dronecourier.management.mediflight.repository.MedicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class MedicationServiceImpl implements MedicationService {
    private final MedicationRepository medicationRepository;

    public MedicationServiceImpl(MedicationRepository medicationRepository) {
        this.medicationRepository = medicationRepository;
    }

    @Override
    @Transactional
    public void setImage(ImageDto imageDto) throws IOException {
        Medication medication = medicationRepository.findById(UUID.fromString(imageDto.getUuid()))
                .orElseThrow(() -> new EntityNotFoundException(""));
        if (medication != null) {
            InputStream resourceAsStream = getClass().getResourceAsStream("/images/" + imageDto.getImageNumber() + ".jpeg");
            if (resourceAsStream == null) {
                throw new FileNotFoundException("/images/" + imageDto.getImageNumber());
            }
            try {
                byte[] imageBytes = resourceAsStream.readAllBytes();
                medication.setImage(imageBytes);
                medicationRepository.save(medication);
            } catch (IOException e) {
                throw new IOException(e);
            }
        }
    }

    @Override
    public List<Medication> getMedicationList(List<String> ids) {
        List<UUID> uuids = ids.stream()
                .map(UUID::fromString)
                .collect(Collectors.toList());
        return medicationRepository.findAllById(uuids);
    }

    @Override
    @Transactional
    public void setSupplierForMedication(List<Medication> medications) {
        medicationRepository.saveAll(medications);
    }

    @Override
    public List<Medication> getTransferedMedications(String droneID) {
        return medicationRepository.findAllByDroneId(UUID.fromString(droneID));
    }

    @Override
    @Transactional
    public void save(List<Medication> medications) {
        medicationRepository.saveAll(medications);
    }
}