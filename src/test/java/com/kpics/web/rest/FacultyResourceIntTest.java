package com.kpics.web.rest;

import com.kpics.KpicsApp;

import com.kpics.domain.Faculty;
import com.kpics.repository.FacultyRepository;
import com.kpics.service.FacultyService;

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
 * Test class for the FacultyResource REST controller.
 *
 * @see FacultyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KpicsApp.class)
public class FacultyResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private FacultyService facultyService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFacultyMockMvc;

    private Faculty faculty;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FacultyResource facultyResource = new FacultyResource(facultyService);
        this.restFacultyMockMvc = MockMvcBuilders.standaloneSetup(facultyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Faculty createEntity() {
        Faculty faculty = new Faculty()
                .name(DEFAULT_NAME);
        return faculty;
    }

    @Before
    public void initTest() {
        facultyRepository.deleteAll();
        faculty = createEntity();
    }

    @Test
    public void createFaculty() throws Exception {
        int databaseSizeBeforeCreate = facultyRepository.findAll().size();

        // Create the Faculty

        restFacultyMockMvc.perform(post("/api/faculties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faculty)))
            .andExpect(status().isCreated());

        // Validate the Faculty in the database
        List<Faculty> facultyList = facultyRepository.findAll();
        assertThat(facultyList).hasSize(databaseSizeBeforeCreate + 1);
        Faculty testFaculty = facultyList.get(facultyList.size() - 1);
        assertThat(testFaculty.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    public void createFacultyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = facultyRepository.findAll().size();

        // Create the Faculty with an existing ID
        Faculty existingFaculty = new Faculty();
        existingFaculty.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restFacultyMockMvc.perform(post("/api/faculties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingFaculty)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Faculty> facultyList = facultyRepository.findAll();
        assertThat(facultyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = facultyRepository.findAll().size();
        // set the field null
        faculty.setName(null);

        // Create the Faculty, which fails.

        restFacultyMockMvc.perform(post("/api/faculties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faculty)))
            .andExpect(status().isBadRequest());

        List<Faculty> facultyList = facultyRepository.findAll();
        assertThat(facultyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllFaculties() throws Exception {
        // Initialize the database
        facultyRepository.save(faculty);

        // Get all the facultyList
        restFacultyMockMvc.perform(get("/api/faculties?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(faculty.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    public void getFaculty() throws Exception {
        // Initialize the database
        facultyRepository.save(faculty);

        // Get the faculty
        restFacultyMockMvc.perform(get("/api/faculties/{id}", faculty.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(faculty.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    public void getNonExistingFaculty() throws Exception {
        // Get the faculty
        restFacultyMockMvc.perform(get("/api/faculties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateFaculty() throws Exception {
        // Initialize the database
        facultyService.save(faculty);

        int databaseSizeBeforeUpdate = facultyRepository.findAll().size();

        // Update the faculty
        Faculty updatedFaculty = facultyRepository.findOne(faculty.getId());
        updatedFaculty
                .name(UPDATED_NAME);

        restFacultyMockMvc.perform(put("/api/faculties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFaculty)))
            .andExpect(status().isOk());

        // Validate the Faculty in the database
        List<Faculty> facultyList = facultyRepository.findAll();
        assertThat(facultyList).hasSize(databaseSizeBeforeUpdate);
        Faculty testFaculty = facultyList.get(facultyList.size() - 1);
        assertThat(testFaculty.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    public void updateNonExistingFaculty() throws Exception {
        int databaseSizeBeforeUpdate = facultyRepository.findAll().size();

        // Create the Faculty

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFacultyMockMvc.perform(put("/api/faculties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faculty)))
            .andExpect(status().isCreated());

        // Validate the Faculty in the database
        List<Faculty> facultyList = facultyRepository.findAll();
        assertThat(facultyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteFaculty() throws Exception {
        // Initialize the database
        facultyService.save(faculty);

        int databaseSizeBeforeDelete = facultyRepository.findAll().size();

        // Get the faculty
        restFacultyMockMvc.perform(delete("/api/faculties/{id}", faculty.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Faculty> facultyList = facultyRepository.findAll();
        assertThat(facultyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
