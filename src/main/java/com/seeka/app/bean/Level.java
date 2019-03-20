package com.seeka.app.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.Gson;

@Entity
@Table(name="level")
public class Level extends RecordModifier implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="name")
	private String name; //type text
	
	@Column(name="level_key")
	private String levelKey; //type description
		
	@Column(name="description")
	private String description; // Description
	 
	@Column(name="is_active")
	private	Boolean isActive; // Is Active

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLevelKey() {
		return levelKey;
	}

	public void setLevelKey(String levelKey) {
		this.levelKey = levelKey;
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
        System.out.println("Hello World!");
        Level obj = new Level();
        
        
		obj.setName("UG");
		obj.setIsActive(true);
		obj.setLevelKey("levelKey");
		obj.setCreatedBy("Own");
		obj.setDescription("Nothing");		
		obj.setUpdatedBy("Own");		
		obj.setCreatedOn(new Date());
		obj.setUpdatedOn(new Date());
		obj.setDeletedOn(new Date());
		obj.setIsDeleted(true);					
		Gson gson = new Gson();
		
		String value = gson.toJson(obj);
		
		System.out.println(value);
    }
    
	
}
