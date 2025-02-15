package com.example.course_enrollment_system.controller;

import com.example.course_enrollment_system.dto.StudentDTO;
import com.example.course_enrollment_system.entity.Student;
import com.example.course_enrollment_system.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.example.course_enrollment_system.exception.ResourceNotFoundException;
import static com.example.course_enrollment_system.util.MessageConstants.STUDENT_NOT_FOUND;
import java.util.List;
import com.example.course_enrollment_system.dto.CourseDTO;
import com.example.course_enrollment_system.service.EnrollmentService;

@RestController
@RequestMapping("/api/students")
@Tag(name = "Alunos", description = "API para gerenciamento de alunos")
public class StudentController {
    private final StudentService studentService;
    private final EnrollmentService enrollmentService;

    public StudentController(StudentService studentService, EnrollmentService enrollmentService) {
        this.studentService = studentService;
        this.enrollmentService = enrollmentService;
    }

    @Operation(summary = "Listar todos os alunos")
    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        List<StudentDTO> students = studentService.getAllStudents().stream().map(student -> {
            StudentDTO dto = new StudentDTO();
            dto.setId(student.getId());
            dto.setName(student.getName());
            dto.setEmail(student.getEmail());
            return dto;
        }).toList();

        return ResponseEntity.ok(students);
    }

    @Operation(summary = "Buscar aluno por ID")
    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id)
            .map(student -> {
                StudentDTO dto = new StudentDTO();
                dto.setId(student.getId());
                dto.setName(student.getName());
                dto.setEmail(student.getEmail());
                return ResponseEntity.ok(dto);
            })
            .orElseThrow(() -> new ResourceNotFoundException(STUDENT_NOT_FOUND + id));
    }

    @Operation(summary = "Criar novo aluno")
    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@Valid @RequestBody StudentDTO studentDTO) {
        Student student = new Student();
        student.setName(studentDTO.getName());
        student.setEmail(studentDTO.getEmail());

        Student createdStudent = studentService.createStudent(student);

        studentDTO.setId(createdStudent.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(studentDTO);
    }

    @Operation(summary = "Atualizar aluno existente")
    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentDTO studentDTO) {
        Student student = new Student();
        student.setName(studentDTO.getName());
        student.setEmail(studentDTO.getEmail());

        Student updatedStudent = studentService.updateStudent(id, student);
        if (updatedStudent == null) {
            throw new ResourceNotFoundException(STUDENT_NOT_FOUND + id);
        }
        studentDTO.setId(updatedStudent.getId());
        return ResponseEntity.ok(studentDTO);
    }

    @Operation(summary = "Excluir aluno")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        if (!studentService.deleteStudent(id)) {
            throw new ResourceNotFoundException(STUDENT_NOT_FOUND + id);
        }
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar cursos em que um aluno está matriculado")
    @GetMapping("/{id}/courses")
    public ResponseEntity<List<CourseDTO>> getCoursesByStudent(@PathVariable Long id) {
        if (!studentService.getStudentById(id).isPresent()) {
            throw new ResourceNotFoundException(STUDENT_NOT_FOUND + id);
        }

        List<CourseDTO> courses = enrollmentService.getEnrollmentsByStudent(id)
            .stream()
            .map(enrollment -> {
                CourseDTO dto = new CourseDTO();
                dto.setId(enrollment.getCourse().getId());
                dto.setName(enrollment.getCourse().getName());
                dto.setDescription(enrollment.getCourse().getDescription());
                dto.setDuration(enrollment.getCourse().getDuration());
                return dto;
            })
            .toList();

        return ResponseEntity.ok(courses);
    }
}