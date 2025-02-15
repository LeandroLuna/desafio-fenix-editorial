package com.example.course_enrollment_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.course_enrollment_system.entity.Course;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

}
