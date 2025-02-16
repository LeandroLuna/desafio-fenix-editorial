package com.example.course_enrollment_system.service;

import com.example.course_enrollment_system.entity.Course;
import com.example.course_enrollment_system.repository.CourseRepository;
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

class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    private Course course;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        course = new Course();
        course.setId(1L);
        course.setName("Java");
        course.setDescription("Java Course");
        course.setDuration(40);
    }

    @Test
    void getAllCourses_ShouldReturnListOfCourses() {
        // Arrange
        when(courseRepository.findAll()).thenReturn(Arrays.asList(course));

        // Act
        List<Course> courses = courseService.getAllCourses();

        // Assert
        assertFalse(courses.isEmpty());
        assertEquals(1, courses.size());
        assertEquals("Java", courses.get(0).getName());
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    void getCourseById_WhenCourseExists_ShouldReturnCourse() {
        // Arrange
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        // Act
        Optional<Course> result = courseService.getCourseById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Java", result.get().getName());
        verify(courseRepository, times(1)).findById(1L);
    }

    @Test
    void getCourseById_WhenCourseDoesNotExist_ShouldReturnEmpty() {
        // Arrange
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<Course> result = courseService.getCourseById(1L);

        // Assert
        assertFalse(result.isPresent());
        verify(courseRepository, times(1)).findById(1L);
    }

    @Test
    void createCourse_ShouldReturnCreatedCourse() {
        // Arrange
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        // Act
        Course createdCourse = courseService.createCourse(course);

        // Assert
        assertNotNull(createdCourse);
        assertEquals("Java", createdCourse.getName());
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    void updateCourse_WhenCourseExists_ShouldReturnUpdatedCourse() {
        // Arrange
        Course updatedCourse = new Course();
        updatedCourse.setId(1L);
        updatedCourse.setName("Updated Java");
        updatedCourse.setDescription("Updated Java Course");
        updatedCourse.setDuration(45);

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(courseRepository.save(any(Course.class))).thenReturn(updatedCourse);

        // Act
        Course result = courseService.updateCourse(1L, updatedCourse);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Java", result.getName());
        verify(courseRepository, times(1)).findById(1L);
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    void updateCourse_WhenCourseDoesNotExist_ShouldReturnNull() {
        // Arrange
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Course result = courseService.updateCourse(1L, course);

        // Assert
        assertNull(result);
        verify(courseRepository, times(1)).findById(1L);
        verify(courseRepository, never()).save(any(Course.class));
    }

    @Test
    void deleteCourse_WhenCourseExists_ShouldReturnTrue() {
        // Arrange
        when(courseRepository.existsById(1L)).thenReturn(true);
        doNothing().when(courseRepository).deleteById(1L);

        // Act
        boolean result = courseService.deleteCourse(1L);

        // Assert
        assertTrue(result);
        verify(courseRepository).existsById(1L);
        verify(courseRepository).deleteById(1L);
    }

    @Test
    void deleteCourse_WhenCourseDoesNotExist_ShouldReturnFalse() {
        // Arrange
        when(courseRepository.existsById(1L)).thenReturn(false);

        // Act
        boolean result = courseService.deleteCourse(1L);

        // Assert
        assertFalse(result);
        verify(courseRepository).existsById(1L);
        verify(courseRepository, never()).deleteById(anyLong());
    }
} 