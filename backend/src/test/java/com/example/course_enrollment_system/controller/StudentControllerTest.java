package com.example.course_enrollment_system.controller;

import com.example.course_enrollment_system.dto.StudentDTO;
import com.example.course_enrollment_system.entity.Student;
import com.example.course_enrollment_system.service.StudentService;
import com.example.course_enrollment_system.service.EnrollmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @Mock
    private EnrollmentService enrollmentService;

    @InjectMocks
    private StudentController studentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllStudents_ShouldReturnListOfStudents() {
        // Arrange
        Student student1 = new Student();
        student1.setId(1L);
        student1.setName("Leandro");
        student1.setEmail("leandro@email.com");

        Student student2 = new Student();
        student2.setId(2L);
        student2.setName("Luna");
        student2.setEmail("luna@email.com");

        when(studentService.getAllStudents()).thenReturn(Arrays.asList(student1, student2));

        // Act
        ResponseEntity<List<StudentDTO>> response = studentController.getAllStudents();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("Leandro", response.getBody().get(0).getName());
        assertEquals("Luna", response.getBody().get(1).getName());
    }

    @Test
    void getStudentById_WhenStudentExists_ShouldReturnStudent() {
        // Arrange
        Student student = new Student();
        student.setId(1L);
        student.setName("Leandro");
        student.setEmail("leandro@email.com");

        when(studentService.getStudentById(1L)).thenReturn(Optional.of(student));

        // Act
        ResponseEntity<StudentDTO> response = studentController.getStudentById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Leandro", response.getBody().getName());
    }

    @Test
    void createStudent_ShouldReturnCreatedStudent() {
        // Arrange
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("Leandro");
        studentDTO.setEmail("leandro@email.com");

        Student student = new Student();
        student.setId(1L);
        student.setName("Leandro");
        student.setEmail("leandro@email.com");

        when(studentService.createStudent(any(Student.class))).thenReturn(student);

        // Act
        ResponseEntity<StudentDTO> response = studentController.createStudent(studentDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
        assertEquals("Leandro", response.getBody().getName());
    }

    @Test
    void updateStudent_WhenStudentExists_ShouldReturnUpdatedStudent() {
        // Arrange
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("Updated Leandro");
        studentDTO.setEmail("leandro.updated@email.com");

        Student updatedStudent = new Student();
        updatedStudent.setId(1L);
        updatedStudent.setName("Updated Leandro");
        updatedStudent.setEmail("leandro.updated@email.com");

        when(studentService.updateStudent(eq(1L), any(Student.class))).thenReturn(updatedStudent);

        // Act
        ResponseEntity<StudentDTO> response = studentController.updateStudent(1L, studentDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated Leandro", response.getBody().getName());
    }

    @Test
    void deleteStudent_WhenStudentExists_ShouldReturnNoContent() {
        // Arrange
        when(studentService.deleteStudent(1L)).thenReturn(true);

        // Act
        ResponseEntity<Void> response = studentController.deleteStudent(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
} 