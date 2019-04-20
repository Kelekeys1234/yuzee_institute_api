package com.seeka.app.bean;

import java.io.Serializable;
import java.util.UUID;

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
@Table(name="faculty_level")
public class FacultyLevel extends RecordModifier implements Serializable{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private UUID id;	
	
	@ManyToOne
	@JoinColumn(name="institute_id")
	private Institute instituteObj; // Institute LevelId
	
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
