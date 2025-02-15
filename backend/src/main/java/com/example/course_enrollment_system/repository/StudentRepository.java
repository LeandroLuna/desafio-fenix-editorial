package com.example.course_enrollment_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.course_enrollment_system.entity.Student;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

}
