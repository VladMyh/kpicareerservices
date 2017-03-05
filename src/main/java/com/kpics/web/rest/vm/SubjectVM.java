package com.kpics.web.rest.vm;

public class SubjectVM {

    private String id;

    private String name;

    private TeacherVM teacher;

    private Integer semester;

    public SubjectVM() {}

    public SubjectVM(String id, String name, TeacherVM teacher, Integer semester) {
        this.id = id;
        this.name = name;
        this.teacher = teacher;
        this.semester = semester;
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

    public TeacherVM getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherVM teacher) {
        this.teacher = teacher;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    @Override
    public String toString() {
        return "SubjectVM{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", teacher=" + teacher +
            ", semester=" + semester +
            '}';
    }
}
