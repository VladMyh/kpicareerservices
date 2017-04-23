package com.kpics.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Stream.
 */

@Document(collection = "stream")
public class Stream implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("name")
    private String name;

    @NotNull
    @Field("start_date")
    private LocalDate startDate;

    @NotNull
    @Field("end_date")
    private LocalDate endDate;

    @Field("description")
    private String description;

    @Field("tracks")
    private Set<Track> tracks = new HashSet<>();

    @Field("groups")
    private Set<String> groups = new HashSet<>();

    public String getId() {
        return id;
    }

    public Stream id(String id) {
        this.id = id;
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Stream name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Stream startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Stream endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Stream description(String description) {
        this.description = description;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Set<Track> getTracks() {
        return tracks;
    }

    public Stream tracks(Set<Track> tracks) {
        this.tracks = tracks;
        return this;
    }

    public void setTracks(Set<Track> tracks) {
        this.tracks = tracks;
    }

    public void setGroups(Set<String> groups) {
        this.groups = groups;
    }

    public Set<String> getGroups() {
        return groups;
    }

    public Stream groups(Set<String> groups) {
        this.groups = groups;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stream stream = (Stream) o;

        if (!id.equals(stream.id)) return false;
        if (!name.equals(stream.name)) return false;
        if (!startDate.equals(stream.startDate)) return false;
        if (!endDate.equals(stream.endDate)) return false;
        if (description != null ? !description.equals(stream.description) : stream.description != null) return false;
        if (tracks != null ? !tracks.equals(stream.tracks) : stream.tracks != null) return false;
        return groups != null ? groups.equals(stream.groups) : stream.groups == null;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Stream{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", startDate=" + startDate +
            ", endDate=" + endDate +
            ", description='" + description + '\'' +
            ", tracks=" + tracks +
            ", groups=" + groups +
            '}';
    }
}
