package com.example.course_enrollment_system.service;

import com.example.course_enrollment_system.entity.Student;
import com.example.course_enrollment_system.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public boolean deleteStudent(Long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Student updateStudent(Long id, Student student) {
        return studentRepository.findById(id)
            .map(existingStudent -> {
                existingStudent.setName(student.getName());
                existingStudent.setEmail(student.getEmail());
                existingStudent.setUpdatedAt(LocalDateTime.now());
                return studentRepository.save(existingStudent);
            })
            .orElse(null);
    }
}
