package com.seeka.app.bean;

import java.util.Date;

import javax.persistence.Column;
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
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "institute_additional_info",  uniqueConstraints = @UniqueConstraint(columnNames = { "institute_id" } , name = "UK_INSTITUTE_ID"),
		indexes = { @Index (name = "IDX_INSTITUTE_ID", columnList="institute_id", unique = true)})
@Data
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class InstituteAdditionalInfo {
	
	@Id
	@GenericGenerator(name = "GUID" , strategy = "org.hibernate.id.GUIDGenerator")
	@GeneratedValue(generator = "GUID")
	@Column(name = "id", unique = true, nullable = false)
	private String id;
	
	@Column(name = "institute_id", nullable = false)
	private String instituteId;
	
	@Column(name ="student_number", nullable = false)
	private int numberOfStudent;
	
	@Column(name ="employee_number", nullable = false)
	private int numberOfEmployee;
	
	@Column(name ="teacher_number", nullable = false)
	private int numberOfTeacher;
	
	@Column(name ="class_number", nullable = false)
	private int numberOfClassRoom;
	
	@Column(name ="campus_size", nullable = false)
	private String sizeOfCampus;
	
	@Column(name ="lecture_hall_number", nullable = false)
	private int numberOfLectureHall;
	
	@Column(name ="faculty_number", nullable = false)
	private int numberOfFaculty;
	
	@Column(name ="employment_rate", nullable = false)
	private String rateOfEmployment;
	
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

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn( name = "institute_id", insertable = false, updatable = false )
	private Institute institute;

	public InstituteAdditionalInfo(String instituteId, int numberOfStudent, int numberOfEmployee, int numberOfTeacher,
			int numberOfClassRoom, String sizeOfCampus, int numberOfLectureHall, int numberOfFaculty,
			String rateOfEmployment, Date createdOn, Date updatedOn, String createdBy, String updatedBy) {
		super();
		this.instituteId = instituteId;
		this.numberOfStudent = numberOfStudent;
		this.numberOfEmployee = numberOfEmployee;
		this.numberOfTeacher = numberOfTeacher;
		this.numberOfClassRoom = numberOfClassRoom;
		this.sizeOfCampus = sizeOfCampus;
		this.numberOfLectureHall = numberOfLectureHall;
		this.numberOfFaculty = numberOfFaculty;
		this.rateOfEmployment = rateOfEmployment;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
	}
}
