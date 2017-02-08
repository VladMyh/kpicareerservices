package com.kpics.web.rest.vm;

import com.kpics.domain.TeacherInfo;
import com.kpics.service.dto.UserDTO;

public class TeacherVM {
    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private String linkedin;

    private boolean activated;

    private String faculty;

    private String department;

    private String about;

    private String userId;

    public TeacherVM() {}

    public TeacherVM(String id, String firstName, String lastName, String email,
                     String linkedin, boolean activated, String faculty,
                     String department, String about, String userId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.linkedin = linkedin;
        this.activated = activated;
        this.faculty = faculty;
        this.department = department;
        this.about = about;
        this.userId = userId;
    }

    public TeacherVM(UserDTO userDTO, TeacherInfo teacherInfo) {
        this.id = teacherInfo.getId();
        this.firstName = userDTO.getFirstName();
        this.lastName = userDTO.getLastName();
        this.email = userDTO.getEmail();
        this.linkedin = userDTO.getLinkedin();
        this.activated = userDTO.isActivated();
        this.faculty = teacherInfo.getFaculty();
        this.department = teacherInfo.getDepartment();
        this.about = teacherInfo.getAbout();
        this.userId = userDTO.getId();
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
