package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
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
@ToString(exclude = { "sourceCourse", "sourceInstitute", "destinationCourse", "destinationInstitute" })
@EqualsAndHashCode
@Document(collection = "course_institute")
public class CourseInstitute implements Serializable {
//, uniqueConstraints = @UniqueConstraint(columnNames = { "source_course_id",
//			"source_institute_id", "destination_course_id",
//			"destination_institute_id" }, name = "UK_COURSE_SC_SI_DC_DI"), indexes = {
//		@Index(name = "IDX_SRC_COURSE_ID", columnList = "source_course_id", unique = false),
//		@Index(name = "IDX_DES_COURSE_ID", columnList = "source_course_id", unique = false) }
	/**
	 * 
	 */
	private static final long serialVersionUID = 5639931588147850985L;

	@Id

	private String id;
	@DBRef(lazy = true)
	private Course sourceCourse;

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "source_institute_id", nullable = false)
//	private Institute sourceInstitute;

	@DBRef(lazy = true)
	private Course destinationCourse;

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "destination_institute_id", nullable = false)
//	private Institute destinationInstitute;

	private Date createdOn;

	private Date updatedOn;

	@Column(name = "created_by", length = 50)
	private String createdBy;

	@Column(name = "updated_by", length = 50)
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
