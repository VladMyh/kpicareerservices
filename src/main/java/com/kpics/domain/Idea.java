package com.kpics.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Idea.
 */

@Document(collection = "idea")
public class Idea implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(max = 2000)
    @Field("description")
    private String description;

    @NotNull
    @Size(max = 100)
    @Field("name")
    private String name;

    @NotNull
    @Field("create_date")
    private LocalDate createDate;

    @NotNull
    @Field("deadline_date")
    private LocalDate deadlineDate;

    @NotNull
    @Size(max = 100)
    @Field("company_name")
    private String companyName;

    @Size(max = 200)
    @Field("company_website")
    private String companyWebsite;

    @Field("project_manager")
    private User projectManager;

    @Field("isIdeaHasPM")
    private boolean isIdeaHasPM;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Idea description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public Idea name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public Idea createDate(LocalDate createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getDeadlineDate() {
        return deadlineDate;
    }

    public Idea deadlineDate(LocalDate deadlineDate) {
        this.deadlineDate = deadlineDate;
        return this;
    }

    public void setDeadlineDate(LocalDate deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Idea companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyWebsite() {
        return companyWebsite;
    }

    public Idea companyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite;
        return this;
    }

    public void setCompanyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite;
    }

    public User getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(User projectManager) {
        this.projectManager = projectManager;
    }

    public boolean isIdeaHasPM() {
        return isIdeaHasPM;
        //return projectManager != null;
    }

    public void setIdeaHasPM(boolean ideaHasPM) {
        isIdeaHasPM = ideaHasPM;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Idea idea = (Idea) o;
        if (idea.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, idea.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Idea{" +
            "id=" + id +
            ", description='" + description + "'" +
            ", name='" + name + "'" +
            ", createDate='" + createDate + "'" +
            ", deadlineDate='" + deadlineDate + "'" +
            ", companyName='" + companyName + "'" +
            ", companyWebsite='" + companyWebsite + "'" +
            ", projectManager='" + projectManager + "'" +
            ", isIdeaHasPM='" + isIdeaHasPM + "'" +
            '}';
    }
}
