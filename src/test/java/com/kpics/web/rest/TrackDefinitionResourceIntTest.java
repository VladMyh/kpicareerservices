package com.kpics.web.rest;

import com.google.common.collect.Sets;
import com.kpics.KpicsApp;

import com.kpics.domain.TrackDefinition;
import com.kpics.repository.TrackDefinitionRepository;
import com.kpics.service.TrackDefinitionService;

import org.apache.commons.lang3.ArrayUtils;
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
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TrackDefinitionResource REST controller.
 *
 * @see TrackDefinitionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KpicsApp.class)
public class TrackDefinitionResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Set<String> DEFAULT_SKILL_DEFINITIONS = Sets.newHashSet("a", "b", "c");
    private static final Set<String> UPDATED_SKILL_DEFINITIONS = Sets.newHashSet("d", "e", "f");

    @Autowired
    private TrackDefinitionRepository trackDefinitionRepository;

    @Autowired
    private TrackDefinitionService trackDefinitionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTrackDefinitionMockMvc;

    private TrackDefinition trackDefinition;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TrackDefinitionResource trackDefinitionResource = new TrackDefinitionResource(trackDefinitionService);
        this.restTrackDefinitionMockMvc = MockMvcBuilders.standaloneSetup(trackDefinitionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrackDefinition createEntity() {
        TrackDefinition trackDefinition = new TrackDefinition()
                .name(DEFAULT_NAME)
                .skillDefinitions(DEFAULT_SKILL_DEFINITIONS);
        return trackDefinition;
    }

    @Before
    public void initTest() {
        trackDefinitionRepository.deleteAll();
        trackDefinition = createEntity();
    }

    @Test
    public void createTrackDefinition() throws Exception {
        int databaseSizeBeforeCreate = trackDefinitionRepository.findAll().size();

        // Create the TrackDefinition

        restTrackDefinitionMockMvc.perform(post("/api/track-definitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trackDefinition)))
            .andExpect(status().isCreated());

        // Validate the TrackDefinition in the database
        List<TrackDefinition> trackDefinitionList = trackDefinitionRepository.findAll();
        assertThat(trackDefinitionList).hasSize(databaseSizeBeforeCreate + 1);
        TrackDefinition testTrackDefinition = trackDefinitionList.get(trackDefinitionList.size() - 1);
        assertThat(testTrackDefinition.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTrackDefinition.getSkillDefinitions()).isEqualTo(DEFAULT_SKILL_DEFINITIONS);
    }

    @Test
    public void createTrackDefinitionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = trackDefinitionRepository.findAll().size();

        // Create the TrackDefinition with an existing ID
        TrackDefinition existingTrackDefinition = new TrackDefinition();
        existingTrackDefinition.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrackDefinitionMockMvc.perform(post("/api/track-definitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTrackDefinition)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TrackDefinition> trackDefinitionList = trackDefinitionRepository.findAll();
        assertThat(trackDefinitionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = trackDefinitionRepository.findAll().size();
        // set the field null
        trackDefinition.setName(null);

        // Create the TrackDefinition, which fails.

        restTrackDefinitionMockMvc.perform(post("/api/track-definitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trackDefinition)))
            .andExpect(status().isBadRequest());

        List<TrackDefinition> trackDefinitionList = trackDefinitionRepository.findAll();
        assertThat(trackDefinitionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllTrackDefinitions() throws Exception {
        // Initialize the database
        trackDefinitionRepository.save(trackDefinition);

        // Get all the trackDefinitionList
        restTrackDefinitionMockMvc.perform(get("/api/track-definitions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trackDefinition.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .equals(jsonPath("$.[*].skillDefinitions").value(new String[0]).equals(DEFAULT_SKILL_DEFINITIONS));
    }

    @Test
    public void getTrackDefinition() throws Exception {
        // Initialize the database
        trackDefinitionRepository.save(trackDefinition);

        // Get the trackDefinition
        restTrackDefinitionMockMvc.perform(get("/api/track-definitions/{id}", trackDefinition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(trackDefinition.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .equals(jsonPath("$.skillDefinitions").value(new String[0]).equals(DEFAULT_SKILL_DEFINITIONS));
    }

    @Test
    public void getNonExistingTrackDefinition() throws Exception {
        // Get the trackDefinition
        restTrackDefinitionMockMvc.perform(get("/api/track-definitions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateTrackDefinition() throws Exception {
        // Initialize the database
        trackDefinitionService.save(trackDefinition);

        int databaseSizeBeforeUpdate = trackDefinitionRepository.findAll().size();

        // Update the trackDefinition
        TrackDefinition updatedTrackDefinition = trackDefinitionRepository.findOne(trackDefinition.getId());
        updatedTrackDefinition
                .name(UPDATED_NAME)
                .skillDefinitions(UPDATED_SKILL_DEFINITIONS);

        restTrackDefinitionMockMvc.perform(put("/api/track-definitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTrackDefinition)))
            .andExpect(status().isOk());

        // Validate the TrackDefinition in the database
        List<TrackDefinition> trackDefinitionList = trackDefinitionRepository.findAll();
        assertThat(trackDefinitionList).hasSize(databaseSizeBeforeUpdate);
        TrackDefinition testTrackDefinition = trackDefinitionList.get(trackDefinitionList.size() - 1);
        assertThat(testTrackDefinition.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTrackDefinition.getSkillDefinitions()).isEqualTo(UPDATED_SKILL_DEFINITIONS);
    }

    @Test
    public void updateNonExistingTrackDefinition() throws Exception {
        int databaseSizeBeforeUpdate = trackDefinitionRepository.findAll().size();

        // Create the TrackDefinition

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTrackDefinitionMockMvc.perform(put("/api/track-definitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trackDefinition)))
            .andExpect(status().isCreated());

        // Validate the TrackDefinition in the database
        List<TrackDefinition> trackDefinitionList = trackDefinitionRepository.findAll();
        assertThat(trackDefinitionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteTrackDefinition() throws Exception {
        // Initialize the database
        trackDefinitionService.save(trackDefinition);

        int databaseSizeBeforeDelete = trackDefinitionRepository.findAll().size();

        // Get the trackDefinition
        restTrackDefinitionMockMvc.perform(delete("/api/track-definitions/{id}", trackDefinition.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TrackDefinition> trackDefinitionList = trackDefinitionRepository.findAll();
        assertThat(trackDefinitionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
