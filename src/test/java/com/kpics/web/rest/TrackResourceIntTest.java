package com.kpics.web.rest;

import com.kpics.KpicsApp;

import com.kpics.domain.Track;
import com.kpics.repository.TrackRepository;
import com.kpics.service.TeacherInfoService;
import com.kpics.service.TrackService;

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

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TrackResource REST controller.
 *
 * @see TrackResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KpicsApp.class)
public class TrackResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private TrackService trackService;

    @Autowired
    private TeacherInfoService teacherInfoService;

    @Autowired
    private UserService userService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTrackMockMvc;

    private Track track;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TrackResource trackResource = new TrackResource(trackService, teacherInfoService, userService);
        this.restTrackMockMvc = MockMvcBuilders.standaloneSetup(trackResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Track createEntity() {
        Track track = new Track()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION)
                .isActive(DEFAULT_IS_ACTIVE);
        return track;
    }

    @Before
    public void initTest() {
        trackRepository.deleteAll();
        track = createEntity();
    }

    @Test
    public void createTrack() throws Exception {
        int databaseSizeBeforeCreate = trackRepository.findAll().size();

        // Create the Track

        restTrackMockMvc.perform(post("/api/tracks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(track)))
            .andExpect(status().isCreated());

        // Validate the Track in the database
        List<Track> trackList = trackRepository.findAll();
        assertThat(trackList).hasSize(databaseSizeBeforeCreate + 1);
        Track testTrack = trackList.get(trackList.size() - 1);
        assertThat(testTrack.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTrack.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTrack.isActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    public void createTrackWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = trackRepository.findAll().size();

        // Create the Track with an existing ID
        Track existingTrack = new Track();
        existingTrack.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrackMockMvc.perform(post("/api/tracks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTrack)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Track> trackList = trackRepository.findAll();
        assertThat(trackList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = trackRepository.findAll().size();
        // set the field null
        track.setName(null);

        // Create the Track, which fails.

        restTrackMockMvc.perform(post("/api/tracks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(track)))
            .andExpect(status().isBadRequest());

        List<Track> trackList = trackRepository.findAll();
        assertThat(trackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = trackRepository.findAll().size();
        // set the field null
        track.setDescription(null);

        // Create the Track, which fails.

        restTrackMockMvc.perform(post("/api/tracks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(track)))
            .andExpect(status().isBadRequest());

        List<Track> trackList = trackRepository.findAll();
        assertThat(trackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkIsActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = trackRepository.findAll().size();
        // set the field null
        track.setIsActive(null);

        // Create the Track, which fails.

        restTrackMockMvc.perform(post("/api/tracks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(track)))
            .andExpect(status().isBadRequest());

        List<Track> trackList = trackRepository.findAll();
        assertThat(trackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllTracks() throws Exception {
        // Initialize the database
        trackRepository.save(track);

        // Get all the trackList
        restTrackMockMvc.perform(get("/api/tracks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(track.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)));
    }

    @Test
    public void getTrack() throws Exception {
        // Initialize the database
        trackRepository.save(track);

        // Get the track
        restTrackMockMvc.perform(get("/api/tracks/{id}", track.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(track.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE));
    }

    @Test
    public void getNonExistingTrack() throws Exception {
        // Get the track
        restTrackMockMvc.perform(get("/api/tracks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateTrack() throws Exception {
        // Initialize the database
        trackService.save(track);

        int databaseSizeBeforeUpdate = trackRepository.findAll().size();

        // Update the track
        Track updatedTrack = trackRepository.findOne(track.getId());
        updatedTrack
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION)
                .isActive(UPDATED_IS_ACTIVE);

        restTrackMockMvc.perform(put("/api/tracks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTrack)))
            .andExpect(status().isOk());

        // Validate the Track in the database
        List<Track> trackList = trackRepository.findAll();
        assertThat(trackList).hasSize(databaseSizeBeforeUpdate);
        Track testTrack = trackList.get(trackList.size() - 1);
        assertThat(testTrack.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTrack.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTrack.isActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    public void updateNonExistingTrack() throws Exception {
        int databaseSizeBeforeUpdate = trackRepository.findAll().size();

        // Create the Track

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTrackMockMvc.perform(put("/api/tracks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(track)))
            .andExpect(status().isCreated());

        // Validate the Track in the database
        List<Track> trackList = trackRepository.findAll();
        assertThat(trackList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteTrack() throws Exception {
        // Initialize the database
        trackService.save(track);

        int databaseSizeBeforeDelete = trackRepository.findAll().size();

        // Get the track
        restTrackMockMvc.perform(delete("/api/tracks/{id}", track.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Track> trackList = trackRepository.findAll();
        assertThat(trackList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Track.class);
    }
}
