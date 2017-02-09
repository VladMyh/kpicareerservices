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

        if (!id.equals(that.id)) return false;
        if (!faculty.equals(that.faculty)) return false;
        if (!department.equals(that.department)) return false;
        if (!group.equals(that.group)) return false;
        if (!github.equals(that.github)) return false;
        if (!linkedin.equals(that.linkedin)) return false;
        if (about != null ? !about.equals(that.about) : that.about != null) return false;
        return userId.equals(that.userId);
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
