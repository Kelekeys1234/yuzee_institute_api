package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
@Document(collection="help")
public class Help implements Serializable {

	private static final long serialVersionUID = 6922844940897956622L;

	@Id
	private String id;
	@DBRef
	private HelpCategory category;

	@DBRef
	private HelpSubCategory subCategory;

	private String title;

	private String description;

	private Date createdOn;
	
	private Date updatedOn;

	private Date deletedOn;

	private String createdBy;

	private String updatedBy;

	private Boolean isActive;

	private Boolean isQuestioning;

	private String userId;

	private String status;

	private String assignedUserId;

	private Boolean isFavourite = false;

	private Boolean isArchive = false;
}
