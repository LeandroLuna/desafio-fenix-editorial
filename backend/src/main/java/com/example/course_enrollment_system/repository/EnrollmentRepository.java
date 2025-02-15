package com.example.course_enrollment_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.course_enrollment_system.entity.Enrollment;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByCourseId(Long courseId);
    List<Enrollment> findByStudentId(Long studentId);
    boolean existsByCourseIdAndStudentId(Long courseId, Long studentId);
}
