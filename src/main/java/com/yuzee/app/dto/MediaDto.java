package com.yuzee.app.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class MediaDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("image_name")
	private String imageName;
	
	@JsonProperty("image_url")
	private String imageUrl;
	
	public MediaDto(String id, String imageName, String imageUrl) {
		this.id = id;
		this.imageName = imageName;
		this.imageUrl = imageUrl;
	}

}
