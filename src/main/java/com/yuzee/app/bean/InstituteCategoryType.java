package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
public class InstituteCategoryType implements Serializable {

    @Id
    private String id;

    private String name;

    private Date createdOn;

    private Date updatedOn;

    private Date deletedOn;

    private String createdBy;

    private String updatedBy;
}
//@Entity
//@Table(name = "institute_category_type", uniqueConstraints = @UniqueConstraint(columnNames = { "name" },name = "UK_ICT_NAME"))