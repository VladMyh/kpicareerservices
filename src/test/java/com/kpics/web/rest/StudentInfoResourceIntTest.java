package com.kpics.web.rest;

import com.kpics.KpicsApp;

import com.kpics.domain.StudentInfo;
import com.kpics.domain.User;
import com.kpics.repository.StudentInfoRepository;
import com.kpics.repository.UserRepository;
import com.kpics.service.StudentInfoService;

import com.kpics.service.UserService;
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
    private StudentInfoRepository studentInfoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentInfoService studentInfoService;

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
        StudentInfoResource studentInfoResource = new StudentInfoResource(studentInfoService, userService);
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
                .linkedin(DEFAULT_LINKEDIN)
                .about(DEFAULT_ABOUT)
                .user(DEFAULT_USERID);
        return studentInfo;
    }

    /**
     * Create user entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static User createUserEntity() {
        User user = new User();
        user.setId(DEFAULT_USERID);
        user.setEmail("user@localhost");
        user.setLinkedin(DEFAULT_LINKEDIN);
        user.setPassword("$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC");
        return user;
    }

    @Before
    public void initTest() {
        studentInfoRepository.deleteAll();
        userRepository.deleteAll();
        studentInfo = createStudentInfoEntity();
        user = createUserEntity();
    }

    @Test
    public void createStudentInfo() throws Exception {
        int databaseSizeBeforeCreate = studentInfoRepository.findAll().size();

        // Create the StudentInfo

        restStudentInfoMockMvc.perform(post("/api/student-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentInfo)))
            .andExpect(status().isCreated());

        // Validate the StudentInfo in the database
        List<StudentInfo> studentInfoList = studentInfoRepository.findAll();
        assertThat(studentInfoList).hasSize(databaseSizeBeforeCreate + 1);
        StudentInfo testStudentInfo = studentInfoList.get(studentInfoList.size() - 1);
        assertThat(testStudentInfo.getFaculty()).isEqualTo(DEFAULT_FACULTY);
        assertThat(testStudentInfo.getDepartment()).isEqualTo(DEFAULT_DEPARTMENT);
        assertThat(testStudentInfo.getGroup()).isEqualTo(DEFAULT_GROUP);
        assertThat(testStudentInfo.getGithub()).isEqualTo(DEFAULT_GITHUB);
        assertThat(testStudentInfo.getLinkedin()).isEqualTo(DEFAULT_LINKEDIN);
        assertThat(testStudentInfo.getAbout()).isEqualTo(DEFAULT_ABOUT);
        assertThat(testStudentInfo.getUserId()).isEqualTo(DEFAULT_USERID);
    }

    @Test
    public void createStudentInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studentInfoRepository.findAll().size();

        // Create the StudentInfo with an existing ID
        StudentInfo existingStudentInfo = new StudentInfo();
        existingStudentInfo.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentInfoMockMvc.perform(post("/api/student-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingStudentInfo)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<StudentInfo> studentInfoList = studentInfoRepository.findAll();
        assertThat(studentInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkFacultyIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentInfoRepository.findAll().size();
        // set the field null
        studentInfo.setFaculty(null);

        // Create the StudentInfo, which fails.

        restStudentInfoMockMvc.perform(post("/api/student-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentInfo)))
            .andExpect(status().isBadRequest());

        List<StudentInfo> studentInfoList = studentInfoRepository.findAll();
        assertThat(studentInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDepartmentIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentInfoRepository.findAll().size();
        // set the field null
        studentInfo.setDepartment(null);

        // Create the StudentInfo, which fails.

        restStudentInfoMockMvc.perform(post("/api/student-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentInfo)))
            .andExpect(status().isBadRequest());

        List<StudentInfo> studentInfoList = studentInfoRepository.findAll();
        assertThat(studentInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkGroupIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentInfoRepository.findAll().size();
        // set the field null
        studentInfo.setGroup(null);

        // Create the StudentInfo, which fails.

        restStudentInfoMockMvc.perform(post("/api/student-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentInfo)))
            .andExpect(status().isBadRequest());

        List<StudentInfo> studentInfoList = studentInfoRepository.findAll();
        assertThat(studentInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkGithubIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentInfoRepository.findAll().size();
        // set the field null
        studentInfo.setGithub(null);

        // Create the StudentInfo, which fails.

        restStudentInfoMockMvc.perform(post("/api/student-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentInfo)))
            .andExpect(status().isBadRequest());

        List<StudentInfo> studentInfoList = studentInfoRepository.findAll();
        assertThat(studentInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllStudentInfos() throws Exception {
        // Initialize the database
        studentInfoRepository.save(studentInfo);
        userRepository.save(user);

        // Get all the studentInfoList
        restStudentInfoMockMvc.perform(get("/api/student-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentInfo.getId())))
            .andExpect(jsonPath("$.[*].faculty").value(hasItem(DEFAULT_FACULTY)))
            .andExpect(jsonPath("$.[*].department").value(hasItem(DEFAULT_DEPARTMENT)))
            .andExpect(jsonPath("$.[*].group").value(hasItem(DEFAULT_GROUP)))
            .andExpect(jsonPath("$.[*].github").value(hasItem(DEFAULT_GITHUB)))
            .andExpect(jsonPath("$.[*].linkedin").value(hasItem(DEFAULT_LINKEDIN)))
            .andExpect(jsonPath("$.[*].about").value(hasItem(DEFAULT_ABOUT)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USERID)));
    }

    @Test
    public void getStudentInfo() throws Exception {
        // Initialize the database
        studentInfoRepository.save(studentInfo);
        userRepository.save(user);

        // Get the studentInfo
        restStudentInfoMockMvc.perform(get("/api/student-infos/{id}", studentInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(studentInfo.getId()))
            .andExpect(jsonPath("$.faculty").value(DEFAULT_FACULTY))
            .andExpect(jsonPath("$.department").value(DEFAULT_DEPARTMENT))
            .andExpect(jsonPath("$.group").value(DEFAULT_GROUP))
            .andExpect(jsonPath("$.github").value(DEFAULT_GITHUB))
            .andExpect(jsonPath("$.linkedin").value(DEFAULT_LINKEDIN))
            .andExpect(jsonPath("$.about").value(DEFAULT_ABOUT))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USERID));
    }

    @Test
    public void getNonExistingStudentInfo() throws Exception {
        // Get the studentInfo
        restStudentInfoMockMvc.perform(get("/api/student-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateStudentInfo() throws Exception {
        // Initialize the database
        studentInfoService.save(studentInfo);

        int databaseSizeBeforeUpdate = studentInfoRepository.findAll().size();

        // Update the studentInfo
        StudentInfo updatedStudentInfo = studentInfoRepository.findOne(studentInfo.getId());
        updatedStudentInfo
                .faculty(UPDATED_FACULTY)
                .department(UPDATED_DEPARTMENT)
                .group(UPDATED_GROUP)
                .github(UPDATED_GITHUB)
                .linkedin(UPDATED_LINKEDIN)
                .about(UPDATED_ABOUT)
                .user(UPDATED_USERID);

        restStudentInfoMockMvc.perform(put("/api/student-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStudentInfo)))
            .andExpect(status().isOk());

        // Validate the StudentInfo in the database
        List<StudentInfo> studentInfoList = studentInfoRepository.findAll();
        assertThat(studentInfoList).hasSize(databaseSizeBeforeUpdate);
        StudentInfo testStudentInfo = studentInfoList.get(studentInfoList.size() - 1);
        assertThat(testStudentInfo.getFaculty()).isEqualTo(UPDATED_FACULTY);
        assertThat(testStudentInfo.getDepartment()).isEqualTo(UPDATED_DEPARTMENT);
        assertThat(testStudentInfo.getGroup()).isEqualTo(UPDATED_GROUP);
        assertThat(testStudentInfo.getGithub()).isEqualTo(UPDATED_GITHUB);
        assertThat(testStudentInfo.getLinkedin()).isEqualTo(UPDATED_LINKEDIN);
        assertThat(testStudentInfo.getAbout()).isEqualTo(UPDATED_ABOUT);
        assertThat(testStudentInfo.getUserId()).isEqualTo(UPDATED_USERID);
    }

    @Test
    public void updateNonExistingStudentInfo() throws Exception {
        int databaseSizeBeforeUpdate = studentInfoRepository.findAll().size();

        // Create the StudentInfo

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restStudentInfoMockMvc.perform(put("/api/student-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentInfo)))
            .andExpect(status().isCreated());

        // Validate the StudentInfo in the database
        List<StudentInfo> studentInfoList = studentInfoRepository.findAll();
        assertThat(studentInfoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteStudentInfo() throws Exception {
        // Initialize the database
        studentInfoService.save(studentInfo);

        int databaseSizeBeforeDelete = studentInfoRepository.findAll().size();

        // Get the studentInfo
        restStudentInfoMockMvc.perform(delete("/api/student-infos/{id}", studentInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StudentInfo> studentInfoList = studentInfoRepository.findAll();
        assertThat(studentInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
