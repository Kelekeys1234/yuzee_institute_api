package com.yuzee.app.bean;

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
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@ToString
@EqualsAndHashCode
@Table(name = "scholarship_eligible_nationality", uniqueConstraints = @UniqueConstraint(columnNames = { "country_name",
		"scholarship_id" }, name = "UK_CN_LE_IN_CN"), indexes = {
				@Index(name = "IDX_SCHOLARSHIP_ID", columnList = "scholarship_id", unique = false) })
public class ScholarshipEligibleNationality {

	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", columnDefinition = "uniqueidentifier")
	private String id;

	@Column(name = "country_name")
	private String countryName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "scholarship_id")
	private Scholarship scholarship;

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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "deleted_on", length = 19)
	private Date deletedOn;
}
