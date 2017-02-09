package com.kpics.web.rest;

import com.kpics.KpicsApp;

import com.kpics.domain.StudentInfo;
import com.kpics.repository.StudentInfoRepository;
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

    private static final String DEFAULT_ABOUT = "AAAAAAAAAA";
    private static final String UPDATED_ABOUT = "BBBBBBBBBB";

    @Autowired
    private StudentInfoRepository studentInfoRepository;

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

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StudentInfoResource studentInfoResource = new StudentInfoResource(studentInfoService, userService);
        this.restStudentInfoMockMvc = MockMvcBuilders.standaloneSetup(studentInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StudentInfo createEntity() {
        StudentInfo studentInfo = new StudentInfo()
                .faculty(DEFAULT_FACULTY)
                .department(DEFAULT_DEPARTMENT)
                .group(DEFAULT_GROUP)
                .github(DEFAULT_GITHUB)
                .about(DEFAULT_ABOUT);
        return studentInfo;
    }

    @Before
    public void initTest() {
        studentInfoRepository.deleteAll();
        studentInfo = createEntity();
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
        assertThat(testStudentInfo.getAbout()).isEqualTo(DEFAULT_ABOUT);
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

        // Get all the studentInfoList
        restStudentInfoMockMvc.perform(get("/api/student-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentInfo.getId())))
            .andExpect(jsonPath("$.[*].faculty").value(hasItem(DEFAULT_FACULTY.toString())))
            .andExpect(jsonPath("$.[*].department").value(hasItem(DEFAULT_DEPARTMENT.toString())))
            .andExpect(jsonPath("$.[*].group").value(hasItem(DEFAULT_GROUP.toString())))
            .andExpect(jsonPath("$.[*].github").value(hasItem(DEFAULT_GITHUB.toString())))
            .andExpect(jsonPath("$.[*].about").value(hasItem(DEFAULT_ABOUT.toString())));
    }

    @Test
    public void getStudentInfo() throws Exception {
        // Initialize the database
        studentInfoRepository.save(studentInfo);

        // Get the studentInfo
        restStudentInfoMockMvc.perform(get("/api/student-infos/{id}", studentInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(studentInfo.getId()))
            .andExpect(jsonPath("$.faculty").value(DEFAULT_FACULTY.toString()))
            .andExpect(jsonPath("$.department").value(DEFAULT_DEPARTMENT.toString()))
            .andExpect(jsonPath("$.group").value(DEFAULT_GROUP.toString()))
            .andExpect(jsonPath("$.github").value(DEFAULT_GITHUB.toString()))
            .andExpect(jsonPath("$.about").value(DEFAULT_ABOUT.toString()));
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
                .about(UPDATED_ABOUT);

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
        assertThat(testStudentInfo.getAbout()).isEqualTo(UPDATED_ABOUT);
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

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentInfo.class);
    }
}
