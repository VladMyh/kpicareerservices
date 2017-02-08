package com.kpics.service.dto;

import javax.validation.constraints.Size;

/**
 * A DTO representing a teacher.
 */
public class TeacherDTO {
    @Size(max = 100)
    private String faculty;

    @Size(max = 100)
    private String department;

    @Size(max = 200)
    private String about;

    public TeacherDTO() {}

    public TeacherDTO(String faculty, String department, String about) {
        this.faculty = faculty;
        this.department = department;
        this.about = about;
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

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    @Override
    public String toString() {
        return "TeacherDTO{" +
            "faculty='" + faculty + '\'' +
            ", department='" + department + '\'' +
            ", about='" + about + '\'' +
            '}';
    }
}
