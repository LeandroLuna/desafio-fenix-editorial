package com.example.course_enrollment_system.service;

import com.example.course_enrollment_system.entity.Student;
import com.example.course_enrollment_system.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private Student student;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        student = new Student();
        student.setId(1L);
        student.setName("Leandro");
        student.setEmail("leandro@email.com");
    }

    @Test
    void getAllStudents_ShouldReturnListOfStudents() {
        // Arrange
        when(studentRepository.findAll()).thenReturn(Arrays.asList(student));

        // Act
        List<Student> students = studentService.getAllStudents();

        // Assert
        assertFalse(students.isEmpty());
        assertEquals(1, students.size());
        assertEquals("Leandro", students.get(0).getName());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void getStudentById_WhenStudentExists_ShouldReturnStudent() {
        // Arrange
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        // Act
        Optional<Student> result = studentService.getStudentById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Leandro", result.get().getName());
        assertEquals("leandro@email.com", result.get().getEmail());
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    void getStudentById_WhenStudentDoesNotExist_ShouldReturnEmpty() {
        // Arrange
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<Student> result = studentService.getStudentById(1L);

        // Assert
        assertFalse(result.isPresent());
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    void createStudent_ShouldReturnCreatedStudent() {
        // Arrange
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        // Act
        Student createdStudent = studentService.createStudent(student);

        // Assert
        assertNotNull(createdStudent);
        assertEquals("Leandro", createdStudent.getName());
        assertEquals("leandro@email.com", createdStudent.getEmail());
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void updateStudent_WhenStudentExists_ShouldReturnUpdatedStudent() {
        // Arrange
        Student updatedStudent = new Student();
        updatedStudent.setId(1L);
        updatedStudent.setName("John Updated");
        updatedStudent.setEmail("john.updated@example.com");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(updatedStudent);

        // Act
        Student result = studentService.updateStudent(1L, updatedStudent);

        // Assert
        assertNotNull(result);
        assertEquals("John Updated", result.getName());
        assertEquals("john.updated@example.com", result.getEmail());
        verify(studentRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void updateStudent_WhenStudentDoesNotExist_ShouldReturnNull() {
        // Arrange
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Student result = studentService.updateStudent(1L, student);

        // Assert
        assertNull(result);
        verify(studentRepository, times(1)).findById(1L);
        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    void deleteStudent_WhenStudentExists_ShouldReturnTrue() {
        // Arrange
        when(studentRepository.existsById(1L)).thenReturn(true);
        doNothing().when(studentRepository).deleteById(1L);

        // Act
        boolean result = studentService.deleteStudent(1L);

        // Assert
        assertTrue(result);
        verify(studentRepository, times(1)).existsById(1L);
        verify(studentRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteStudent_WhenStudentDoesNotExist_ShouldReturnFalse() {
        // Arrange
        when(studentRepository.existsById(1L)).thenReturn(false);

        // Act
        boolean result = studentService.deleteStudent(1L);

        // Assert
        assertFalse(result);
        verify(studentRepository, times(1)).existsById(1L);
        verify(studentRepository, never()).deleteById(anyLong());
    }
} 