package com.seeka.app.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.google.gson.Gson;

@Entity
@Table(name="service")
public class ServiceDetails extends RecordModifier implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
	@Type(type = "uuid-char")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;
	
	@Column(name="institute_type_id")
	@Type(type = "uuid-char")
	private UUID instituteTypeId; //Institute type Id
	
	@Column(name="name")
	private String name; //Name
	
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

	public UUID getInstituteTypeId() {
		return instituteTypeId;
	}

	public void setInstituteTypeId(UUID instituteTypeId) {
		this.instituteTypeId = instituteTypeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		
		ServiceDetails obj = new ServiceDetails();
	    obj.setInstituteTypeId(UUID.randomUUID());
		obj.setName("service");
		obj.setDescription("description");
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
