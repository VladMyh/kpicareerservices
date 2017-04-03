package com.kpics.web.rest;

import com.kpics.KpicsApp;

import com.kpics.domain.Idea;
import com.kpics.repository.IdeaRepository;
import com.kpics.service.IdeaService;

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
 * Test class for the IdeaResource REST controller.
 *
 * @see IdeaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KpicsApp.class)
public class IdeaResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DEADLINE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DEADLINE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_WEBSITE = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_WEBSITE = "BBBBBBBBBB";

    @Autowired
    private IdeaRepository ideaRepository;

    @Autowired
    private IdeaService ideaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restIdeaMockMvc;

    private Idea idea;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        IdeaResource ideaResource = new IdeaResource(ideaService);
        this.restIdeaMockMvc = MockMvcBuilders.standaloneSetup(ideaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Idea createEntity() {
        Idea idea = new Idea()
                .description(DEFAULT_DESCRIPTION)
                .name(DEFAULT_NAME)
                .createDate(DEFAULT_CREATE_DATE)
                .deadlineDate(DEFAULT_DEADLINE_DATE)
                .companyName(DEFAULT_COMPANY_NAME)
                .companyWebsite(DEFAULT_COMPANY_WEBSITE);
        return idea;
    }

    @Before
    public void initTest() {
        ideaRepository.deleteAll();
        idea = createEntity();
    }

    @Test
    public void createIdea() throws Exception {
        int databaseSizeBeforeCreate = ideaRepository.findAll().size();

        // Create the Idea

        restIdeaMockMvc.perform(post("/api/ideas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(idea)))
            .andExpect(status().isCreated());

        // Validate the Idea in the database
        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeCreate + 1);
        Idea testIdea = ideaList.get(ideaList.size() - 1);
        assertThat(testIdea.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testIdea.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testIdea.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testIdea.getDeadlineDate()).isEqualTo(DEFAULT_DEADLINE_DATE);
        assertThat(testIdea.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testIdea.getCompanyWebsite()).isEqualTo(DEFAULT_COMPANY_WEBSITE);
    }

    @Test
    public void createIdeaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ideaRepository.findAll().size();

        // Create the Idea with an existing ID
        Idea existingIdea = new Idea();
        existingIdea.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restIdeaMockMvc.perform(post("/api/ideas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingIdea)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = ideaRepository.findAll().size();
        // set the field null
        idea.setDescription(null);

        // Create the Idea, which fails.

        restIdeaMockMvc.perform(post("/api/ideas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(idea)))
            .andExpect(status().isBadRequest());

        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = ideaRepository.findAll().size();
        // set the field null
        idea.setName(null);

        // Create the Idea, which fails.

        restIdeaMockMvc.perform(post("/api/ideas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(idea)))
            .andExpect(status().isBadRequest());

        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCreateDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = ideaRepository.findAll().size();
        // set the field null
        idea.setCreateDate(null);

        // Create the Idea, which fails.

        restIdeaMockMvc.perform(post("/api/ideas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(idea)))
            .andExpect(status().isBadRequest());

        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDeadlineDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = ideaRepository.findAll().size();
        // set the field null
        idea.setDeadlineDate(null);

        // Create the Idea, which fails.

        restIdeaMockMvc.perform(post("/api/ideas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(idea)))
            .andExpect(status().isBadRequest());

        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCompanyNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = ideaRepository.findAll().size();
        // set the field null
        idea.setCompanyName(null);

        // Create the Idea, which fails.

        restIdeaMockMvc.perform(post("/api/ideas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(idea)))
            .andExpect(status().isBadRequest());

        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllIdeas() throws Exception {
        // Initialize the database
        ideaRepository.save(idea);

        // Get all the ideaList
        restIdeaMockMvc.perform(get("/api/ideas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(idea.getId())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].deadlineDate").value(hasItem(DEFAULT_DEADLINE_DATE.toString())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())))
            .andExpect(jsonPath("$.[*].companyWebsite").value(hasItem(DEFAULT_COMPANY_WEBSITE.toString())));
    }

    @Test
    public void getIdea() throws Exception {
        // Initialize the database
        ideaRepository.save(idea);

        // Get the idea
        restIdeaMockMvc.perform(get("/api/ideas/{id}", idea.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(idea.getId()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.deadlineDate").value(DEFAULT_DEADLINE_DATE.toString()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME.toString()))
            .andExpect(jsonPath("$.companyWebsite").value(DEFAULT_COMPANY_WEBSITE.toString()));
    }

    @Test
    public void getNonExistingIdea() throws Exception {
        // Get the idea
        restIdeaMockMvc.perform(get("/api/ideas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateIdea() throws Exception {
        // Initialize the database
        ideaService.save(idea);

        int databaseSizeBeforeUpdate = ideaRepository.findAll().size();

        // Update the idea
        Idea updatedIdea = ideaRepository.findOne(idea.getId());
        updatedIdea
                .description(UPDATED_DESCRIPTION)
                .name(UPDATED_NAME)
                .createDate(UPDATED_CREATE_DATE)
                .deadlineDate(UPDATED_DEADLINE_DATE)
                .companyName(UPDATED_COMPANY_NAME)
                .companyWebsite(UPDATED_COMPANY_WEBSITE);

        restIdeaMockMvc.perform(put("/api/ideas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIdea)))
            .andExpect(status().isOk());

        // Validate the Idea in the database
        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeUpdate);
        Idea testIdea = ideaList.get(ideaList.size() - 1);
        assertThat(testIdea.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testIdea.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testIdea.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testIdea.getDeadlineDate()).isEqualTo(UPDATED_DEADLINE_DATE);
        assertThat(testIdea.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testIdea.getCompanyWebsite()).isEqualTo(UPDATED_COMPANY_WEBSITE);
    }

    @Test
    public void updateNonExistingIdea() throws Exception {
        int databaseSizeBeforeUpdate = ideaRepository.findAll().size();

        // Create the Idea

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restIdeaMockMvc.perform(put("/api/ideas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(idea)))
            .andExpect(status().isCreated());

        // Validate the Idea in the database
        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteIdea() throws Exception {
        // Initialize the database
        ideaService.save(idea);

        int databaseSizeBeforeDelete = ideaRepository.findAll().size();

        // Get the idea
        restIdeaMockMvc.perform(delete("/api/ideas/{id}", idea.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Idea.class);
    }
}
