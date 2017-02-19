package com.kpics.web.rest.vm;

import com.kpics.domain.StudentInfo;
import com.kpics.service.dto.UserDTO;

public class StudentVM {
    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private String linkedin;

    private String github;

    private boolean activated;

    private String faculty;

    private String department;

    private String group;

    private String about;

    public StudentVM() {}

    public StudentVM(String id, String firstName, String lastName, String email,
                     String linkedin, boolean activated, String faculty,
                     String department, String about) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.linkedin = linkedin;
        this.activated = activated;
        this.faculty = faculty;
        this.department = department;
        this.about = about;
    }

    public StudentVM(UserDTO userDTO) {
        this.firstName = userDTO.getFirstName();
        this.lastName = userDTO.getLastName();
        this.email = userDTO.getEmail();
        this.linkedin = userDTO.getLinkedin();
        this.github = userDTO.getStudentInfo().getGithub();
        this.activated = userDTO.isActivated();
        this.faculty = userDTO.getStudentInfo().getFaculty();
        this.department = userDTO.getStudentInfo().getDepartment();
        this.group = userDTO.getStudentInfo().getGroup();
        this.about = userDTO.getStudentInfo().getAbout();
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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
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

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "StudentVM{" +
            "id='" + id + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", linkedin='" + linkedin + '\'' +
            ", github='" + github + '\'' +
            ", activated=" + activated +
            ", faculty='" + faculty + '\'' +
            ", department='" + department + '\'' +
            ", group='" + group + '\'' +
            ", about='" + about + '\'' +
            '}';
    }
}
