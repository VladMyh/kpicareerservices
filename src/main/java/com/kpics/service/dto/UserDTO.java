package com.kpics.service.dto;

import com.kpics.domain.Authority;
import com.kpics.domain.User;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A DTO representing a user, with his authorities.
 */
public class UserDTO {

    private String id;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Email
    @Size(max = 100)
    private String email;

    @Size(max = 100)
    private String faculty;

    @Size(max = 100)
    private String department;

    @Size(max = 5)
    private String group;

    @Size(max = 200)
    private String about;

    private String github;

    private String linkedin;

    @Size(max = 256)
    private String imageUrl;

    private boolean activated = false;

    @Size(min = 2, max = 5)
    private String langKey;

    private String createdBy;

    private ZonedDateTime createdDate;

    private String lastModifiedBy;

    private ZonedDateTime lastModifiedDate;

    private Set<String> authorities;

    public UserDTO() {
        // Empty constructor needed for MapStruct.
    }

    public UserDTO(User user) {
        this(user.getId(), user.getFirstName(), user.getLastName(),
            user.getEmail(), user.getImageUrl(), user.getActivated(), user.getLangKey(),
            user.getCreatedBy(), user.getCreatedDate(), user.getLastModifiedBy(), user.getLastModifiedDate(),
            user.getAuthorities().stream().map(Authority::getName)
                .collect(Collectors.toSet()));
    }

    public UserDTO(String id, String firstName,
                   String lastName, String email,
                   String imageUrl, boolean activated, String langKey,
                   String createdBy, ZonedDateTime createdDate, String lastModifiedBy,
                   ZonedDateTime lastModifiedDate, Set<String> authorities) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.imageUrl = imageUrl;
        this.activated = activated;
        this.langKey = langKey;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
        this.authorities = authorities;
    }

    public UserDTO(String id, String firstName,
                   String lastName, String email, String faculty,
                   String department, String group, String about, String github,
                   String linkedin, String imageUrl, boolean activated, String langKey,
                   String createdBy, ZonedDateTime createdDate, String lastModifiedBy,
                   ZonedDateTime lastModifiedDate, Set<String> authorities) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.faculty = faculty;
        this.department = department;
        this.group = group;
        this.about = about;
        this.github = github;
        this.linkedin = linkedin;
        this.imageUrl = imageUrl;
        this.activated = activated;
        this.langKey = langKey;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
        this.authorities = authorities;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isActivated() {
        return activated;
    }

    public String getLangKey() {
        return langKey;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public ZonedDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getDepartment() {
        return department;
    }

    public String getGroup() {
        return group;
    }

    public String getAbout() {
        return about;
    }

    public String getGithub() {
        return github;
    }

    public String getLinkedin() {
        return linkedin;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
            "id='" + id + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", faculty='" + faculty + '\'' +
            ", department='" + department + '\'' +
            ", group='" + group + '\'' +
            ", about='" + about + '\'' +
            ", github='" + github + '\'' +
            ", linkedin='" + linkedin + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", activated=" + activated +
            ", langKey='" + langKey + '\'' +
            ", createdBy='" + createdBy + '\'' +
            ", createdDate=" + createdDate +
            ", lastModifiedBy='" + lastModifiedBy + '\'' +
            ", lastModifiedDate=" + lastModifiedDate +
            ", authorities=" + authorities +
            '}';
    }
}
