package com.example.course_enrollment_system.service;

import com.example.course_enrollment_system.entity.Course;
import com.example.course_enrollment_system.entity.Student;
import com.example.course_enrollment_system.entity.Enrollment;
import com.example.course_enrollment_system.repository.EnrollmentRepository;
import com.example.course_enrollment_system.exception.EnrollmentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EnrollmentServiceTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @InjectMocks
    private EnrollmentService enrollmentService;

    private Course course;
    private Student student;
    private Enrollment enrollment;

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
    }

    @Test
    void getAllEnrollments_ShouldReturnListOfEnrollments() {
        // Arrange
        when(enrollmentRepository.findAll()).thenReturn(Arrays.asList(enrollment));

        // Act
        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();

        // Assert
        assertFalse(enrollments.isEmpty());
        assertEquals(1, enrollments.size());
        assertEquals(1L, enrollments.get(0).getCourse().getId());
        assertEquals(1L, enrollments.get(0).getStudent().getId());
        verify(enrollmentRepository, times(1)).findAll();
    }

    @Test
    void getEnrollmentById_WhenEnrollmentExists_ShouldReturnEnrollment() {
        // Arrange
        when(enrollmentRepository.findById(1L)).thenReturn(Optional.of(enrollment));

        // Act
        Enrollment result = enrollmentService.getEnrollmentById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getCourse().getId());
        assertEquals(1L, result.getStudent().getId());
        verify(enrollmentRepository, times(1)).findById(1L);
    }

    @Test
    void createEnrollment_WhenNotEnrolled_ShouldReturnCreatedEnrollment() {
        // Arrange
        when(enrollmentRepository.existsByCourseIdAndStudentId(1L, 1L)).thenReturn(false);
        when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(enrollment);

        // Act
        Enrollment result = enrollmentService.createEnrollment(enrollment);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getCourse().getId());
        assertEquals(1L, result.getStudent().getId());
        verify(enrollmentRepository, times(1)).save(any(Enrollment.class));
    }

    @Test
    void createEnrollment_WhenAlreadyEnrolled_ShouldThrowException() {
        // Arrange
        when(enrollmentRepository.existsByCourseIdAndStudentId(1L, 1L)).thenReturn(true);

        // Act & Assert
        assertThrows(EnrollmentException.class, () -> 
            enrollmentService.createEnrollment(enrollment)
        );
        verify(enrollmentRepository, never()).save(any(Enrollment.class));
    }

    @Test
    void updateEnrollment_WhenEnrollmentExists_ShouldReturnUpdatedEnrollment() {
        // Arrange
        Enrollment updatedEnrollment = new Enrollment();
        updatedEnrollment.setId(1L);
        updatedEnrollment.setCourse(course);
        updatedEnrollment.setStudent(student);
        updatedEnrollment.setEnrollmentDate(LocalDateTime.now());

        when(enrollmentRepository.findById(1L)).thenReturn(Optional.of(enrollment));
        when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(updatedEnrollment);

        // Act
        Enrollment result = enrollmentService.updateEnrollment(1L, updatedEnrollment);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getCourse().getId());
        assertEquals(1L, result.getStudent().getId());
        verify(enrollmentRepository, times(1)).findById(1L);
        verify(enrollmentRepository, times(1)).save(any(Enrollment.class));
    }

    @Test
    void deleteEnrollment_WhenEnrollmentExists_ShouldReturnTrue() {
        // Arrange
        when(enrollmentRepository.existsById(1L)).thenReturn(true);
        doNothing().when(enrollmentRepository).deleteById(1L);

        // Act
        boolean result = enrollmentService.deleteEnrollment(1L);

        // Assert
        assertTrue(result);
        verify(enrollmentRepository, times(1)).existsById(1L);
        verify(enrollmentRepository, times(1)).deleteById(1L);
    }

    @Test
    void getEnrollmentsByCourse_ShouldReturnListOfEnrollments() {
        // Arrange
        when(enrollmentRepository.findByCourseId(1L)).thenReturn(Arrays.asList(enrollment));

        // Act
        List<Enrollment> enrollments = enrollmentService.getEnrollmentsByCourse(1L);

        // Assert
        assertFalse(enrollments.isEmpty());
        assertEquals(1, enrollments.size());
        assertEquals(1L, enrollments.get(0).getCourse().getId());
        verify(enrollmentRepository, times(1)).findByCourseId(1L);
    }

    @Test
    void getEnrollmentsByStudent_ShouldReturnListOfEnrollments() {
        // Arrange
        when(enrollmentRepository.findByStudentId(1L)).thenReturn(Arrays.asList(enrollment));

        // Act
        List<Enrollment> enrollments = enrollmentService.getEnrollmentsByStudent(1L);

        // Assert
        assertFalse(enrollments.isEmpty());
        assertEquals(1, enrollments.size());
        assertEquals(1L, enrollments.get(0).getStudent().getId());
        verify(enrollmentRepository, times(1)).findByStudentId(1L);
    }
} 