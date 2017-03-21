package com.kpics.domain;

import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

/**
 * A StudentInfo.
 */

public class StudentInfo implements Serializable {

    private static final long serialVersionUID = 1L;

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

    @Size(max = 200)
    @Field("about")
    private String about;

    @NotNull
    @Field("skills")
    private Set<Skill> skills;

    public StudentInfo() {}

    public StudentInfo(String faculty, String department, String group, String github, String about) {
        this.faculty = faculty;
        this.department = department;
        this.group = group;
        this.github = github;
        this.about = about;
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

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentInfo that = (StudentInfo) o;

        if (faculty != null ? !faculty.equals(that.faculty) : that.faculty != null) return false;
        if (department != null ? !department.equals(that.department) : that.department != null) return false;
        if (group != null ? !group.equals(that.group) : that.group != null) return false;
        if (github != null ? !github.equals(that.github) : that.github != null) return false;
        if (about != null ? !about.equals(that.about) : that.about != null) return false;
        return skills != null ? skills.equals(that.skills) : that.skills == null;
    }

    @Override
    public int hashCode() {
        int result = faculty != null ? faculty.hashCode() : 0;
        result = 31 * result + (department != null ? department.hashCode() : 0);
        result = 31 * result + (group != null ? group.hashCode() : 0);
        result = 31 * result + (github != null ? github.hashCode() : 0);
        result = 31 * result + (about != null ? about.hashCode() : 0);
        result = 31 * result + (skills != null ? skills.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "StudentInfo{" +
            "faculty='" + faculty + '\'' +
            ", department='" + department + '\'' +
            ", group='" + group + '\'' +
            ", github='" + github + '\'' +
            ", about='" + about + '\'' +
            '}';
    }
}
