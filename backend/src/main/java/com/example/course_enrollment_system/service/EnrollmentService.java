package com.example.course_enrollment_system.service;

import com.example.course_enrollment_system.entity.Enrollment;
import com.example.course_enrollment_system.repository.EnrollmentRepository;
import com.example.course_enrollment_system.exception.EnrollmentException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    public Enrollment createEnrollment(Enrollment enrollment) {
        if (enrollmentRepository.existsByCourseIdAndStudentId(
                enrollment.getCourse().getId(), 
                enrollment.getStudent().getId())) {
            throw new EnrollmentException("Student already enrolled in this course");
        }
        return enrollmentRepository.save(enrollment);
    }

    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public Enrollment getEnrollmentById(Long id) {
        Optional<Enrollment> enrollment = enrollmentRepository.findById(id);
        return enrollment.orElse(null);
    }

    public Enrollment updateEnrollment(Long id, Enrollment enrollment) {
        return enrollmentRepository.findById(id)
            .map(existingEnrollment -> {
                existingEnrollment.setCourse(enrollment.getCourse());
                existingEnrollment.setStudent(enrollment.getStudent());
                existingEnrollment.setEnrollmentDate(LocalDateTime.now());
                return enrollmentRepository.save(existingEnrollment);
            })
            .orElse(null);
    }

    public boolean deleteEnrollment(Long id) {
        if (enrollmentRepository.existsById(id)) {
            enrollmentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Enrollment> getEnrollmentsByCourse(Long courseId) {
        return enrollmentRepository.findByCourseId(courseId);
    }

    public List<Enrollment> getEnrollmentsByStudent(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }
}
