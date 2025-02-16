package com.example.course_enrollment_system.integration;

import com.example.course_enrollment_system.dto.EnrollmentDTO;
import com.example.course_enrollment_system.entity.Course;
import com.example.course_enrollment_system.entity.Student;
import com.example.course_enrollment_system.entity.Enrollment;
import com.example.course_enrollment_system.repository.CourseRepository;
import com.example.course_enrollment_system.repository.StudentRepository;
import com.example.course_enrollment_system.repository.EnrollmentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.example.course_enrollment_system.security.JwtTokenUtil;
import com.example.course_enrollment_system.repository.UserRepository;
import com.example.course_enrollment_system.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class EnrollmentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String authToken;

    private Course course;
    private Student student;

    @BeforeEach
    void setUp() {
        enrollmentRepository.deleteAll();
        courseRepository.deleteAll();
        studentRepository.deleteAll();
        userRepository.deleteAll();

        User testUser = new User();
        testUser.setUsername("admin");
        testUser.setPassword(passwordEncoder.encode("admin123"));
        testUser.setRole("ADMIN");
        userRepository.save(testUser);

        authToken = jwtTokenUtil.generateToken(testUser.getUsername());

        course = new Course();
        course.setName("Java");
        course.setDescription("Java Programming");
        course.setDuration(40);
        course = courseRepository.save(course);

        student = new Student();
        student.setName("Leandro");
        student.setEmail("leandro@email.com");
        student = studentRepository.save(student);
    }

    @Test
    void createEnrollment_ShouldCreateAndReturnEnrollment() throws Exception {
        EnrollmentDTO enrollmentDTO = new EnrollmentDTO();
        enrollmentDTO.setCourseId(course.getId());
        enrollmentDTO.setStudentId(student.getId());
        enrollmentDTO.setEnrollmentDate(LocalDateTime.now());

        mockMvc.perform(post("/api/enrollments")
                .header("Authorization", "Bearer " + authToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(enrollmentDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.courseId", is(course.getId().intValue())))
                .andExpect(jsonPath("$.studentId", is(student.getId().intValue())));
    }

    @Test
    void getAllEnrollments_ShouldReturnAllEnrollments() throws Exception {
        Enrollment enrollment = new Enrollment();
        enrollment.setCourse(course);
        enrollment.setStudent(student);
        enrollment.setEnrollmentDate(LocalDateTime.now());
        enrollmentRepository.save(enrollment);

        mockMvc.perform(get("/api/enrollments")
                .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].courseId", is(course.getId().intValue())))
                .andExpect(jsonPath("$[0].studentId", is(student.getId().intValue())));
    }

    @Test
    void getEnrollmentById_WhenEnrollmentExists_ShouldReturnEnrollment() throws Exception {
        Enrollment enrollment = new Enrollment();
        enrollment.setCourse(course);
        enrollment.setStudent(student);
        enrollment.setEnrollmentDate(LocalDateTime.now());
        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);

        mockMvc.perform(get("/api/enrollments/{id}", savedEnrollment.getId())
                .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courseId", is(course.getId().intValue())))
                .andExpect(jsonPath("$.studentId", is(student.getId().intValue())));
    }

    @Test
    void deleteEnrollment_WhenEnrollmentExists_ShouldDeleteEnrollment() throws Exception {
        Enrollment enrollment = new Enrollment();
        enrollment.setCourse(course);
        enrollment.setStudent(student);
        enrollment.setEnrollmentDate(LocalDateTime.now());
        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);

        mockMvc.perform(delete("/api/enrollments/{id}", savedEnrollment.getId())
                .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isNoContent());
    }
} 