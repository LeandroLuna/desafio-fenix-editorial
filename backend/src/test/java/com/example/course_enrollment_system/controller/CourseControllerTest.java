package com.example.course_enrollment_system.controller;

import com.example.course_enrollment_system.dto.CourseDTO;
import com.example.course_enrollment_system.entity.Course;
import com.example.course_enrollment_system.service.CourseService;
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

class CourseControllerTest {

    @Mock
    private CourseService courseService;

    @Mock
    private EnrollmentService enrollmentService;

    @InjectMocks
    private CourseController courseController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCourses_ShouldReturnListOfCourses() {
        // Arrange
        Course course1 = new Course();
        course1.setId(1L);
        course1.setName("Java");
        course1.setDescription("Java Course");
        course1.setDuration(40);

        Course course2 = new Course();
        course2.setId(2L);
        course2.setName("Python");
        course2.setDescription("Python Course");
        course2.setDuration(30);

        when(courseService.getAllCourses()).thenReturn(Arrays.asList(course1, course2));

        // Act
        ResponseEntity<List<CourseDTO>> response = courseController.getAllCourses();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("Java", response.getBody().get(0).getName());
        assertEquals("Python", response.getBody().get(1).getName());
    }

    @Test
    void getCourseById_WhenCourseExists_ShouldReturnCourse() {
        // Arrange
        Course course = new Course();
        course.setId(1L);
        course.setName("Java");
        course.setDescription("Java Course");
        course.setDuration(40);

        when(courseService.getCourseById(1L)).thenReturn(Optional.of(course));

        // Act
        ResponseEntity<CourseDTO> response = courseController.getCourseById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Java", response.getBody().getName());
    }

    @Test
    void createCourse_ShouldReturnCreatedCourse() {
        // Arrange
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setName("Java");
        courseDTO.setDescription("Java Course");
        courseDTO.setDuration(40);

        Course course = new Course();
        course.setId(1L);
        course.setName("Java");
        course.setDescription("Java Course");
        course.setDuration(40);

        when(courseService.createCourse(any(Course.class))).thenReturn(course);

        // Act
        ResponseEntity<CourseDTO> response = courseController.createCourse(courseDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
        assertEquals("Java", response.getBody().getName());
    }

    @Test
    void updateCourse_WhenCourseExists_ShouldReturnUpdatedCourse() {
        // Arrange
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setName("Updated Java");
        courseDTO.setDescription("Updated Java Course");
        courseDTO.setDuration(45);

        Course updatedCourse = new Course();
        updatedCourse.setId(1L);
        updatedCourse.setName("Updated Java");
        updatedCourse.setDescription("Updated Java Course");
        updatedCourse.setDuration(45);

        when(courseService.updateCourse(eq(1L), any(Course.class))).thenReturn(updatedCourse);

        // Act
        ResponseEntity<CourseDTO> response = courseController.updateCourse(1L, courseDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated Java", response.getBody().getName());
    }

    @Test
    void deleteCourse_WhenCourseExists_ShouldReturnNoContent() {
        // Arrange
        when(courseService.deleteCourse(1L)).thenReturn(true);

        // Act
        ResponseEntity<Void> response = courseController.deleteCourse(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
} 