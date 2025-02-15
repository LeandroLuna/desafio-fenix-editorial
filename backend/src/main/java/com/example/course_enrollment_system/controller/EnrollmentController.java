package com.example.course_enrollment_system.controller;

import com.example.course_enrollment_system.dto.EnrollmentDTO;
import com.example.course_enrollment_system.entity.Enrollment;
import com.example.course_enrollment_system.service.CourseService;
import com.example.course_enrollment_system.service.EnrollmentService;
import com.example.course_enrollment_system.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@Tag(name = "Matrículas", description = "API para gerenciamento de matrículas")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;
    private final CourseService courseService;
    private final StudentService studentService;

    public EnrollmentController(EnrollmentService enrollmentService, CourseService courseService, StudentService studentService) {
        this.enrollmentService = enrollmentService;
        this.courseService = courseService;
        this.studentService = studentService;
    }

    @Operation(summary = "Criar nova matrícula")
    @PostMapping
    public ResponseEntity<EnrollmentDTO> createEnrollment(@Valid @RequestBody EnrollmentDTO enrollmentDTO) {
        Enrollment enrollment = new Enrollment();
        enrollment.setCourse(courseService.getCourseById(enrollmentDTO.getCourseId()).orElseThrow(() -> new RuntimeException("Course not found")));
        enrollment.setStudent(studentService.getStudentById(enrollmentDTO.getStudentId()).orElseThrow(() -> new RuntimeException("Student not found")));
        enrollment.setEnrollmentDate(LocalDateTime.now());
        Enrollment createdEnrollment = enrollmentService.createEnrollment(enrollment);

        enrollmentDTO.setId(createdEnrollment.getId());
        enrollmentDTO.setEnrollmentDate(createdEnrollment.getEnrollmentDate());
        return ResponseEntity.status(HttpStatus.CREATED).body(enrollmentDTO);
    }

    @Operation(summary = "Listar todas as matrículas")
    @GetMapping
    public ResponseEntity<List<EnrollmentDTO>> getAllEnrollments() {
        List<EnrollmentDTO> enrollments = enrollmentService.getAllEnrollments().stream().map(enrollment -> {
            EnrollmentDTO dto = new EnrollmentDTO();
            dto.setId(enrollment.getId());
            dto.setCourseId(enrollment.getCourse().getId());
            dto.setStudentId(enrollment.getStudent().getId());
            dto.setEnrollmentDate(enrollment.getEnrollmentDate());
            return dto;
        }).toList();

        return enrollments.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                                     : new ResponseEntity<>(enrollments, HttpStatus.OK);
    }

    @Operation(summary = "Buscar matrícula por ID")
    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentDTO> getEnrollmentById(@PathVariable Long id) {
        Enrollment enrollment = enrollmentService.getEnrollmentById(id);
        if (enrollment != null) {
            EnrollmentDTO dto = new EnrollmentDTO();
            dto.setId(enrollment.getId());
            dto.setCourseId(enrollment.getCourse().getId());
            dto.setStudentId(enrollment.getStudent().getId());
            dto.setEnrollmentDate(enrollment.getEnrollmentDate());
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Atualizar matrícula existente")
    @PutMapping("/{id}")
    public ResponseEntity<EnrollmentDTO> updateEnrollment(@PathVariable Long id, @Valid @RequestBody EnrollmentDTO enrollmentDTO) {
        Enrollment enrollment = new Enrollment();
        enrollment.setId(id);
        enrollment.setCourse(courseService.getCourseById(enrollmentDTO.getCourseId()).orElseThrow(() -> new RuntimeException("Course not found")));
        enrollment.setStudent(studentService.getStudentById(enrollmentDTO.getStudentId()).orElseThrow(() -> new RuntimeException("Student not found")));
        enrollment.setEnrollmentDate(enrollmentDTO.getEnrollmentDate());

        Enrollment updatedEnrollment = enrollmentService.updateEnrollment(id, enrollment);

        if (updatedEnrollment != null) {
            enrollmentDTO.setId(updatedEnrollment.getId());
            enrollmentDTO.setEnrollmentDate(updatedEnrollment.getEnrollmentDate());
            return new ResponseEntity<>(enrollmentDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Excluir matrícula")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable Long id) {
        boolean deleted = enrollmentService.deleteEnrollment(id);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                       : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}