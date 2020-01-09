package com.seeka.app.dto;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "global_student_data")
public class GlobalDataDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1464188653045286660L;
	/**
	 * This DTO contains total Number of students migrated in a particular country. Please dont change this DTO
	 * if you want to reuse this make another DTO.
	 * -mihir
	 */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private BigInteger id;
	@Column(name = "source_country")
	private String sourceCountry;
	@Column(name = "total_student")
	private Double totalNumberOfStudent;
	@Column(name = "destination_country")
	private String destinationCountry;
	
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
	
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public String getSourceCountry() {
		return sourceCountry;
	}
	public void setSourceCountry(String sourceCountry) {
		this.sourceCountry = sourceCountry;
	}
	public Double getTotalNumberOfStudent() {
		return totalNumberOfStudent;
	}
	public void setTotalNumberOfStudent(Double totalNumberOfStudent) {
		this.totalNumberOfStudent = totalNumberOfStudent;
	}
	public String getDestinationCountry() {
		return destinationCountry;
	}
	public void setDestinationCountry(String destinationCountry) {
		this.destinationCountry = destinationCountry;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GlobalDataDto [id=").append(id).append(", sourceCountry=").append(sourceCountry)
				.append(", totalNumberOfStudent=").append(totalNumberOfStudent).append(", destinationCountry=")
				.append(destinationCountry).append("]");
		return builder.toString();
	}
	
}
