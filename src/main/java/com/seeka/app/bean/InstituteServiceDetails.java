package com.seeka.app.bean;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="institute_service")
public class InstituteServiceDetails extends RecordModifier implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@Column(name="id")
	private UUID id;
	
	@ManyToOne
	@JoinColumn(name="service_id")
	private ServiceDetails serviceObj; //ServiceId
	
	@ManyToOne
	@JoinColumn(name="institute_id")
	private Institute instituteObj; //Institute Id
		
	@Column(name="description")
	private String description; //Description
	
	@Column(name="is_active")
	private Boolean isActive; // Is Active

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
	public Institute getInstituteObj() {
		return instituteObj;
	}

	public void setInstituteObj(Institute instituteObj) {
		this.instituteObj = instituteObj;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public ServiceDetails getServiceObj() {
		return serviceObj;
	}

	public void setServiceObj(ServiceDetails serviceObj) {
		this.serviceObj = serviceObj;
	}
 
}
