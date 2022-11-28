package com.yuzee.app.bean;

import java.io.Serializable;

import java.util.Date;
import org.springframework.data.mongodb.core.mapping.Document;

import com.yuzee.app.constant.FaqEntityType;

import lombok.Data;

@Document("faq")
@Data
public class Faq implements Serializable {

	private static final long serialVersionUID = 5744814923342867841L;

	@org.springframework.data.annotation.Id
	private String id;

	private String entityId;

	private FaqEntityType entityType;

	private String title;

	private String description;

	private Date createdOn;

	private Date updatedOn;

	private String createdBy;

	private String updatedBy;

	private FaqSubCategory faqSubCategory;
}