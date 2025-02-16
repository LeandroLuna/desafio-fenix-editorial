package com.example.course_enrollment_system.integration;

import com.example.course_enrollment_system.dto.CourseDTO;
import com.example.course_enrollment_system.entity.Course;
import com.example.course_enrollment_system.repository.CourseRepository;
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
import com.example.course_enrollment_system.entity.User;
import com.example.course_enrollment_system.repository.UserRepository;
import com.example.course_enrollment_system.security.JwtTokenUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CourseControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CourseRepository courseRepository;

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
        courseRepository.deleteAll();
        userRepository.deleteAll();

        User testUser = new User();
        testUser.setUsername("admin");
        testUser.setPassword(passwordEncoder.encode("admin123"));
        testUser.setRole("ADMIN");
        userRepository.save(testUser);

        authToken = jwtTokenUtil.generateToken(testUser.getUsername());
    }

    @Test
    void createCourse_ShouldCreateAndReturnCourse() throws Exception {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setName("Java");
        courseDTO.setDescription("Java Programming");
        courseDTO.setDuration(40);

        mockMvc.perform(post("/api/courses")
                .header("Authorization", "Bearer " + authToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(courseDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Java")))
                .andExpect(jsonPath("$.description", is("Java Programming")))
                .andExpect(jsonPath("$.duration", is(40)));
    }

    @Test
    void getAllCourses_ShouldReturnAllCourses() throws Exception {
        Course course = new Course();
        course.setName("Java");
        course.setDescription("Java Programming");
        course.setDuration(40);
        courseRepository.save(course);

        mockMvc.perform(get("/api/courses")
                .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Java")));
    }

    @Test
    void getCourseById_WhenCourseExists_ShouldReturnCourse() throws Exception {
        Course course = new Course();
        course.setName("Java");
        course.setDescription("Java Programming");
        course.setDuration(40);
        Course savedCourse = courseRepository.save(course);

        mockMvc.perform(get("/api/courses/{id}", savedCourse.getId())
                .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Java")));
    }

    @Test
    void updateCourse_WhenCourseExists_ShouldUpdateAndReturnCourse() throws Exception {
        Course course = new Course();
        course.setName("Java");
        course.setDescription("Java Programming");
        course.setDuration(40);
        Course savedCourse = courseRepository.save(course);

        CourseDTO updateDTO = new CourseDTO();
        updateDTO.setName("Java Updated");
        updateDTO.setDescription("Updated Java Programming");
        updateDTO.setDuration(45);

        mockMvc.perform(put("/api/courses/{id}", savedCourse.getId())
                .header("Authorization", "Bearer " + authToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Java Updated")));
    }

    @Test
    void deleteCourse_WhenCourseExists_ShouldDeleteCourse() throws Exception {
        Course course = new Course();
        course.setName("Java");
        course.setDescription("Java Programming");
        course.setDuration(40);
        Course savedCourse = courseRepository.save(course);

        mockMvc.perform(delete("/api/courses/{id}", savedCourse.getId())
                .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isNoContent());
    }
} 