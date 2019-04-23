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
@Table(name="institute_type")
public class InstituteType extends RecordModifier implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
	@Type(type = "uuid-char")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;
	
	@Column(name="name")
	private String name; // Name
	
	@Column(name="type")
	private String type; //Institute Type
	
	@Column(name="description")
	private String description; //Type description
	
	@Column(name="is_active")
	private	Boolean isActive; // Is Active

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
		InstituteType instituteTypeObj = new InstituteType();
		instituteTypeObj.setDescription("University");
		instituteTypeObj.setName("University");
		instituteTypeObj.setIsActive(true);
		instituteTypeObj.setType("Type of institute");
		instituteTypeObj.setCreatedBy("Name of the record Creator");
		instituteTypeObj.setUpdatedBy("Name of the record Creator");
		instituteTypeObj.setCreatedOn(new Date());
		instituteTypeObj.setUpdatedOn(new Date());
		instituteTypeObj.setDeletedOn(new Date());
		instituteTypeObj.setIsDeleted(false);
		
		Gson gson = new Gson();
		System.out.println(gson.toJson(instituteTypeObj));
		
		System.out.println(new Date().getTime());
	}
	
	
	
}
