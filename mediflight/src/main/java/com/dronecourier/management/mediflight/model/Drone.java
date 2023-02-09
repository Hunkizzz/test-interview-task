package com.dronecourier.management.mediflight.model;

import com.dronecourier.management.mediflight.enums.DroneModel;
import com.dronecourier.management.mediflight.enums.DroneState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "drone")
@NamedEntityGraph(name = "medication_entity_graph",
        attributeNodes = {
                @NamedAttributeNode("medications")
        }
)
public class Drone {
    @OneToMany(targetEntity = Medication.class)
    @JoinColumn(name = "drone_id")
    List<Medication> medications;

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "serial_number")
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "model")
    private DroneModel model;

    @Column(name = "weight_limit")
    private int weightLimit;

    @Column(name = "battery_capacity")
    private int batteryCapacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private DroneState state;
}