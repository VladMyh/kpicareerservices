package com.kpics.domain;

import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A TeacherInfo.
 */

public class TeacherInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 100)
    @Field("faculty")
    private String faculty;

    @NotNull
    @Size(max = 100)
    @Field("department")
    private String department;

    @Field("about")
    private String about;

    public TeacherInfo() {}

    public TeacherInfo(String faculty, String department, String about) {
        this.faculty = faculty;
        this.department = department;
        this.about = about;
    }

    public String getFaculty() {
        return faculty;
    }

    public TeacherInfo faculty(String faculty) {
        this.faculty = faculty;
        return this;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getDepartment() {
        return department;
    }

    public TeacherInfo department(String department) {
        this.department = department;
        return this;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getAbout() {
        return about;
    }

    public TeacherInfo about(String about) {
        this.about = about;
        return this;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TeacherInfo that = (TeacherInfo) o;

        if (faculty != null ? !faculty.equals(that.faculty) : that.faculty != null) return false;
        if (department != null ? !department.equals(that.department) : that.department != null) return false;
        return about != null ? about.equals(that.about) : that.about == null;
    }

    @Override
    public int hashCode() {
        int result = faculty != null ? faculty.hashCode() : 0;
        result = 31 * result + (department != null ? department.hashCode() : 0);
        result = 31 * result + (about != null ? about.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TeacherInfo{" +
            "faculty='" + faculty + "'" +
            ", department='" + department + "'" +
            ", about='" + about + "'" +
            '}';
    }
}
