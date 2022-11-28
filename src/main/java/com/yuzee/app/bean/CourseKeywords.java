package com.yuzee.app.bean;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data

@ToString
@EqualsAndHashCode
@Document("course_keywords")
public class CourseKeywords {

	@org.springframework.data.annotation.Id
	private String id;

	private String keyword;

	private String description;

	private Date createdOn;

	private String createdBy;

	private Date updatedOn;

	private String updatedBy;
}
