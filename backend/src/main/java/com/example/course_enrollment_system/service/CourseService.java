package com.example.course_enrollment_system.service;

import com.example.course_enrollment_system.entity.Course;
import com.example.course_enrollment_system.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course updateCourse(Long id, Course course) {
        return courseRepository.findById(id)
            .map(existingCourse -> {
                existingCourse.setName(course.getName());
                existingCourse.setDescription(course.getDescription());
                existingCourse.setDuration(course.getDuration());
                existingCourse.setUpdatedAt(LocalDateTime.now());
                return courseRepository.save(existingCourse);
            })
            .orElse(null);
    }

    public boolean deleteCourse(Long id) {
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
            return true;
        }
        return false;
    }
}