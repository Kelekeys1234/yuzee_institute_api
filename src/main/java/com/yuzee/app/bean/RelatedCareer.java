package com.yuzee.app.bean;

import java.io.Serializable;

import java.util.Date;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(exclude = "careers")
@EqualsAndHashCode
@NoArgsConstructor
@Document(collection="relatedCareer")
public class RelatedCareer implements Serializable {

	private static final long serialVersionUID = 1L;

	@org.springframework.data.annotation.Id
	private String id;
	
	private String relatedCareer;

	@DBRef(lazy = true)
	private Careers careers;

	private Date createdOn;

	private Date updatedOn;

	private String createdBy;

	private String updatedBy;

public RelatedCareer(String id, String relatedCareer, Careers careers, Date createdOn, String createdBy) {
	super();
	this.id = id;
	this.relatedCareer = relatedCareer;
	this.careers = careers;
	this.createdOn = createdOn;
	this.createdBy = createdBy;
}



}
