package com.example.course_enrollment_system.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EnrollmentDTO {
    private Long id;

    @NotNull(message = "Course ID is required.")
    private Long courseId;

    @NotNull(message = "Student ID is required.")
    private Long studentId;

    private LocalDateTime enrollmentDate;
}
