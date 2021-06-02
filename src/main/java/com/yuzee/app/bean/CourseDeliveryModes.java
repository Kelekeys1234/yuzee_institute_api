package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@ToString(exclude = "course")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "course_delivery_modes", uniqueConstraints = @UniqueConstraint(columnNames = { "course_id", "study_mode",
		"delivery_type" }, name = "UK_COURSE_ID_STUDY_MODE_DELIVERY_TYPE"), indexes = {
				@Index(name = "IDX_COURSE_ID", columnList = "course_id", unique = false) })
public class CourseDeliveryModes implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length=36)
	private String id;

	@EqualsAndHashCode.Include
	@Column(name = "delivery_type", nullable = false)
	private String deliveryType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id", nullable = false)
	private Course course;

	@EqualsAndHashCode.Include
	@Column(name = "duration", nullable = false)
	private Double duration;

	@EqualsAndHashCode.Include
	@Column(name = "duration_time", nullable = false)
	private String durationTime;

	@EqualsAndHashCode.Include
	@Column(name = "domestic_fee", nullable = false)
	private Double domesticFee;

	@EqualsAndHashCode.Include
	@Column(name = "international_fee")
	private Double internationalFee;

	@EqualsAndHashCode.Include
	@Column(name = "usd_domestic_fee")
	private Double usdDomesticFee;

	@EqualsAndHashCode.Include
	@Column(name = "usd_international_fee")
	private Double usdInternationalFee;

	@EqualsAndHashCode.Include
	@Column(name = "study_mode", nullable = false)
	private String studyMode;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on", length = 19)
	private Date createdOn;

	@Column(name = "created_by", length = 50)
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_on", length = 19)
	private Date updatedOn;

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
