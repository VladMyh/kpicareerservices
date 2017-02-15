package com.kpics.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * A Subject
 */

public class Subject {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("name")
    private String name;

    @NotNull
    @Field("teacher")
    private String teacherId;

    @NotNull
    @Size(min = 1, max = 2)
    @Field("semester")
    private Integer semester;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Subject id(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Subject name(String name) {
        this.name = name;
        return this;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public Subject teacherId(String teacherId) {
        this.teacherId = teacherId;
        return this;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public Subject semester(Integer semester) {
        this.semester = semester;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subject subject = (Subject) o;

        if (id != null ? !id.equals(subject.id) : subject.id != null) return false;
        if (name != null ? !name.equals(subject.name) : subject.name != null) return false;
        if (teacherId != null ? !teacherId.equals(subject.teacherId) : subject.teacherId != null) return false;
        return semester != null ? semester.equals(subject.semester) : subject.semester == null;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Subject{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", teacherId='" + teacherId + '\'' +
            ", semester=" + semester +
            '}';
    }
}
