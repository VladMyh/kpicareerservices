package com.kpics.web.rest;

import com.kpics.KpicsApp;
import com.kpics.domain.Authority;
import com.kpics.domain.StudentInfo;
import com.kpics.domain.User;
import com.kpics.repository.UserRepository;
import com.kpics.security.AuthoritiesConstants;
import com.kpics.service.UserService;
import com.kpics.service.dto.UserDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the StudentInfoResource REST controller.
 *
 * @see StudentInfoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KpicsApp.class)
public class StudentInfoResourceIntTest {

    private static final String DEFAULT_FNAME = "AAAAAAAAAA";
    private static final String UPDATED_FNAME = "BBBBBBBBBB";

    private static final String DEFAULT_LNAME = "AAAAAAAAAA";
    private static final String UPDATED_LNAME = "BBBBBBBBBB";

    private static final String DEFAULT_FACULTY = "AAAAAAAAAA";
    private static final String UPDATED_FACULTY = "BBBBBBBBBB";

    private static final String DEFAULT_DEPARTMENT = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTMENT = "BBBBBBBBBB";

    private static final String DEFAULT_GROUP = "AAAAAAAAAA";
    private static final String UPDATED_GROUP = "BBBBBBBBBB";

    private static final String DEFAULT_GITHUB = "AAAAAAAAAA";
    private static final String UPDATED_GITHUB = "BBBBBBBBBB";

    private static final String DEFAULT_LINKEDIN = "AAAAAAAAAA";
    private static final String UPDATED_LINKEDIN = "BBBBBBBBBB";

    private static final String DEFAULT_ABOUT = "AAAAAAAAAA";
    private static final String UPDATED_ABOUT = "BBBBBBBBBB";

    private static final String DEFAULT_USERID = "AAAAAAAAAA";
    private static final String UPDATED_USERID = "BBBBBBBBBB";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restStudentInfoMockMvc;

    private StudentInfo studentInfo;

    private User user;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StudentInfoResource studentInfoResource = new StudentInfoResource(userService);
        this.restStudentInfoMockMvc = MockMvcBuilders.standaloneSetup(studentInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create studentInfo entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StudentInfo createStudentInfoEntity() {
        StudentInfo studentInfo = new StudentInfo()
                .faculty(DEFAULT_FACULTY)
                .department(DEFAULT_DEPARTMENT)
                .group(DEFAULT_GROUP)
                .github(DEFAULT_GITHUB)
                .about(DEFAULT_ABOUT);
        return studentInfo;
    }

    /**
     * Create user entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static User createUserEntity(StudentInfo studentInfo) {
        User user = new User();
        user.setId(DEFAULT_USERID);
        user.setFirstName(DEFAULT_FNAME);
        user.setLastName(DEFAULT_LNAME);
        user.setEmail("user@localhost");
        user.setLinkedin(DEFAULT_LINKEDIN);
        user.setPassword("$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC");
        user.setStudentInfo(studentInfo);
        user.setAuthorities(new HashSet<>(Arrays.asList(new Authority(AuthoritiesConstants.USER))));
        user.setImageUrl("http://placehold.it/50x50");
        user.setLangKey("ru");
        return user;
    }

    @Before
    public void initTest() {
        userRepository.deleteAll();
        studentInfo = createStudentInfoEntity();
        user = createUserEntity(studentInfo);
    }

    @Test
    public void getAllStudents() throws Exception {
        // Initialize the database
        userRepository.save(user);

        // Get all the studentList
        restStudentInfoMockMvc.perform(get("/api/student-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].faculty").value(hasItem(DEFAULT_FACULTY)))
            .andExpect(jsonPath("$.[*].department").value(hasItem(DEFAULT_DEPARTMENT)))
            .andExpect(jsonPath("$.[*].group").value(hasItem(DEFAULT_GROUP)))
            .andExpect(jsonPath("$.[*].github").value(hasItem(DEFAULT_GITHUB)))
            .andExpect(jsonPath("$.[*].linkedin").value(hasItem(DEFAULT_LINKEDIN)))
            .andExpect(jsonPath("$.[*].about").value(hasItem(DEFAULT_ABOUT)));
    }

    @Test
    public void getStudent() throws Exception {
        // Initialize the database
        userRepository.save(user);

        // Get the student
        restStudentInfoMockMvc.perform(get("/api/student-infos/{id}", user.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.faculty").value(DEFAULT_FACULTY))
            .andExpect(jsonPath("$.department").value(DEFAULT_DEPARTMENT))
            .andExpect(jsonPath("$.group").value(DEFAULT_GROUP))
            .andExpect(jsonPath("$.github").value(DEFAULT_GITHUB))
            .andExpect(jsonPath("$.linkedin").value(DEFAULT_LINKEDIN))
            .andExpect(jsonPath("$.about").value(DEFAULT_ABOUT));
    }

    @Test
    public void getNonExistingStudent() throws Exception {
        // Get the student
        restStudentInfoMockMvc.perform(get("/api/student-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateStudent() throws Exception {
        // Initialize the database
        userRepository.save(user);

        int databaseSizeBeforeUpdate = userRepository.findAll().size();

        // Update the student
        UserDTO updatedStudentInfo = new UserDTO(userRepository.findOne(user.getId()));
        updatedStudentInfo.getStudentInfo().setFaculty(UPDATED_FACULTY);
        updatedStudentInfo.getStudentInfo().setDepartment(UPDATED_DEPARTMENT);
        updatedStudentInfo.getStudentInfo().setGroup(UPDATED_GROUP);
        updatedStudentInfo.getStudentInfo().setGithub(UPDATED_GITHUB);
        updatedStudentInfo.getStudentInfo().setAbout(UPDATED_ABOUT);


        restStudentInfoMockMvc.perform(put("/api/student-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStudentInfo)))
            .andExpect(status().isOk());

        // Validate the Student in the database
        List<User> studentInfoList = userRepository.findAll();
        assertThat(studentInfoList).hasSize(databaseSizeBeforeUpdate);
        User testStudentInfo = studentInfoList.get(studentInfoList.size() - 1);
        assertThat(testStudentInfo.getStudentInfo().getFaculty()).isEqualTo(UPDATED_FACULTY);
        assertThat(testStudentInfo.getStudentInfo().getDepartment()).isEqualTo(UPDATED_DEPARTMENT);
        assertThat(testStudentInfo.getStudentInfo().getGroup()).isEqualTo(UPDATED_GROUP);
        assertThat(testStudentInfo.getStudentInfo().getGithub()).isEqualTo(UPDATED_GITHUB);
        assertThat(testStudentInfo.getStudentInfo().getAbout()).isEqualTo(UPDATED_ABOUT);
    }

    @Test
    public void deleteStudent() throws Exception {
        // Initialize the database
        userRepository.save(user);

        int databaseSizeBeforeDelete = userRepository.findAll().size();

        // Get the student
        restStudentInfoMockMvc.perform(delete("/api/student-infos/{id}", user.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<User> studentInfoList = userRepository.findAll();
        assertThat(studentInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
