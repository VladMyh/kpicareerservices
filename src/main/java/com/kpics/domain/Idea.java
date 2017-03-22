package com.kpics.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * An idea of project
 */
@Document(collection = "idea")
public class Idea implements Serializable {

    @Id
    private String id;

    @NotNull
    @Field("description")
    private String description;

    @NotNull
    @Field("create_date")
    private LocalDate createDate;

    @NotNull
    @Field("deadline_date")
    private LocalDate deadlineDate;

    @NotNull
    @Field("company")
    private Company company;

    @NotNull
    @Field("tags")
    private Set<Tag> tags = new HashSet<>();

    public Idea() { }

    public Idea(String id,
                String description,
                LocalDate createDate,
                LocalDate deadlineDate,
                Company company,
                Set<Tag> tags) {
        this.id = id;
        this.description = description;
        this.createDate = createDate;
        this.deadlineDate = deadlineDate;
        this.company = company;
        this.tags = tags;
    }


    //region Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(LocalDate deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public Company getCompanyName() {
        return company;
    }

    public void setCompanyName(Company company) {
        this.company = company;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
    //endregion

    //region Override methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Idea idea = (Idea) o;

        return (id != null ? id.equals(idea.id) : idea.id == null)
            && (createDate != null ? createDate.equals(idea.createDate) : idea.createDate == null)
            && (deadlineDate != null ? deadlineDate.equals(idea.deadlineDate) : idea.deadlineDate == null)
            && (description != null ? description.equals(idea.description) : idea.description == null)
            && (company != null ? company.equals(idea.company) : idea.company == null);

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Idea{" +
            "id='" + id + '\'' +
            ", description= '" + description + '\'' +
            ", createDate= " + createDate +
            ", deadline Date= " + deadlineDate +
            ", company = " + company.toString() +
            '}';
    }
    //endregion
}
