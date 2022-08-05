package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

//@Entity
//@Table(name = "course_funding", uniqueConstraints = @UniqueConstraint(columnNames = { "course_id",
//		"funding_name_id" }, name = "UK_COURSE_ID_FUNDING_ID"), indexes = {
//				@Index(name = "IDX_FUNDING_COURSE_ID", columnList = "course_id", unique = false) })
@Data
@NoArgsConstructor
@ToString(exclude = "course")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "course_funding")
public class CourseFunding implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5639931588147850985L;

	@Id

	private String id;

	private Date createdOn;

	private Date updatedOn;

	private String createdBy;

	private String updatedBy;

	@DBRef(lazy = true)
	private Course course;

	@EqualsAndHashCode.Include

	private String fundingNameId;

	public void setAuditFields(String userId) {
		this.setUpdatedBy(userId);
		this.setUpdatedOn(new Date());
		if (StringUtils.isEmpty(id)) {
			this.setCreatedBy(userId);
			this.setCreatedOn(new Date());
		}
	}
}
