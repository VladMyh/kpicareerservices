package com.kpics.domain;

import org.jongo.marshall.jackson.oid.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Skill
 */

@Document(collection = "skill")
public class Skill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("stident_id")
    private String studentId;

    @NotNull
    @Field("subject_id")
    private String subjectId;

    @NotNull
    @Field("mark")
    @Size(max = 100)
    private Integer mark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Skill skill = (Skill) o;

        if (id != null ? !id.equals(skill.id) : skill.id != null) return false;
        if (studentId != null ? !studentId.equals(skill.studentId) : skill.studentId != null) return false;
        if (subjectId != null ? !subjectId.equals(skill.subjectId) : skill.subjectId != null) return false;
        return mark != null ? mark.equals(skill.mark) : skill.mark == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (studentId != null ? studentId.hashCode() : 0);
        result = 31 * result + (subjectId != null ? subjectId.hashCode() : 0);
        result = 31 * result + (mark != null ? mark.hashCode() : 0);
        return result;
    }
}
