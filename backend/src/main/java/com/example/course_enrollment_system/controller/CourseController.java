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
import com.example.course_enrollment_system.exception.ResourceNotFoundException;
import static com.example.course_enrollment_system.util.MessageConstants.COURSE_NOT_FOUND;
import com.example.course_enrollment_system.dto.StudentDTO;
import com.example.course_enrollment_system.service.EnrollmentService;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@Tag(name = "Cursos", description = "API para gerenciamento de cursos")
public class CourseController {
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;

    public CourseController(CourseService courseService, EnrollmentService enrollmentService) {
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
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

        return ResponseEntity.ok(courses);
    }

    @Operation(summary = "Buscar curso por ID")
    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable Long id) {
        return courseService.getCourseById(id)
            .map(course -> {
                CourseDTO dto = new CourseDTO();
                dto.setId(course.getId());
                dto.setName(course.getName());
                dto.setDescription(course.getDescription());
                dto.setDuration(course.getDuration());
                return ResponseEntity.ok(dto);
            })
            .orElseThrow(() -> new ResourceNotFoundException(COURSE_NOT_FOUND + id));
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
        if (updatedCourse == null) {
            throw new ResourceNotFoundException(COURSE_NOT_FOUND + id);
        }
        courseDTO.setId(updatedCourse.getId());
        return ResponseEntity.ok(courseDTO);
    }

    @Operation(summary = "Excluir curso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        if (!courseService.deleteCourse(id)) {
            throw new ResourceNotFoundException(COURSE_NOT_FOUND + id);
        }
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar alunos matriculados em um curso")
    @GetMapping("/{id}/students")
    public ResponseEntity<List<StudentDTO>> getStudentsByCourse(@PathVariable Long id) {
        if (!courseService.getCourseById(id).isPresent()) {
            throw new ResourceNotFoundException(COURSE_NOT_FOUND + id);
        }

        List<StudentDTO> students = enrollmentService.getEnrollmentsByCourse(id)
            .stream()
            .map(enrollment -> {
                StudentDTO dto = new StudentDTO();
                dto.setId(enrollment.getStudent().getId());
                dto.setName(enrollment.getStudent().getName());
                dto.setEmail(enrollment.getStudent().getEmail());
                return dto;
            })
            .toList();

        return ResponseEntity.ok(students);
    }
}