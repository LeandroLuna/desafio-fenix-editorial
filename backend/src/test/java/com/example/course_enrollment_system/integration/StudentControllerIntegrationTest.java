package com.example.course_enrollment_system.integration;

import com.example.course_enrollment_system.dto.StudentDTO;
import com.example.course_enrollment_system.entity.Student;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.course_enrollment_system.security.JwtTokenUtil;
import com.example.course_enrollment_system.entity.User;
import com.example.course_enrollment_system.repository.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String authToken;

    @BeforeEach
    void setUp() {
        enrollmentRepository.deleteAll();
        studentRepository.deleteAll();
        userRepository.deleteAll();

        User testUser = new User();
        testUser.setUsername("admin");
        testUser.setPassword(passwordEncoder.encode("admin123"));
        testUser.setRole("ADMIN");
        userRepository.save(testUser);

        authToken = jwtTokenUtil.generateToken(testUser.getUsername());
    }

    @Test
    void createStudent_ShouldCreateAndReturnStudent() throws Exception {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("Leandro");
        studentDTO.setEmail("leandro@email.com");

        mockMvc.perform(post("/api/students")
                .header("Authorization", "Bearer " + authToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studentDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Leandro")))
                .andExpect(jsonPath("$.email", is("leandro@email.com")));
    }

    @Test
    void getAllStudents_ShouldReturnAllStudents() throws Exception {
        Student student = new Student();
        student.setName("Leandro");
        student.setEmail("leandro@email.com");
        studentRepository.save(student);

        mockMvc.perform(get("/api/students")
                .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Leandro")));
    }

    @Test
    void getStudentById_WhenStudentExists_ShouldReturnStudent() throws Exception {
        Student student = new Student();
        student.setName("Leandro");
        student.setEmail("leandro@email.com");
        Student savedStudent = studentRepository.save(student);

        mockMvc.perform(get("/api/students/{id}", savedStudent.getId())
                .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Leandro")));
    }

    @Test
    void updateStudent_WhenStudentExists_ShouldUpdateAndReturnStudent() throws Exception {
        Student student = new Student();
        student.setName("Leandro");
        student.setEmail("leandro@email.com");
        Student savedStudent = studentRepository.save(student);

        StudentDTO updateDTO = new StudentDTO();
        updateDTO.setName("Leandro Updated");
        updateDTO.setEmail("leandro.updated@email.com");

        mockMvc.perform(put("/api/students/{id}", savedStudent.getId())
                .header("Authorization", "Bearer " + authToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Leandro Updated")));
    }

    @Test
    void deleteStudent_WhenStudentExists_ShouldDeleteStudent() throws Exception {
        Student student = new Student();
        student.setName("Leandro");
        student.setEmail("leandro@email.com");
        Student savedStudent = studentRepository.save(student);

        mockMvc.perform(delete("/api/students/{id}", savedStudent.getId())
                .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isNoContent());
    }
} 