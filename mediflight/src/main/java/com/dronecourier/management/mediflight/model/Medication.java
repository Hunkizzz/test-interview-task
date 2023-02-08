package com.dronecourier.management.mediflight.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Pattern;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "medication")
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name = "name")
    @Pattern(regexp = "[a-zA-Z0-9-_]+")
    private String name;

    @Column(name = "weight")
    @Max(500)
    private long weight;

    @Column(name = "code")
    @Pattern(regexp = "[A-Z_0-9]+")
    private String code;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "drone_id")
    private UUID droneId;

}