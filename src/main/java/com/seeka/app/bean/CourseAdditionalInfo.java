package com.seeka.app.bean;

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

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
@Table(name = "course_additional_info", uniqueConstraints = @UniqueConstraint(columnNames = { "course_id", "study_mode",
		"delivery_type" }, name = "UK_COURSE_ID_STUDY_MODE_DELIVERY_TYPE"), indexes = {
				@Index(name = "IDX_COURSE_ID", columnList = "course_id", unique = false) })
public class CourseAdditionalInfo {

	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", columnDefinition = "uniqueidentifier", nullable = false)
	private String id;

	@Column(name = "delivery_type", nullable = false)
	private String deliveryType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id")
	private Course course;

	@Column(name = "duration", nullable = false)
	private Integer duration;

	@Column(name = "duration_time", nullable = false)
	private String durationTime;

	@Column(name = "domestic_fee", nullable = false)
	private Double domesticFee;

	@Column(name = "international_fee", nullable = false)
	private Double internationalFee;

	@Column(name = "usd_domestic_fee", nullable = false)
	private Double usdDomesticFee;

	@Column(name = "usd_international_fee", nullable = false)
	private Double usdInternationalFee;

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


}
