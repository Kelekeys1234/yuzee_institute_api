package com.seeka.app.bean;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.google.gson.Gson;

@Entity
@Table(name="faculty_level")
public class FacultyLevel extends RecordModifier implements Serializable{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
	@Type(type = "uuid-char")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;	
	
	@ManyToOne
	@JoinColumn(name="institute_id")
	private Institute instituteObj; // Institute LevelId
	
	@Type(type = "uuid-char")
	@Column(name="faculty_id")
	private UUID facultyId; // FacultyId
			
	@Column(name="is_active")
	private	Boolean isActive; // Is Active

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

	public UUID getFacultyId() {
		return facultyId;
	}

	public void setFacultyId(UUID facultyId) {
		this.facultyId = facultyId;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
    
	public static void main(String[] args) {
		   
	    FacultyLevel obj = new FacultyLevel();
	    Level levlObj = new Level();
	    levlObj.setId(UUID.randomUUID());
	    obj.setFacultyId(UUID.randomUUID());
	    
	    Institute instituteObj = new Institute();
	    instituteObj.setId(UUID.randomUUID());
	    
	    
	    obj.setInstituteObj(instituteObj);
	    obj.setIsActive(true);
	    
	     				
		Gson gson = new Gson();
		
		String value = gson.toJson(obj);
		
		 System.out.println(value);
		  
	}
	
	
	
	
}
