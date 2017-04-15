package com.kpics.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A TrackDefinition.
 */

@Document(collection = "track_definition")
public class TrackDefinition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("name")
    private String name;

    @Field("skill_definitions")
    private Set<String> skillDefinitions = new HashSet<>();


    public TrackDefinition(String name, Set<String> skillDefinitions) {
        this.name = name;
        this.skillDefinitions = skillDefinitions;
    }


    public TrackDefinition() { }

    public TrackDefinition name(String name){
        this.name = name;
        return this;
    }

    public TrackDefinition skillDefinitions(Set<String> skillDefinitions){
        this.skillDefinitions = skillDefinitions;
        return this;
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

    public Set<String> getSkillDefinitions() {
        return skillDefinitions;
    }

    public void setSkillDefinitions(Set<String> skillDefinitions) {
        this.skillDefinitions = skillDefinitions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrackDefinition that = (TrackDefinition) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return skillDefinitions != null ? skillDefinitions.equals(that.skillDefinitions) : that.skillDefinitions == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (skillDefinitions != null ? skillDefinitions.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TrackDefinition{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", skillDefinitions=" + skillDefinitions +
            '}';
    }
}
