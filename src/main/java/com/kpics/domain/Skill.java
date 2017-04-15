package com.kpics.domain;

import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * Skill
 */

public class Skill implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Field("teacher_id")
    private String teacherId;

    @NotNull
    @Field("name")
    private String name;

    @NotNull
    @Field("track_name")
    private String trackName;

    @NotNull
    @Field("academicYear")
    @Pattern(regexp="[12]\\d{3}-[12]\\d{3}")
    private String academicYear;

    @NotNull
    @Field("semester")
    private int semester;

    @NotNull
    @Field("mark")
    //@Size(min = 60, max = 100)
    private Integer mark;

    @NotNull
    @Field("review")
    private String review;

    public Skill(){}

    public Skill(String teacherId, String name, String trackName, String academicYear, int semester, Integer mark, String review) {
        this.teacherId = teacherId;
        this.name = name;
        this.trackName = trackName;
        this.academicYear = academicYear;
        this.semester = semester;
        this.mark = mark;
        this.review = review;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
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

        if (semester != skill.semester) return false;
        if (teacherId != null ? !teacherId.equals(skill.teacherId) : skill.teacherId != null) return false;
        if (name != null ? !name.equals(skill.name) : skill.name != null) return false;
        if (trackName != null ? !trackName.equals(skill.trackName) : skill.trackName != null) return false;
        if (academicYear != null ? !academicYear.equals(skill.academicYear) : skill.academicYear != null) return false;
        if (mark != null ? !mark.equals(skill.mark) : skill.mark != null) return false;
        return review != null ? review.equals(skill.review) : skill.review == null;
    }

    @Override
    public int hashCode() {
        int result = teacherId != null ? teacherId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (trackName != null ? trackName.hashCode() : 0);
        result = 31 * result + (academicYear != null ? academicYear.hashCode() : 0);
        result = 31 * result + semester;
        result = 31 * result + (mark != null ? mark.hashCode() : 0);
        result = 31 * result + (review != null ? review.hashCode() : 0);
        return result;
    }
}
