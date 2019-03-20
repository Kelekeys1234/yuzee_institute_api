package com.seeka.freshfuture.app.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="institute_images")
public class InstituteImages extends RecordModifier implements Serializable {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id; 
	
	@Column(name="institute_id")
	private Integer instituteId; // Institute Id
	
	@Column(name="image_index")
	private Integer imageIndex; // Image Index
	
	@Column(name="image_name")
	private String imageName; // Image Name
	
	@Column(name="image_path")
	private String imagePath; // Image Path
	
	@Column(name="description")
	private String description; // Description
	
	@Column(name="is_active")
	private Boolean isActive; // Is Active

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getInstituteId() {
		return instituteId;
	}

	public void setInstituteId(Integer instituteId) {
		this.instituteId = instituteId;
	}

	public Integer getImageIndex() {
		return imageIndex;
	}

	public void setImageIndex(Integer imageIndex) {
		this.imageIndex = imageIndex;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
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

	
				
	
	
}
