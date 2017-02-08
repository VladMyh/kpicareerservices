package com.kpics.service.dto;

import javax.validation.constraints.Size;

/**
 * A DTO representing a student.
 */
public class StudentDTO{
    @Size(max = 100)
    private String faculty;

    @Size(max = 100)
    private String department;

    @Size(max = 5)
    private String group;

    @Size(max = 200)
    private String about;

    private String github;

    public StudentDTO() {}

    public StudentDTO(String faculty, String department, String group, String about, String github) {
        this.faculty = faculty;
        this.department = department;
        this.group = group;
        this.about = about;
        this.github = github;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    @Override
    public String toString() {
        return "StudentDTO{" +
            "faculty='" + faculty + '\'' +
            ", department='" + department + '\'' +
            ", group='" + group + '\'' +
            ", about='" + about + '\'' +
            ", github='" + github + '\'' +
            '}';
    }
}
