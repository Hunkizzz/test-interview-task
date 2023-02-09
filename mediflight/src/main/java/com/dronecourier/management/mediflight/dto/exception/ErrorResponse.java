package com.dronecourier.management.mediflight.dto.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private String uuid;
    private LocalDateTime timestamp;
    private String message;
}