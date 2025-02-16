package com.example.course_enrollment_system.repository;

import com.example.course_enrollment_system.entity.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CourseRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CourseRepository courseRepository;

    @Test
    void findById_WhenCourseExists_ShouldReturnCourse() {
        // Arrange
        Course course = new Course();
        course.setName("Java");
        course.setDescription("Java Programming");
        course.setDuration(40);
        
        Course savedCourse = entityManager.persist(course);
        entityManager.flush();

        // Act
        Optional<Course> found = courseRepository.findById(savedCourse.getId());

        // Assert
        assertTrue(found.isPresent());
        assertEquals("Java", found.get().getName());
    }

    @Test
    void findAll_WhenCoursesExist_ShouldReturnAllCourses() {
        // Arrange
        Course course1 = new Course();
        course1.setName("Java");
        course1.setDescription("Java Programming");
        course1.setDuration(40);

        Course course2 = new Course();
        course2.setName("Python");
        course2.setDescription("Python Programming");
        course2.setDuration(30);

        entityManager.persist(course1);
        entityManager.persist(course2);
        entityManager.flush();

        // Act
        List<Course> courses = courseRepository.findAll();

        // Assert
        assertEquals(2, courses.size());
    }

    @Test
    void save_ShouldPersistCourse() {
        // Arrange
        Course course = new Course();
        course.setName("Java");
        course.setDescription("Java Programming");
        course.setDuration(40);

        // Act
        Course savedCourse = courseRepository.save(course);

        // Assert
        assertNotNull(savedCourse.getId());
        assertEquals("Java", savedCourse.getName());
    }

    @Test
    void deleteById_ShouldRemoveCourse() {
        // Arrange
        Course course = new Course();
        course.setName("Java");
        course.setDescription("Java Programming");
        course.setDuration(40);
        
        Course savedCourse = entityManager.persist(course);
        entityManager.flush();

        // Act
        courseRepository.deleteById(savedCourse.getId());
        Optional<Course> found = courseRepository.findById(savedCourse.getId());

        // Assert
        assertFalse(found.isPresent());
    }
} 