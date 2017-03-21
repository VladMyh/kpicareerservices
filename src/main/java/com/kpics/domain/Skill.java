package com.kpics.domain;

import org.jongo.marshall.jackson.oid.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Skill
 */

public class Skill implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Field("subject_id")
    private String subjectId;

    @NotNull
    @Field("teacher_id")
    private String teachderId;

    @NotNull
    @Field("mark")
    @Size(max = 100)
    private Integer mark;

    @NotNull
    @Field("date")
    private LocalDate date;

    @NotNull
    @Field("review")
    private String review;

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getTeachderId() {
        return teachderId;
    }

    public void setTeachderId(String teachderId) {
        this.teachderId = teachderId;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Skill skill = (Skill) o;

        if (subjectId != null ? !subjectId.equals(skill.subjectId) : skill.subjectId != null) return false;
        if (teachderId != null ? !teachderId.equals(skill.teachderId) : skill.teachderId != null) return false;
        if (mark != null ? !mark.equals(skill.mark) : skill.mark != null) return false;
        if (date != null ? !date.equals(skill.date) : skill.date != null) return false;
        return review != null ? review.equals(skill.review) : skill.review == null;
    }

    @Override
    public int hashCode() {
        int result = subjectId != null ? subjectId.hashCode() : 0;
        result = 31 * result + (teachderId != null ? teachderId.hashCode() : 0);
        result = 31 * result + (mark != null ? mark.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (review != null ? review.hashCode() : 0);
        return result;
    }
}
