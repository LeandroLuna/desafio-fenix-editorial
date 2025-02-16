package com.example.course_enrollment_system.controller;

import com.example.course_enrollment_system.dto.EnrollmentDTO;
import com.example.course_enrollment_system.entity.Course;
import com.example.course_enrollment_system.entity.Student;
import com.example.course_enrollment_system.entity.Enrollment;
import com.example.course_enrollment_system.service.CourseService;
import com.example.course_enrollment_system.service.StudentService;
import com.example.course_enrollment_system.service.EnrollmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EnrollmentControllerTest {

    @Mock
    private EnrollmentService enrollmentService;

    @Mock
    private CourseService courseService;

    @Mock
    private StudentService studentService;

    @InjectMocks
    private EnrollmentController enrollmentController;

    private Course course;
    private Student student;
    private Enrollment enrollment;
    private EnrollmentDTO enrollmentDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        course = new Course();
        course.setId(1L);
        course.setName("Java");
        course.setDescription("Java Programming");
        course.setDuration(10);

        student = new Student();
        student.setId(1L);
        student.setName("Leandro");
        student.setEmail("leandro@email.com");

        enrollment = new Enrollment();
        enrollment.setId(1L);
        enrollment.setCourse(course);
        enrollment.setStudent(student);
        enrollment.setEnrollmentDate(LocalDateTime.now());

        enrollmentDTO = new EnrollmentDTO();
        enrollmentDTO.setId(1L);
        enrollmentDTO.setCourseId(1L);
        enrollmentDTO.setStudentId(1L);
        enrollmentDTO.setEnrollmentDate(LocalDateTime.now());
    }

    @Test
    void getAllEnrollments_ShouldReturnListOfEnrollments() {
        // Arrange
        when(enrollmentService.getAllEnrollments()).thenReturn(Arrays.asList(enrollment));

        // Act
        ResponseEntity<List<EnrollmentDTO>> response = enrollmentController.getAllEnrollments();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(1L, response.getBody().get(0).getCourseId());
        assertEquals(1L, response.getBody().get(0).getStudentId());
    }

    @Test
    void getEnrollmentById_WhenEnrollmentExists_ShouldReturnEnrollment() {
        // Arrange
        when(enrollmentService.getEnrollmentById(1L)).thenReturn(enrollment);

        // Act
        ResponseEntity<EnrollmentDTO> response = enrollmentController.getEnrollmentById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getCourseId());
        assertEquals(1L, response.getBody().getStudentId());
    }

    @Test
    void createEnrollment_ShouldReturnCreatedEnrollment() {
        // Arrange
        when(courseService.getCourseById(1L)).thenReturn(Optional.of(course));
        when(studentService.getStudentById(1L)).thenReturn(Optional.of(student));
        when(enrollmentService.createEnrollment(any(Enrollment.class))).thenReturn(enrollment);

        // Act
        ResponseEntity<EnrollmentDTO> response = enrollmentController.createEnrollment(enrollmentDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
        assertEquals(1L, response.getBody().getCourseId());
        assertEquals(1L, response.getBody().getStudentId());
    }

    @Test
    void updateEnrollment_WhenEnrollmentExists_ShouldReturnUpdatedEnrollment() {
        // Arrange
        when(courseService.getCourseById(1L)).thenReturn(Optional.of(course));
        when(studentService.getStudentById(1L)).thenReturn(Optional.of(student));
        when(enrollmentService.updateEnrollment(eq(1L), any(Enrollment.class))).thenReturn(enrollment);

        // Act
        ResponseEntity<EnrollmentDTO> response = enrollmentController.updateEnrollment(1L, enrollmentDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
        assertEquals(1L, response.getBody().getCourseId());
        assertEquals(1L, response.getBody().getStudentId());
    }

    @Test
    void deleteEnrollment_WhenEnrollmentExists_ShouldReturnNoContent() {
        // Arrange
        when(enrollmentService.deleteEnrollment(1L)).thenReturn(true);

        // Act
        ResponseEntity<Void> response = enrollmentController.deleteEnrollment(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void createEnrollment_WhenCourseNotFound_ShouldThrowException() {
        // Arrange
        when(courseService.getCourseById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> 
            enrollmentController.createEnrollment(enrollmentDTO)
        );
    }

    @Test
    void createEnrollment_WhenStudentNotFound_ShouldThrowException() {
        // Arrange
        when(courseService.getCourseById(1L)).thenReturn(Optional.of(course));
        when(studentService.getStudentById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> 
            enrollmentController.createEnrollment(enrollmentDTO)
        );
    }
} 