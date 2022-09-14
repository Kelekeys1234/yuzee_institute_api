package com.yuzee.app.bean;

import java.io.Serializable;


// Generated 7 Jun, 2019 2:45:49 PM by Hibernate Tools 4.3.1

import java.util.Date;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document("faculty")
@NoArgsConstructor
@AllArgsConstructor
/*@Table(name = "faculty", uniqueConstraints = @UniqueConstraint(columnNames = { "name" }, name = "UK_FACULY_NA"), 
	indexes = {@Index(name = "IDX_FACULTY_NAME", columnList = "name", unique = true) }) */
public class Faculty implements Serializable {

	private static final long serialVersionUID = -5502957778916515394L;

	@Id
	private String id;

	private String name;

	private String description;

	private Boolean isActive;

	private Date createdOn;

	private Date updatedOn;

	private Date deletedOn;

	private String createdBy;

	private String updatedBy;

	private Boolean isDeleted;
}
