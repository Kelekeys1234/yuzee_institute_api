package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@ToString(exclude = "course")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "course_work_placement_requirement", indexes = {
		@Index(name = "IDX_COURSE_ID", columnList = "course_id", unique = false) })
public class CourseWorkPlacementRequirement implements Serializable {

	private static final long serialVersionUID = 8492390790670110780L;

	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;

	@EqualsAndHashCode.Include
	@Column(name = "description")
	private String description;

	@OneToOne
	@JoinColumn(name = "course_id")
	private Course course;

	@JoinColumn(name = "duration_type")
	private String durationType;

	@JoinColumn(name = "duration")
	private Double duration;

	
	@EqualsAndHashCode.Include
	@ElementCollection(fetch = FetchType.EAGER)
	@Cascade(CascadeType.ALL)
	private List<String> fields;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on", length = 19)
	private Date createdOn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_on", length = 19)
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
