package com.yuzee.app.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import org.springframework.data.mongodb.core.mapping.Document;

//@Entity
//@ToString
//@EqualsAndHashCode
//@Table(name = "institute_type", uniqueConstraints = @UniqueConstraint(columnNames = { "name", "country_name" },
//		name = "UK_ITYPE_NA_CN_CN"), indexes = {@Index(name = "IDX_INSTITUTE_TYPE_NAME", columnList = "name", unique = false) })
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class InstituteType {

	private String name;

	private String description;

	private Boolean isActive;

	private String countryName;
}
