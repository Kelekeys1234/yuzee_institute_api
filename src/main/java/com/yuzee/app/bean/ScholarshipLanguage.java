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

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "scholarship_language", uniqueConstraints = @UniqueConstraint(columnNames = { "scholarship_id", "name" },
       name = "UK_SCHOLARSHIP_ID_NAME"), indexes = {@Index(name = "IDX_SCHOLARSHIP_ID", columnList = "scholarship_id", unique = false)})
public class ScholarshipLanguage implements Serializable {

	private static final long serialVersionUID = -3516597245947004154L;
	
	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", columnDefinition = "uniqueidentifier")
	private String id;
	
	@Column(name = "name")
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "scholarship_id")
	private Scholarship scholarship;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on")
	private Date createdOn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_on")
	private Date updatedOn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "deleted_on")
	private Date deletedOn;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_by")
	private String updatedBy;
	
	public ScholarshipLanguage(Scholarship scholarship, String name, String createdBy, Date createdOn) {
		this.scholarship = scholarship;
		this.name = name;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
	}
}
