package com.example.course_enrollment_system.controller;

import com.example.course_enrollment_system.dto.CourseDTO;
import com.example.course_enrollment_system.entity.Course;
import com.example.course_enrollment_system.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@Tag(name = "Cursos", description = "API para gerenciamento de cursos")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @Operation(summary = "Listar todos os cursos")
    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        List<CourseDTO> courses = courseService.getAllCourses().stream().map(course -> {
            CourseDTO dto = new CourseDTO();
            dto.setId(course.getId());
            dto.setName(course.getName());
            dto.setDescription(course.getDescription());
            dto.setDuration(course.getDuration());
            return dto;
        }).toList();

        return courses.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                                 : new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @Operation(summary = "Buscar curso por ID")
    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable Long id) {
        return courseService.getCourseById(id).map(course -> {
            CourseDTO dto = new CourseDTO();
            dto.setId(course.getId());
            dto.setName(course.getName());
            dto.setDescription(course.getDescription());
            dto.setDuration(course.getDuration());
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Criar novo curso")
    @PostMapping
    public ResponseEntity<CourseDTO> createCourse(@Valid @RequestBody CourseDTO courseDTO) {
        Course course = new Course();
        course.setName(courseDTO.getName());
        course.setDescription(courseDTO.getDescription());
        course.setDuration(courseDTO.getDuration());

        Course createdCourse = courseService.createCourse(course);

        courseDTO.setId(createdCourse.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(courseDTO);
    }

    @Operation(summary = "Atualizar curso existente")
    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable Long id, @Valid @RequestBody CourseDTO courseDTO) {
        Course course = new Course();
        course.setId(id);
        course.setName(courseDTO.getName());
        course.setDescription(courseDTO.getDescription());
        course.setDuration(courseDTO.getDuration());

        Course updatedCourse = courseService.updateCourse(id, course);

        if (updatedCourse != null) {
            courseDTO.setId(updatedCourse.getId());
            return new ResponseEntity<>(courseDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Excluir curso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        boolean deleted = courseService.deleteCourse(id);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                       : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}