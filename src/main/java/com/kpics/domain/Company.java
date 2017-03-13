package com.kpics.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Created by acube on 14.03.2017.
 */
@Document(collection = "company")
public class Company {

    @Id
    private String id;

    @NotNull
    @Field("name")
    private String name;

    @NotNull
    @Field("description")
    private String description;

    @NotNull
    @Field("contact_email")
    private String contactEmail;

    @NotNull
    @Field("contact_url")
    private String contactUrl;

    @NotNull
    @Field("contact_user")
    private User contactUser;

    public Company() {}

    public Company(String id,
                   String name,
                   String description,
                   String contactEmail,
                   String contactUrl,
                   User contactUser) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.contactEmail = contactEmail;
        this.contactUrl = contactUrl;
        this.contactUser = contactUser;
    }

    //region Getters and Setters
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public User getContactUser() {
        return contactUser;
    }

    public void setContactUser(User contactUser) {
        this.contactUser = contactUser;
    }

    public String getContactUrl() {
        return contactUrl;
    }

    public void setContactUrl(String contactUrl) {
        this.contactUrl = contactUrl;
    }
    //endregion

    //region Override methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        return (id != null ? id.equals(company.id) : company.id == null)
            && (name != null ? name.equals(company.name) : company.name == null)
            && (contactEmail != null ? contactEmail.equals(company.contactEmail) : company.contactEmail == null)
            && (description != null ? description.equals(company.description) : company.description == null)
            && (contactUrl != null ? contactUrl.equals(company.contactUrl) : company.contactUrl == null)
            && (contactUser != null ? contactUser.equals(company.contactUser) : company.contactUser == null);

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Company{" +
            "id='" + id + '\'' +
            ", name= '" + name + '\'' +
            ", description= '" + description + '\'' +
            ", contact email= " + contactEmail +
            ", contact url= " + contactUrl +
            ", contact person= " + contactUser +
            '}';
    }
    //endregion
}
