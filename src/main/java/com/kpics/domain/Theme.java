package com.kpics.domain;

import org.jongo.marshall.jackson.oid.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

/**
 * Theme
 */

@Document(collection = "theme")
public class Theme implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("name")
    private String name;

    @Field("sub_themes")
    private Set<SubTheme> subThemes;


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

    public Set<SubTheme> getSubThemes() {
        return subThemes;
    }

    public void setSubThemes(Set<SubTheme> subThemes) {
        this.subThemes = subThemes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Theme theme = (Theme) o;

        if (id != null ? !id.equals(theme.id) : theme.id != null) return false;
        if (name != null ? !name.equals(theme.name) : theme.name != null) return false;
        return subThemes != null ? subThemes.equals(theme.subThemes) : theme.subThemes == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (subThemes != null ? subThemes.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Theme{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", subThemes=" + subThemes +
            '}';
    }
}
