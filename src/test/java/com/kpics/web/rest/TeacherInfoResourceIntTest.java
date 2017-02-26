package com.kpics.web.rest;

import com.kpics.KpicsApp;
import com.kpics.domain.Authority;
import com.kpics.domain.TeacherInfo;
import com.kpics.domain.User;
import com.kpics.repository.StreamRepository;
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
 * Test class for the TeacherInfoResource REST controller.
 *
 * @see TeacherInfoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KpicsApp.class)
public class TeacherInfoResourceIntTest {

    private static final String DEFAULT_FNAME = "AAAAAAAAAA";
    private static final String UPDATED_FNAME = "BBBBBBBBBB";

    private static final String DEFAULT_LNAME = "AAAAAAAAAA";
    private static final String UPDATED_LNAME = "BBBBBBBBBB";

    private static final String DEFAULT_FACULTY = "AAAAAAAAAA";
    private static final String UPDATED_FACULTY = "BBBBBBBBBB";

    private static final String DEFAULT_DEPARTMENT = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTMENT = "BBBBBBBBBB";

    private static final String DEFAULT_LINKEDIN = "AAAAAAAAAA";
    private static final String UPDATED_LINKEDIN = "BBBBBBBBBB";

    private static final String DEFAULT_ABOUT = "AAAAAAAAAA";
    private static final String UPDATED_ABOUT = "BBBBBBBBBB";

    private static final String DEFAULT_USERID = "AAAAAAAAAA";
    private static final String UPDATED_USERID = "BBBBBBBBBB";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StreamRepository streamRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTeacherInfoMockMvc;

    private TeacherInfo teacherInfo;

    private User user;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TeacherInfoResource teacherInfoResource = new TeacherInfoResource(userService);
        this.restTeacherInfoMockMvc = MockMvcBuilders.standaloneSetup(teacherInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create teacherInfo entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TeacherInfo createTeacherInfoEntity() {
        TeacherInfo teacherInfo = new TeacherInfo()
                .faculty(DEFAULT_FACULTY)
                .department(DEFAULT_DEPARTMENT)
                .about(DEFAULT_ABOUT);
        return teacherInfo;
    }

    /**
     * Create user entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static User createUserEntity(TeacherInfo teacherInfo) {
        User user = new User();
        user.setId(DEFAULT_USERID);
        user.setFirstName(DEFAULT_FNAME);
        user.setLastName(DEFAULT_LNAME);
        user.setEmail("user@localhost");
        user.setLinkedin(DEFAULT_LINKEDIN);
        user.setPassword("$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC");
        user.setTeacherInfo(teacherInfo);
        user.setAuthorities(new HashSet<>(Arrays.asList(new Authority(AuthoritiesConstants.USER),
                                                        new Authority(AuthoritiesConstants.TEACHER))));
        user.setImageUrl("http://placehold.it/50x50");
        user.setLangKey("ru");
        return user;
    }

    @Before
    public void initTest() {
        userRepository.deleteAll();
        streamRepository.deleteAll();
        teacherInfo = createTeacherInfoEntity();
        user = createUserEntity(teacherInfo);
    }

    @Test
    public void getAllTeachers() throws Exception {
        // Initialize the database
        userRepository.save(user);

        // Get all the teacherList
        restTeacherInfoMockMvc.perform(get("/api/teacher-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(user.getId())))
            .andExpect(jsonPath("$.[*].faculty").value(hasItem(DEFAULT_FACULTY)))
            .andExpect(jsonPath("$.[*].department").value(hasItem(DEFAULT_DEPARTMENT)))
            .andExpect(jsonPath("$.[*].about").value(hasItem(DEFAULT_ABOUT)));
    }

    @Test
    public void getTeacher() throws Exception {
        // Initialize the database
        userRepository.save(user);

        // Get the teacher
        restTeacherInfoMockMvc.perform(get("/api/teacher-infos/{id}", user.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(user.getId()))
            .andExpect(jsonPath("$.faculty").value(DEFAULT_FACULTY))
            .andExpect(jsonPath("$.department").value(DEFAULT_DEPARTMENT))
            .andExpect(jsonPath("$.about").value(DEFAULT_ABOUT));
    }

    @Test
    public void getNonExistingTeacher() throws Exception {
        // Get the teacher
        restTeacherInfoMockMvc.perform(get("/api/teacher-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateTeacher() throws Exception {
        // Initialize the database
        userRepository.save(user);

        int databaseSizeBeforeUpdate = userRepository.findAll().size();

        // Update the teacher
        UserDTO updatedTeacherInfo = new UserDTO(userRepository.findOne(user.getId()));
        updatedTeacherInfo.getTeacherInfo().setFaculty(UPDATED_FACULTY);
        updatedTeacherInfo.getTeacherInfo().setDepartment(UPDATED_DEPARTMENT);
        updatedTeacherInfo.getTeacherInfo().setAbout(UPDATED_ABOUT);

        restTeacherInfoMockMvc.perform(put("/api/teacher-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTeacherInfo)))
            .andExpect(status().isOk());

        // Validate the Teacher in the database
        List<User> teacherInfoList = userRepository.findAll();
        assertThat(teacherInfoList).hasSize(databaseSizeBeforeUpdate);
        User testTeacherInfo = teacherInfoList.get(teacherInfoList.size() - 1);
        assertThat(testTeacherInfo.getTeacherInfo().getFaculty()).isEqualTo(UPDATED_FACULTY);
        assertThat(testTeacherInfo.getTeacherInfo().getDepartment()).isEqualTo(UPDATED_DEPARTMENT);
        assertThat(testTeacherInfo.getTeacherInfo().getAbout()).isEqualTo(UPDATED_ABOUT);
    }

    @Test
    public void deleteTeacher() throws Exception {
        // Initialize the database
        userRepository.save(user);

        int databaseSizeBeforeDelete = userRepository.findAll().size();

        // Get the teacher
        restTeacherInfoMockMvc.perform(delete("/api/teacher-infos/{id}", user.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<User> teacherInfoList = userRepository.findAll();
        assertThat(teacherInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
