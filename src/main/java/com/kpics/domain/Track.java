package com.kpics.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

/**
 * A Track.
 */

@Document(collection = "track")
public class Track implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("name")
    private String name;

    @NotNull
    @Field("description")
    private String description;

    @NotNull
    @Field("start_date")
    private LocalDate startDate;

    @NotNull
    @Field("end_date")
    private LocalDate endDate;

    @NotNull
    @Field("is_active")
    private Boolean isActive;

    @NotNull
    @Field("teacher_ids")
    private Set<String> teacherIds;

    @NotNull
    @Field("student_ids")
    private Set<String> studentIds;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Track name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Track description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Track startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Track endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Boolean isActive() {
        return isActive;
    }

    public Track isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Track teacherIds(Set<String> teacherIds) {
        this.teacherIds = teacherIds;
        return this;
    }

    public void setTeacherIds(Set<String> teacherIds) {
        this.teacherIds = teacherIds;
    }

    public Set<String> getTeacherIds() {
        return teacherIds;
    }

    public Track studentIds(Set<String> studentIds) {
        this.studentIds = studentIds;
        return this;
    }

    public void setStudentIds(Set<String> studentIds) {
        this.studentIds = studentIds;
    }

    public Set<String> getStudentIds() {
        return studentIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Track track = (Track) o;

        if (id != null ? !id.equals(track.id) : track.id != null) return false;
        if (name != null ? !name.equals(track.name) : track.name != null) return false;
        if (description != null ? !description.equals(track.description) : track.description != null) return false;
        if (startDate != null ? !startDate.equals(track.startDate) : track.startDate != null) return false;
        if (endDate != null ? !endDate.equals(track.endDate) : track.endDate != null) return false;
        if (isActive != null ? !isActive.equals(track.isActive) : track.isActive != null) return false;
        if (teacherIds != null ? !teacherIds.equals(track.teacherIds) : track.teacherIds != null) return false;
        return studentIds != null ? studentIds.equals(track.studentIds) : track.studentIds == null;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Track{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", startDate=" + startDate +
            ", endDate=" + endDate +
            ", isActive=" + isActive +
            ", teacherIds=" + teacherIds +
            ", studentIds=" + studentIds +
            '}';
    }
}
