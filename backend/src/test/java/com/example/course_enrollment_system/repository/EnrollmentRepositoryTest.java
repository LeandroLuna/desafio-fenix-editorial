package com.example.course_enrollment_system.repository;

import com.example.course_enrollment_system.entity.Course;
import com.example.course_enrollment_system.entity.Student;
import com.example.course_enrollment_system.entity.Enrollment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EnrollmentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Test
    void findByCourseId_ShouldReturnEnrollments() {
        // Arrange
        Course course = new Course();
        course.setName("Java");
        course.setDescription("Java Programming");
        course.setDuration(40);
        entityManager.persist(course);

        Student student = new Student();
        student.setName("Leandro");
        student.setEmail("leandro@email.com");
        entityManager.persist(student);

        Enrollment enrollment = new Enrollment();
        enrollment.setCourse(course);
        enrollment.setStudent(student);
        enrollment.setEnrollmentDate(LocalDateTime.now());
        entityManager.persist(enrollment);
        entityManager.flush();

        // Act
        List<Enrollment> enrollments = enrollmentRepository.findByCourseId(course.getId());

        // Assert
        assertEquals(1, enrollments.size());
        assertEquals(course.getId(), enrollments.get(0).getCourse().getId());
    }

    @Test
    void findByStudentId_ShouldReturnEnrollments() {
        // Arrange
        Course course = new Course();
        course.setName("Java");
        course.setDescription("Java Programming");
        course.setDuration(40);
        entityManager.persist(course);

        Student student = new Student();
        student.setName("Leandro");
        student.setEmail("leandro@email.com");
        entityManager.persist(student);

        Enrollment enrollment = new Enrollment();
        enrollment.setCourse(course);
        enrollment.setStudent(student);
        enrollment.setEnrollmentDate(LocalDateTime.now());
        entityManager.persist(enrollment);
        entityManager.flush();

        // Act
        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(student.getId());

        // Assert
        assertEquals(1, enrollments.size());
        assertEquals(student.getId(), enrollments.get(0).getStudent().getId());
    }

    @Test
    void existsByCourseIdAndStudentId_WhenEnrollmentExists_ShouldReturnTrue() {
        // Arrange
        Course course = new Course();
        course.setName("Java");
        course.setDescription("Java Programming");
        course.setDuration(40);
        entityManager.persist(course);

        Student student = new Student();
        student.setName("Leandro");
        student.setEmail("leandro@email.com");
        entityManager.persist(student);

        Enrollment enrollment = new Enrollment();
        enrollment.setCourse(course);
        enrollment.setStudent(student);
        enrollment.setEnrollmentDate(LocalDateTime.now());
        entityManager.persist(enrollment);
        entityManager.flush();

        // Act
        boolean exists = enrollmentRepository.existsByCourseIdAndStudentId(
            course.getId(), student.getId());

        // Assert
        assertTrue(exists);
    }

    @Test
    void existsByCourseIdAndStudentId_WhenEnrollmentDoesNotExist_ShouldReturnFalse() {
        // Act
        boolean exists = enrollmentRepository.existsByCourseIdAndStudentId(1L, 1L);

        // Assert
        assertFalse(exists);
    }
} 