package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@ToString(exclude = "course")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "off_campus_course", indexes = { @Index(name = "IDX_COURSE", columnList = "course_id", unique = true) })
public class OffCampusCourse implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;

	@EqualsAndHashCode.Include
	@Column(name = "latitude")
	private Double latitude;

	@EqualsAndHashCode.Include
	@Column(name = "longitude")
	private Double longitude;

	@EqualsAndHashCode.Include
	@Column(name = "admin_fee")
	private Double adminFee;

	@EqualsAndHashCode.Include
	@Column(name = "material_fee")
	private Double materialFee;

	@EqualsAndHashCode.Include
	@Column(name = "address", columnDefinition = "TEXT")
	private String address;	

	@EqualsAndHashCode.Include
	@Column(name = "country_name", length = 50)
	private String countryName;

	@EqualsAndHashCode.Include
	@Column(name = "city_name", length = 100)
	private String cityName;

	@EqualsAndHashCode.Include
	@Column(name = "state_name")
	private String stateName;

	@EqualsAndHashCode.Include
	@Column(name = "postal_code")
	private String postalCode;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on")
	private Date createdOn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_on")
	private Date updatedOn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_date")
	private Date startDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_by")
	private String updatedBy;

	@OneToOne
	@JoinColumn(name = "course_id")
	private Course course;

	public void setAuditFields(String userId) {
		this.setUpdatedBy(userId);
		this.setUpdatedOn(new Date());
		if (StringUtils.isEmpty(id)) {
			this.setCreatedBy(userId);
			this.setCreatedOn(new Date());
		}
	}
}