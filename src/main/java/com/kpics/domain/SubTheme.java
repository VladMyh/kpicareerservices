package com.kpics.domain;

import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;

/**
 * SubTheme
 */

public class SubTheme {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    private String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubTheme subTheme = (SubTheme) o;

        if (id != null ? !id.equals(subTheme.id) : subTheme.id != null) return false;
        return name != null ? name.equals(subTheme.name) : subTheme.name == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
