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

    public String getId() {
        return id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stream stream = (Stream) o;

        if (id != null ? !id.equals(stream.id) : stream.id != null) return false;
        if (name != null ? !name.equals(stream.name) : stream.name != null) return false;
        if (startDate != null ? !startDate.equals(stream.startDate) : stream.startDate != null) return false;
        if (endDate != null ? !endDate.equals(stream.endDate) : stream.endDate != null) return false;
        if (description != null ? !description.equals(stream.description) : stream.description != null) return false;
        return tracks != null ? tracks.equals(stream.tracks) : stream.tracks == null;
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
            '}';
    }
}
