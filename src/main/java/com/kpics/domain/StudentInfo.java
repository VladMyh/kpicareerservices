package com.kpics.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A StudentInfo.
 */

@Document(collection = "student_info")
public class StudentInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(max = 100)
    @Field("faculty")
    private String faculty;

    @NotNull
    @Size(max = 100)
    @Field("department")
    private String department;

    @NotNull
    @Size(max = 10)
    @Field("group")
    private String group;

    @NotNull
    @Field("github")
    private String github;

    @NotNull
    @Field("linkedin")
    private String linkedin;

    @Size(max = 200)
    @Field("about")
    private String about;

    @Field("user")
    private String userId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFaculty() {
        return faculty;
    }

    public StudentInfo faculty(String faculty) {
        this.faculty = faculty;
        return this;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getDepartment() {
        return department;
    }

    public StudentInfo department(String department) {
        this.department = department;
        return this;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getGroup() {
        return group;
    }

    public StudentInfo group(String group) {
        this.group = group;
        return this;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGithub() {
        return github;
    }

    public StudentInfo github(String github) {
        this.github = github;
        return this;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public StudentInfo linkedin(String linkedin) {
        this.linkedin = linkedin;
        return this;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public String getAbout() {
        return about;
    }

    public StudentInfo about(String about) {
        this.about = about;
        return this;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public StudentInfo user(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUser(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentInfo that = (StudentInfo) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (faculty != null ? !faculty.equals(that.faculty) : that.faculty != null) return false;
        if (department != null ? !department.equals(that.department) : that.department != null) return false;
        if (group != null ? !group.equals(that.group) : that.group != null) return false;
        if (github != null ? !github.equals(that.github) : that.github != null) return false;
        if (linkedin != null ? !linkedin.equals(that.linkedin) : that.linkedin != null) return false;
        if (about != null ? !about.equals(that.about) : that.about != null) return false;
        return userId != null ? userId.equals(that.userId) : that.userId == null;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "StudentInfo{" +
            "id='" + id + '\'' +
            ", faculty='" + faculty + '\'' +
            ", department='" + department + '\'' +
            ", group='" + group + '\'' +
            ", github='" + github + '\'' +
            ", linkedin='" + linkedin + '\'' +
            ", about='" + about + '\'' +
            ", userId='" + userId + '\'' +
            '}';
    }
}
