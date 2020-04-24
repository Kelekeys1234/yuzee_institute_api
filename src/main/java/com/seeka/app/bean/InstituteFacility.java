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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "institute_facilities",  uniqueConstraints = @UniqueConstraint(columnNames = { "institute_id", "facility_id" } , name = "UK_INSTITUTE_ID_FACILITY_ID"),
          indexes = { @Index (name = "IDX_FACILITY_INSTITUTE_ID", columnList="institute_id", unique = false)})
@Data
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class InstituteFacility {

	@Id
	@GenericGenerator(name = "GUID" , strategy = "org.hibernate.id.GUIDGenerator")
	@GeneratedValue(generator = "GUID")
	@Column(name = "id", unique = true, nullable = false)
	private String id;
	
	@Column(name = "institute_id", nullable = false)
	private String instituteId;
	
	@Column(name ="facility_id", nullable = false)
	private String facilityId;
	
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "institute_id", nullable = false, updatable = false, insertable =  false)
	private Institute institute;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "service_id", nullable = false,updatable = false, insertable =  false)
	private Service service;

	public InstituteFacility(String instituteId, String facilityId, Date createdOn, Date updatedOn, String createdBy,
			String updatedBy) {
		super();
		this.instituteId = instituteId;
		this.facilityId = facilityId;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
	}
}
