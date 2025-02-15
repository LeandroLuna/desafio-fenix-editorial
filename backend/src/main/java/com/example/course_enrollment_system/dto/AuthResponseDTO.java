package com.example.course_enrollment_system.dto;

import lombok.Data;

@Data
public class AuthResponseDTO {
    private String token;
    private String type = "Bearer";

    public AuthResponseDTO(String token) {
        this.token = token;
    }
} 