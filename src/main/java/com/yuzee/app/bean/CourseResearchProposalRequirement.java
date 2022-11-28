package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@ToString(exclude = "course")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document("courseResearchPurposerRequirement")
public class CourseResearchProposalRequirement implements Serializable {

	private static final long serialVersionUID = 8492390790670110780L;

	@Id
	private String id;

	@EqualsAndHashCode.Include
	private String description;

	@DBRef(lazy = true)
	private Course course;
	
	private Date createdOn;

	private Date updatedOn;

	private String createdBy;

	private String updatedBy;

	public void setAuditFields(String userId) {
		this.setUpdatedBy(userId);
		this.setUpdatedOn(new Date());
		if (StringUtils.isEmpty(id)) {
			this.setCreatedBy(userId);
			this.setCreatedOn(new Date());
		}
	}
}
