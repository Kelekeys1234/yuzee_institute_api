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
@Table(name="faculty_level")
public class FacultyLevel extends RecordModifier implements Serializable{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;	
	
	@ManyToOne
	@JoinColumn(name="institute_level_id")
	private Institute instituteObj; // Institute LevelId
	
	@Column(name="faculty_id")
	private Integer facultyId; // FacultyId
			
	@Column(name="is_active")
	private	Boolean isActive; // Is Active

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
 

	public Institute getInstituteObj() {
		return instituteObj;
	}

	public void setInstituteObj(Institute instituteObj) {
		this.instituteObj = instituteObj;
	}

	public Integer getFacultyId() {
		return facultyId;
	}

	public void setFacultyId(Integer facultyId) {
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
	    levlObj.setId(3);
	    obj.setFacultyId(1);
	    
	    Institute instituteObj = new Institute();
	    instituteObj.setId(1);
	    
	    
	    obj.setInstituteObj(instituteObj);
	    obj.setIsActive(true);
	    
	     				
		Gson gson = new Gson();
		
		String value = gson.toJson(obj);
		
		 System.out.println(value);
		  
	}
	
	
	
	
}
