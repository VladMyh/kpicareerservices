package com.kpics.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * An Idea.
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
    @Field("startwork_date")
    private LocalDate startWorkDate;

    @NotNull
    @Size(max = 100)
    @Field("company_name")
    private String companyName;

    @Size(max = 200)
    @Field("company_website")
    private String companyWebsite;

    @Field("project_manager_id")
    private String projectManagerId;

    @Field("isIdeaHasPM")
    private boolean isIdeaHasPM;

    @NotNull
    @Field("user_ids")
    private Set<String> userIds = new HashSet<>();

    @Transient
    private Integer countOfParticipateUsers;

    public Idea() {
    }

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

    public boolean isIdeaHasPM() {
        return isIdeaHasPM;
    }

    public void setIdeaHasPM(boolean ideaHasPM) {
        isIdeaHasPM = ideaHasPM;
    }

    public void setProjectManagerId(String projectManagerId) {
        this.projectManagerId = projectManagerId;
    }

    public String getProjectManagerId() {
        return this.projectManagerId;
    }

    public LocalDate getStartWorkDate() {
        return startWorkDate;
    }

    public void setStartWorkDate(LocalDate startWorkDate) {
        this.startWorkDate = startWorkDate;
    }

    public Set<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(Set<String> userIds) {
        this.userIds = userIds;
    }

    public Integer getCountOfParticipateUsers() {
        return this.userIds.size();
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
            ", projectManager='" + projectManagerId + "'" +
            ", isIdeaHasPM='" + isIdeaHasPM + "'" +
            ", startWorkDate='" + startWorkDate + "'" +
            ", userIds='" + userIds + "'" +
            '}';
    }
}
