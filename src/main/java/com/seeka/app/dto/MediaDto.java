package com.seeka.app.dto;

import java.io.Serializable;

public class MediaDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private String url;

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

}
