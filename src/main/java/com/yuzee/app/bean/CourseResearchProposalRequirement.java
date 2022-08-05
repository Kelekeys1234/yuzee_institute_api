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
//@Table(name = "course_research_proposal_requirement", indexes = {
//		@Index(name = "IDX_COURSE_ID", columnList = "course_id", unique = false) })
@Document("courseResearchPurposerRequirement")
public class CourseResearchProposalRequirement implements Serializable {

	private static final long serialVersionUID = 8492390790670110780L;

	@Id
//	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
//	@GeneratedValue(generator = "generator")
//	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;

	@EqualsAndHashCode.Include
//	@Column(name = "description")
	private String description;

//	
	@DBRef(lazy = true)
	private Course course;

//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "created_on", length = 19)
	private Date createdOn;

//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "updated_on", length = 19)
	private Date updatedOn;

//	@Column(name = "created_by", length = 50)
	private String createdBy;

//	@Column(name = "updated_by", length = 50)
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
