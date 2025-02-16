package com.example.course_enrollment_system.repository;

import com.example.course_enrollment_system.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StudentRepository studentRepository;

    @Test
    void findById_WhenStudentExists_ShouldReturnStudent() {
        // Arrange
        Student student = new Student();
        student.setName("Leandro");
        student.setEmail("leandro@email.com");
        
        Student savedStudent = entityManager.persist(student);
        entityManager.flush();

        // Act
        Optional<Student> found = studentRepository.findById(savedStudent.getId());

        // Assert
        assertTrue(found.isPresent());
        assertEquals("Leandro", found.get().getName());
    }

    @Test
    void findAll_WhenStudentsExist_ShouldReturnAllStudents() {
        // Arrange
        Student student1 = new Student();
        student1.setName("Leandro");
        student1.setEmail("leandro@email.com");

        Student student2 = new Student();
        student2.setName("Jane Doe");
        student2.setEmail("jane@example.com");

        entityManager.persist(student1);
        entityManager.persist(student2);
        entityManager.flush();

        // Act
        List<Student> students = studentRepository.findAll();

        // Assert
        assertEquals(2, students.size());
    }

    @Test
    void save_ShouldPersistStudent() {
        // Arrange
        Student student = new Student();
        student.setName("Leandro");
        student.setEmail("leandro@email.com");

        // Act
        Student savedStudent = studentRepository.save(student);

        // Assert
        assertNotNull(savedStudent.getId());
        assertEquals("Leandro", savedStudent.getName());
    }

    @Test
    void deleteById_ShouldRemoveStudent() {
        // Arrange
        Student student = new Student();
        student.setName("Leandro");
        student.setEmail("leandro@email.com");
        
        Student savedStudent = entityManager.persist(student);
        entityManager.flush();

        // Act
        studentRepository.deleteById(savedStudent.getId());
        Optional<Student> found = studentRepository.findById(savedStudent.getId());

        // Assert
        assertFalse(found.isPresent());
    }
} 