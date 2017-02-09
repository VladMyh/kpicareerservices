package com.kpics.web.rest;

import com.kpics.KpicsApp;

import com.kpics.domain.TeacherInfo;
import com.kpics.repository.TeacherInfoRepository;
import com.kpics.service.TeacherInfoService;

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
 * Test class for the TeacherInfoResource REST controller.
 *
 * @see TeacherInfoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KpicsApp.class)
public class TeacherInfoResourceIntTest {

    private static final String DEFAULT_FACULTY = "AAAAAAAAAA";
    private static final String UPDATED_FACULTY = "BBBBBBBBBB";

    private static final String DEFAULT_DEPARTMENT = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTMENT = "BBBBBBBBBB";

    private static final String DEFAULT_ABOUT = "AAAAAAAAAA";
    private static final String UPDATED_ABOUT = "BBBBBBBBBB";

    @Autowired
    private TeacherInfoRepository teacherInfoRepository;

    @Autowired
    private TeacherInfoService teacherInfoService;

    @Autowired
    private UserService userService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTeacherInfoMockMvc;

    private TeacherInfo teacherInfo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TeacherInfoResource teacherInfoResource = new TeacherInfoResource(teacherInfoService, userService);
        this.restTeacherInfoMockMvc = MockMvcBuilders.standaloneSetup(teacherInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TeacherInfo createEntity() {
        TeacherInfo teacherInfo = new TeacherInfo()
                .faculty(DEFAULT_FACULTY)
                .department(DEFAULT_DEPARTMENT)
                .about(DEFAULT_ABOUT);
        return teacherInfo;
    }

    @Before
    public void initTest() {
        teacherInfoRepository.deleteAll();
        teacherInfo = createEntity();
    }

    @Test
    public void createTeacherInfo() throws Exception {
        int databaseSizeBeforeCreate = teacherInfoRepository.findAll().size();

        // Create the TeacherInfo

        restTeacherInfoMockMvc.perform(post("/api/teacher-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teacherInfo)))
            .andExpect(status().isCreated());

        // Validate the TeacherInfo in the database
        List<TeacherInfo> teacherInfoList = teacherInfoRepository.findAll();
        assertThat(teacherInfoList).hasSize(databaseSizeBeforeCreate + 1);
        TeacherInfo testTeacherInfo = teacherInfoList.get(teacherInfoList.size() - 1);
        assertThat(testTeacherInfo.getFaculty()).isEqualTo(DEFAULT_FACULTY);
        assertThat(testTeacherInfo.getDepartment()).isEqualTo(DEFAULT_DEPARTMENT);
        assertThat(testTeacherInfo.getAbout()).isEqualTo(DEFAULT_ABOUT);
    }

    @Test
    public void createTeacherInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = teacherInfoRepository.findAll().size();

        // Create the TeacherInfo with an existing ID
        TeacherInfo existingTeacherInfo = new TeacherInfo();
        existingTeacherInfo.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restTeacherInfoMockMvc.perform(post("/api/teacher-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTeacherInfo)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TeacherInfo> teacherInfoList = teacherInfoRepository.findAll();
        assertThat(teacherInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkFacultyIsRequired() throws Exception {
        int databaseSizeBeforeTest = teacherInfoRepository.findAll().size();
        // set the field null
        teacherInfo.setFaculty(null);

        // Create the TeacherInfo, which fails.

        restTeacherInfoMockMvc.perform(post("/api/teacher-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teacherInfo)))
            .andExpect(status().isBadRequest());

        List<TeacherInfo> teacherInfoList = teacherInfoRepository.findAll();
        assertThat(teacherInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDepartmentIsRequired() throws Exception {
        int databaseSizeBeforeTest = teacherInfoRepository.findAll().size();
        // set the field null
        teacherInfo.setDepartment(null);

        // Create the TeacherInfo, which fails.

        restTeacherInfoMockMvc.perform(post("/api/teacher-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teacherInfo)))
            .andExpect(status().isBadRequest());

        List<TeacherInfo> teacherInfoList = teacherInfoRepository.findAll();
        assertThat(teacherInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllTeacherInfos() throws Exception {
        // Initialize the database
        teacherInfoRepository.save(teacherInfo);

        // Get all the teacherInfoList
        restTeacherInfoMockMvc.perform(get("/api/teacher-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teacherInfo.getId())))
            .andExpect(jsonPath("$.[*].faculty").value(hasItem(DEFAULT_FACULTY.toString())))
            .andExpect(jsonPath("$.[*].department").value(hasItem(DEFAULT_DEPARTMENT.toString())))
            .andExpect(jsonPath("$.[*].about").value(hasItem(DEFAULT_ABOUT.toString())));
    }

    @Test
    public void getTeacherInfo() throws Exception {
        // Initialize the database
        teacherInfoRepository.save(teacherInfo);

        // Get the teacherInfo
        restTeacherInfoMockMvc.perform(get("/api/teacher-infos/{id}", teacherInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(teacherInfo.getId()))
            .andExpect(jsonPath("$.faculty").value(DEFAULT_FACULTY.toString()))
            .andExpect(jsonPath("$.department").value(DEFAULT_DEPARTMENT.toString()))
            .andExpect(jsonPath("$.about").value(DEFAULT_ABOUT.toString()));
    }

    @Test
    public void getNonExistingTeacherInfo() throws Exception {
        // Get the teacherInfo
        restTeacherInfoMockMvc.perform(get("/api/teacher-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateTeacherInfo() throws Exception {
        // Initialize the database
        teacherInfoService.save(teacherInfo);

        int databaseSizeBeforeUpdate = teacherInfoRepository.findAll().size();

        // Update the teacherInfo
        TeacherInfo updatedTeacherInfo = teacherInfoRepository.findOne(teacherInfo.getId());
        updatedTeacherInfo
                .faculty(UPDATED_FACULTY)
                .department(UPDATED_DEPARTMENT)
                .about(UPDATED_ABOUT);

        restTeacherInfoMockMvc.perform(put("/api/teacher-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTeacherInfo)))
            .andExpect(status().isOk());

        // Validate the TeacherInfo in the database
        List<TeacherInfo> teacherInfoList = teacherInfoRepository.findAll();
        assertThat(teacherInfoList).hasSize(databaseSizeBeforeUpdate);
        TeacherInfo testTeacherInfo = teacherInfoList.get(teacherInfoList.size() - 1);
        assertThat(testTeacherInfo.getFaculty()).isEqualTo(UPDATED_FACULTY);
        assertThat(testTeacherInfo.getDepartment()).isEqualTo(UPDATED_DEPARTMENT);
        assertThat(testTeacherInfo.getAbout()).isEqualTo(UPDATED_ABOUT);
    }

    @Test
    public void updateNonExistingTeacherInfo() throws Exception {
        int databaseSizeBeforeUpdate = teacherInfoRepository.findAll().size();

        // Create the TeacherInfo

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTeacherInfoMockMvc.perform(put("/api/teacher-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teacherInfo)))
            .andExpect(status().isCreated());

        // Validate the TeacherInfo in the database
        List<TeacherInfo> teacherInfoList = teacherInfoRepository.findAll();
        assertThat(teacherInfoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteTeacherInfo() throws Exception {
        // Initialize the database
        teacherInfoService.save(teacherInfo);

        int databaseSizeBeforeDelete = teacherInfoRepository.findAll().size();

        // Get the teacherInfo
        restTeacherInfoMockMvc.perform(delete("/api/teacher-infos/{id}", teacherInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TeacherInfo> teacherInfoList = teacherInfoRepository.findAll();
        assertThat(teacherInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TeacherInfo.class);
    }
}
