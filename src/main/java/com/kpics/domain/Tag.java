package com.kpics.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Tags for projects, ideas and other entities
 * Such as (hashtags)
 */
@Document(collection = "tags")
public class Tag implements Serializable {

    @Id
    private String id;

    @NotNull
    @Field("name")
    private String name;

}
