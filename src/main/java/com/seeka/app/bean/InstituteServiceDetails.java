package com.seeka.app.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.gson.Gson;

@Entity
@Table(name="institute_service")
public class InstituteServiceDetails extends RecordModifier implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="service_id")
	private ServiceDetails serviceId; //ServiceId
	
	@ManyToOne
	@JoinColumn(name="institute_id")
	private Institute instituteId; //Institute Id
		
	@Column(name="description")
	private String description; //Description
	
	@Column(name="is_active")
	private Boolean isActive; // Is Active

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ServiceDetails getServiceId() {
		return serviceId;
	}

	public void setServiceId(ServiceDetails serviceId) {
		this.serviceId = serviceId;
	}

	public Institute getInstituteId() {
		return instituteId;
	}

	public void setInstituteId(Institute instituteId) {
		this.instituteId = instituteId;
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

	public static void main(String[] args) {
		   
		InstituteServiceDetails obj = new InstituteServiceDetails();
		
		ServiceDetails svcObj = new ServiceDetails();
		svcObj.setId(1);
		
		Institute insObj = new Institute();
		insObj.setId(1);
	    
	    obj.setServiceId(svcObj);
	    obj.setInstituteId(insObj);
	    obj.setDescription("Description about the university");
		obj.setIsActive(true);
		obj.setCreatedOn(new Date());
		obj.setUpdatedOn(new Date());
		obj.setDeletedOn(new Date());
		obj.setCreatedBy("Own");
		obj.setUpdatedBy("Own");
		obj.setIsDeleted(false);					
		Gson gson = new Gson();
		
		String value = gson.toJson(obj);
		
		 System.out.println(value);
		 
		System.out.println(new Date().getTime());
	}

	

	
    		 
	
}
