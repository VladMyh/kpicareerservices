package com.kpics.web.rest;

import com.kpics.KpicsApp;
import com.kpics.domain.Authority;
import com.kpics.domain.User;
import com.kpics.repository.AuthorityRepository;
import com.kpics.repository.UserRepository;
import com.kpics.security.AuthoritiesConstants;
import com.kpics.service.MailService;
import com.kpics.service.StudentInfoService;
import com.kpics.service.UserService;
import com.kpics.web.rest.vm.ManagedUserVM;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AccountResource REST controller.
 *
 * @see UserService
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KpicsApp.class)
public class AccountResourceIntTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private StudentInfoService studentInfoService;

    @Mock
    private UserService mockUserService;

    @Mock
    private MailService mockMailService;

    private MockMvc restUserMockMvc;

    private MockMvc restMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        doNothing().when(mockMailService).sendActivationEmail((User) anyObject());

        AccountResource accountResource =
            new AccountResource(userRepository, userService, studentInfoService, mockMailService);

        AccountResource accountUserMockResource =
            new AccountResource(userRepository, mockUserService, studentInfoService, mockMailService);

        this.restMvc = MockMvcBuilders.standaloneSetup(accountResource).build();
        this.restUserMockMvc = MockMvcBuilders.standaloneSetup(accountUserMockResource).build();
    }

    @Test
    public void testNonAuthenticatedUser() throws Exception {
        restUserMockMvc.perform(get("/api/authenticate")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    public void testAuthenticatedUser() throws Exception {
        restUserMockMvc.perform(get("/api/authenticate")
                .with(request -> {
                    request.setRemoteUser("test");
                    return request;
                })
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("test"));
    }

    @Test
    public void testGetExistingAccount() throws Exception {
        Set<Authority> authorities = new HashSet<>();
        Authority authority = new Authority();
        authority.setName(AuthoritiesConstants.ADMIN);
        authorities.add(authority);

        User user = new User();
        user.setFirstName("john");
        user.setLastName("doe");
        user.setEmail("john.doe@jhipster.com");
        user.setImageUrl("http://placehold.it/50x50");
        user.setAuthorities(authorities);
        when(mockUserService.getUserWithAuthorities()).thenReturn(user);

        restUserMockMvc.perform(get("/api/account")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.firstName").value("john"))
                .andExpect(jsonPath("$.lastName").value("doe"))
                .andExpect(jsonPath("$.email").value("john.doe@jhipster.com"))
                .andExpect(jsonPath("$.imageUrl").value("http://placehold.it/50x50"))
                .andExpect(jsonPath("$.authorities").value(AuthoritiesConstants.ADMIN));
    }

    @Test
    public void testGetUnknownAccount() throws Exception {
        when(mockUserService.getUserWithAuthorities()).thenReturn(null);

        restUserMockMvc.perform(get("/api/account")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void testRegisterValid() throws Exception {
        ManagedUserVM validUser = new ManagedUserVM(
            null,                   // id
            "password",             // password
            "Joe",                  // firstName
            "Shmoe",                // lastName
            "v",      // e-mail
            null,                   // faculty
            null,                   //department
            null,                   //group
            null,                   //about
            null,                   //github
            null,                   //linkedin
            true,                   // activated
            "http://placehold.it/50x50", //imageUrl
            "ru",                   // langKey
            null,                   // createdBy
            null,                   // createdDate
            null,                   // lastModifiedBy
            null,                   // lastModifiedDate
            new HashSet<>(Arrays.asList(AuthoritiesConstants.USER)));

        restMvc.perform(
            post("/api/register")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(validUser)))
            .andExpect(status().isCreated());

        Optional<User> user = userRepository.findOneByEmail("b");
        assertThat(user.isPresent()).isTrue();
    }

    @Test
    public void testRegisterInvalidEmail() throws Exception {
        ManagedUserVM invalidUser = new ManagedUserVM(
            null,                   // id
            "password",             // password
            "Bob",                  // firstName
            "Green",                // lastName
            "invalid",              // e-mail <-- invalid
            null,                   // faculty
            null,                   //department
            null,                   //group
            null,                   //about
            null,                   //github
            null,                   //linkedin
            true,                   // activated
            "http://placehold.it/50x50", //imageUrl
            "ru",                   // langKey
            null,                   // createdBy
            null,                   // createdDate
            null,                   // lastModifiedBy
            null,                   // lastModifiedDate
            new HashSet<>(Arrays.asList(AuthoritiesConstants.USER)));

        restUserMockMvc.perform(
            post("/api/register")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(invalidUser)))
            .andExpect(status().isBadRequest());

        Optional<User> user = userRepository.findOneByEmail("invalid");
        assertThat(user.isPresent()).isFalse();
    }

    @Test
    public void testRegisterInvalidPassword() throws Exception {
        ManagedUserVM invalidUser = new ManagedUserVM(
            null,                    // id
            "123",                   // password with only 3 digits
            "Bob",                   // firstName
            "Green",                 // lastName
            "bob@example.com",       // e-mail
            null,                   // faculty
            null,                   //department
            null,                   //group
            null,                   //about
            null,                   //github
            null,                   //linkedin
            true,                    // activated
            "http://placehold.it/50x50", //imageUrl
            "ru",                   // langKey
            null,                   // createdBy
            null,                   // createdDate
            null,                   // lastModifiedBy
            null,                   // lastModifiedDate
            new HashSet<>(Arrays.asList(AuthoritiesConstants.USER)));

        restUserMockMvc.perform(
            post("/api/register")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(invalidUser)))
            .andExpect(status().isBadRequest());

        Optional<User> user = userRepository.findOneByEmail("bob@example.com");
        assertThat(user.isPresent()).isFalse();
    }

    @Test
    public void testRegisterDuplicateLogin() throws Exception {
        // Good
        ManagedUserVM validUser = new ManagedUserVM(
            null,                   // id
            "password",             // password
            "Alice",                // firstName
            "Something",            // lastName
            "alice@example.com",    // e-mail
            null,                   // faculty
            null,                   //department
            null,                   //group
            null,                   //about
            null,                   //github
            null,                   //linkedin
            true,                   // activated
            "http://placehold.it/50x50", //imageUrl
            "ru",                   // langKey
            null,                   // createdBy
            null,                   // createdDate
            null,                   // lastModifiedBy
            null,                   // lastModifiedDate
            new HashSet<>(Arrays.asList(AuthoritiesConstants.USER)));

        // Duplicate login, different e-mail
        ManagedUserVM duplicatedUser = new ManagedUserVM(validUser.getId(), validUser.getPassword(), validUser.getFirstName(), validUser.getLastName(),
            "alicejr@example.com", null, null, null, null, null, null, true, validUser.getImageUrl(), validUser.getLangKey(), validUser.getCreatedBy(), validUser.getCreatedDate(), validUser.getLastModifiedBy(), validUser.getLastModifiedDate(), validUser.getAuthorities());

        // Good user
        restMvc.perform(
            post("/api/register")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(validUser)))
            .andExpect(status().isCreated());

        // Duplicate login
        restMvc.perform(
            post("/api/register")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(duplicatedUser)))
            .andExpect(status().is4xxClientError());

        Optional<User> userDup = userRepository.findOneByEmail("alicejr@example.com");
        assertThat(userDup.isPresent()).isFalse();
    }

    @Test
    public void testRegisterDuplicateEmail() throws Exception {
        // Good
        ManagedUserVM validUser = new ManagedUserVM(
            null,                   // id
            "password",             // password
            "John",                 // firstName
            "Doe",                  // lastName
            "john@example.com",     // e-mail
            null,                   // faculty
            null,                   //department
            null,                   //group
            null,                   //about
            null,                   //github
            null,                   //linkedin
            true,                   // activated
            "http://placehold.it/50x50", //imageUrl
            "ru",                   // langKey
            null,                   // createdBy
            null,                   // createdDate
            null,                   // lastModifiedBy
            null,                   // lastModifiedDate
            new HashSet<>(Arrays.asList(AuthoritiesConstants.USER)));

        // Duplicate e-mail
        ManagedUserVM duplicatedUser = new ManagedUserVM(validUser.getId(), validUser.getPassword(), validUser.getFirstName(), validUser.getLastName(),
            validUser.getEmail(), null, null, null, null, null, null, true, validUser.getImageUrl(), validUser.getLangKey(), validUser.getCreatedBy(), validUser.getCreatedDate(), validUser.getLastModifiedBy(), validUser.getLastModifiedDate(), validUser.getAuthorities());

        // Good user
        restMvc.perform(
            post("/api/register")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(validUser)))
            .andExpect(status().isCreated());

        // Duplicate e-mail
        restMvc.perform(
            post("/api/register")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(duplicatedUser)))
            .andExpect(status().is4xxClientError());

        Optional<User> userDup = userRepository.findOneByEmail("john@example.com");
        assertThat(userDup.isPresent()).isFalse();
    }

    @Test
    public void testRegisterAdminIsIgnored() throws Exception {
        ManagedUserVM validUser = new ManagedUserVM(
            null,                   // id
            "password",             // password
            "Bad",                  // firstName
            "Guy",                  // lastName
            "badguy@example.com",   // e-mail
            null,                   // faculty
            null,                   //department
            null,                   //group
            null,                   //about
            null,                   //github
            null,                   //linkedin
            true,                   // activated
            "http://placehold.it/50x50", //imageUrl
            "ru",                   // langKey
            null,                   // createdBy
            null,                   // createdDate
            null,                   // lastModifiedBy
            null,                   // lastModifiedDate
            new HashSet<>(Arrays.asList(AuthoritiesConstants.ADMIN)));

        restMvc.perform(
            post("/api/register")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(validUser)))
            .andExpect(status().isCreated());

        Optional<User> userDup = userRepository.findOneByEmail("badguy@example.com");
        assertThat(userDup.isPresent()).isTrue();
        assertThat(userDup.get().getAuthorities()).hasSize(1)
            .containsExactly(authorityRepository.findOne(AuthoritiesConstants.USER));
    }
}
