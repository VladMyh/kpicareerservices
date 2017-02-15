package com.kpics.web.rest.vm;

import com.kpics.domain.Subject;

import java.util.HashSet;
import java.util.Set;

public class TrackVM {

    private String id;

    private String name;

    private String description;

    private Set<TeacherVM> teachers = new HashSet<>();

    private Set<Subject> subjects = new HashSet<>();

    public TrackVM() {}

    public TrackVM(String id, String name, String description, Set<TeacherVM> teachers, Set<Subject> subjects) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.teachers = teachers;
        this.subjects = subjects;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<TeacherVM> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<TeacherVM> teachers) {
        this.teachers = teachers;
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }

    @Override
    public String toString() {
        return "TrackVM{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", teachers=" + teachers +
            ", subjects=" + subjects +
            '}';
    }
}
