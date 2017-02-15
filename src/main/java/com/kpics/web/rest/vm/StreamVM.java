package com.kpics.web.rest.vm;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class StreamVM {

    private String id;

    @NotNull
    private String name;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    private String description;

    private Set<TrackVM> tracks = new HashSet<>();

    public StreamVM() {}

    public StreamVM(String id, String name, LocalDate startDate, LocalDate endDate, String description, Set<TrackVM> tracks) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.tracks = tracks;
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<TrackVM> getTracks() {
        return tracks;
    }

    public void setTracks(Set<TrackVM> tracks) {
        this.tracks = tracks;
    }

    @Override
    public String toString() {
        return "StreamVM{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", startDate=" + startDate +
            ", endDate=" + endDate +
            ", description='" + description + '\'' +
            ", tracks=" + tracks +
            '}';
    }
}
