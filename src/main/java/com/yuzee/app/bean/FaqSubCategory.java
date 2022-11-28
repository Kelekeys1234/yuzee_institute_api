package com.yuzee.app.bean;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
 
@Document("faq_sub_category")
@Data
public class FaqSubCategory implements Serializable {

	private static final long serialVersionUID = 6922844940897956622L;

	@org.springframework.data.annotation.Id
	private String id;

	private String name;

	private Date createdOn;

	private Date updatedOn;

	private String createdBy;

	private String updatedBy;

    @DBRef
	private FaqCategory faqCategory;

    @DBRef
	private List<Faq> faqs = new ArrayList<>();
}
