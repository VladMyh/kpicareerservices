package com.kpics.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
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

    @Field("description")
    private String description;

    @NotNull
    @Field("teacher_ids")
    private Set<String> teacherIds = new HashSet<>();

    @NotNull
    @Field("subjects")
    private Set<Subject> subjects = new HashSet<>();

    public Track() {}

    public Track(String id, String name, String description, Set<String> teacherIds, Set<Subject> subjects) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.teacherIds = teacherIds;
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

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }

    public Track subjects(Set<Subject> subjects) {
        this.subjects = subjects;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Track track = (Track) o;

        if (id != null ? !id.equals(track.id) : track.id != null) return false;
        if (name != null ? !name.equals(track.name) : track.name != null) return false;
        if (description != null ? !description.equals(track.description) : track.description != null) return false;
        if (teacherIds != null ? !teacherIds.equals(track.teacherIds) : track.teacherIds != null) return false;
        return subjects != null ? subjects.equals(track.subjects) : track.subjects == null;
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
            ", teacherIds=" + teacherIds +
            ", subjects=" + subjects +
            '}';
    }
}
