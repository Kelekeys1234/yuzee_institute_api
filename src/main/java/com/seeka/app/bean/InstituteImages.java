package com.seeka.app.bean;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
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
	@Column(name="id")
	private UUID id; 
	
	@Column(name="institute_id")
	private UUID instituteId; // Institute Id
	
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

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getInstituteId() {
		return instituteId;
	}

	public void setInstituteId(UUID instituteId) {
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
