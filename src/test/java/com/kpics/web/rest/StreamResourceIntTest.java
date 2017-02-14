package com.kpics.web.rest;

import com.kpics.KpicsApp;

import com.kpics.domain.Stream;
import com.kpics.repository.StreamRepository;
import com.kpics.service.StreamService;

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
 * Test class for the StreamResource REST controller.
 *
 * @see StreamResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KpicsApp.class)
public class StreamResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private StreamRepository streamRepository;

    @Autowired
    private StreamService streamService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restStreamMockMvc;

    private Stream stream;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StreamResource streamResource = new StreamResource(streamService);
        this.restStreamMockMvc = MockMvcBuilders.standaloneSetup(streamResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stream createEntity() {
        Stream stream = new Stream()
                .name(DEFAULT_NAME)
                .startDate(DEFAULT_START_DATE)
                .endDate(DEFAULT_END_DATE);
        return stream;
    }

    @Before
    public void initTest() {
        streamRepository.deleteAll();
        stream = createEntity();
    }

    @Test
    public void createStream() throws Exception {
        int databaseSizeBeforeCreate = streamRepository.findAll().size();

        // Create the Stream

        restStreamMockMvc.perform(post("/api/streams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stream)))
            .andExpect(status().isCreated());

        // Validate the Stream in the database
        List<Stream> streamList = streamRepository.findAll();
        assertThat(streamList).hasSize(databaseSizeBeforeCreate + 1);
        Stream testStream = streamList.get(streamList.size() - 1);
        assertThat(testStream.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStream.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testStream.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    public void createStreamWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = streamRepository.findAll().size();

        // Create the Stream with an existing ID
        Stream existingStream = new Stream();
        existingStream.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restStreamMockMvc.perform(post("/api/streams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingStream)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Stream> streamList = streamRepository.findAll();
        assertThat(streamList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = streamRepository.findAll().size();
        // set the field null
        stream.setName(null);

        // Create the Stream, which fails.

        restStreamMockMvc.perform(post("/api/streams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stream)))
            .andExpect(status().isBadRequest());

        List<Stream> streamList = streamRepository.findAll();
        assertThat(streamList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = streamRepository.findAll().size();
        // set the field null
        stream.setStartDate(null);

        // Create the Stream, which fails.

        restStreamMockMvc.perform(post("/api/streams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stream)))
            .andExpect(status().isBadRequest());

        List<Stream> streamList = streamRepository.findAll();
        assertThat(streamList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = streamRepository.findAll().size();
        // set the field null
        stream.setEndDate(null);

        // Create the Stream, which fails.

        restStreamMockMvc.perform(post("/api/streams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stream)))
            .andExpect(status().isBadRequest());

        List<Stream> streamList = streamRepository.findAll();
        assertThat(streamList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllStreams() throws Exception {
        // Initialize the database
        streamRepository.save(stream);

        // Get all the streamList
        restStreamMockMvc.perform(get("/api/streams?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stream.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }

    @Test
    public void getStream() throws Exception {
        // Initialize the database
        streamRepository.save(stream);

        // Get the stream
        restStreamMockMvc.perform(get("/api/streams/{id}", stream.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stream.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()));
    }

    @Test
    public void getNonExistingStream() throws Exception {
        // Get the stream
        restStreamMockMvc.perform(get("/api/streams/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateStream() throws Exception {
        // Initialize the database
        streamService.save(stream);

        int databaseSizeBeforeUpdate = streamRepository.findAll().size();

        // Update the stream
        Stream updatedStream = streamRepository.findOne(stream.getId());
        updatedStream
                .name(UPDATED_NAME)
                .startDate(UPDATED_START_DATE)
                .endDate(UPDATED_END_DATE);

        restStreamMockMvc.perform(put("/api/streams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStream)))
            .andExpect(status().isOk());

        // Validate the Stream in the database
        List<Stream> streamList = streamRepository.findAll();
        assertThat(streamList).hasSize(databaseSizeBeforeUpdate);
        Stream testStream = streamList.get(streamList.size() - 1);
        assertThat(testStream.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStream.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testStream.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    public void updateNonExistingStream() throws Exception {
        int databaseSizeBeforeUpdate = streamRepository.findAll().size();

        // Create the Stream

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restStreamMockMvc.perform(put("/api/streams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stream)))
            .andExpect(status().isCreated());

        // Validate the Stream in the database
        List<Stream> streamList = streamRepository.findAll();
        assertThat(streamList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteStream() throws Exception {
        // Initialize the database
        streamService.save(stream);

        int databaseSizeBeforeDelete = streamRepository.findAll().size();

        // Get the stream
        restStreamMockMvc.perform(delete("/api/streams/{id}", stream.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Stream> streamList = streamRepository.findAll();
        assertThat(streamList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Stream.class);
    }
}
