package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
@Table(name = "off_campus_course", indexes = { @Index(name = "IDX_COURSE", columnList = "course_id", unique = true) })
public class OffCampusCourse implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;

	@Column(name = "latitude")
	private Double latitude;

	@Column(name = "longitude")
	private Double longitude;

	@Column(name = "admin_fee")
	private Double adminFee;

	@Column(name = "material_fee")
	private Double materialFee;

	@Column(name = "address", columnDefinition = "TEXT")
	private String address;

	@Column(name = "country_name", length = 50)
	private String countryName;

	@Column(name = "city_name", length = 100)
	private String cityName;

	@Column(name = "state_name")
	private String stateName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on")
	private Date createdOn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_on")
	private Date updatedOn;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_by")
	private String updatedBy;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "course_id")
	private Course course;
}