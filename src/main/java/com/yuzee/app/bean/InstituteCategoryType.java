package com.yuzee.app.bean;

import java.io.Serializable;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class InstituteCategoryType implements Serializable {

    private String name;

    private Date createdOn;

    private Date updatedOn;

    private Date deletedOn;

    private String createdBy;

    private String updatedBy;

    public InstituteCategoryType(String name, Date createdOn) {
        this.name = name;
        this.createdOn = createdOn;
    }
}