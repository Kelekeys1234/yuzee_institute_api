package com.yuzee.app.bean;

import java.io.Serializable;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data

@Document("help_category")
public class HelpCategory implements Serializable {

	private static final long serialVersionUID = 6922844940897956622L;

	@org.springframework.data.annotation.Id
	private String id;

	private String name;

	private Date createdOn;

	private Date updatedOn;

	private Date deletedOn;

	private String createdBy;

	private String updatedBy;

	private Boolean isActive;
}
