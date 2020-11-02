package com.yuzee.app.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThumbnailsDTO implements Serializable {
	private static final long serialVersionUID = 74940601449828958L;
	
	@JsonProperty("height")
	private int height;
	
	@JsonProperty("width")
	private int width;	
	
	@JsonProperty("url")
	private String thumbnailURL;
}
