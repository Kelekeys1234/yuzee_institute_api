package com.yuzee.app.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class MediaDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private String url;

}
