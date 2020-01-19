package com.seeka.app.dto;

import java.io.Serializable;

public class EventBriteDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2646097318154126789L;
	private String resourceUri;
	private Integer id;
	private String name;
	private String name_localized;
	private String short_name;
	private String short_name_localized;
	public String getResourceUri() {
		return resourceUri;
	}
	public void setResourceUri(String resourceUri) {
		this.resourceUri = resourceUri;
	}
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
	public String getName_localized() {
		return name_localized;
	}
	public void setName_localized(String name_localized) {
		this.name_localized = name_localized;
	}
	public String getShort_name() {
		return short_name;
	}
	public void setShort_name(String short_name) {
		this.short_name = short_name;
	}
	public String getShort_name_localized() {
		return short_name_localized;
	}
	public void setShort_name_localized(String short_name_localized) {
		this.short_name_localized = short_name_localized;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EventBriteDto [resourceUri=").append(resourceUri).append(", id=").append(id).append(", name=")
				.append(name).append(", name_localized=").append(name_localized).append(", short_name=")
				.append(short_name).append(", short_name_localized=").append(short_name_localized).append("]");
		return builder.toString();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((name_localized == null) ? 0 : name_localized.hashCode());
		result = prime * result + ((resourceUri == null) ? 0 : resourceUri.hashCode());
		result = prime * result + ((short_name == null) ? 0 : short_name.hashCode());
		result = prime * result + ((short_name_localized == null) ? 0 : short_name_localized.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventBriteDto other = (EventBriteDto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (name_localized == null) {
			if (other.name_localized != null)
				return false;
		} else if (!name_localized.equals(other.name_localized))
			return false;
		if (resourceUri == null) {
			if (other.resourceUri != null)
				return false;
		} else if (!resourceUri.equals(other.resourceUri))
			return false;
		if (short_name == null) {
			if (other.short_name != null)
				return false;
		} else if (!short_name.equals(other.short_name))
			return false;
		if (short_name_localized == null) {
			if (other.short_name_localized != null)
				return false;
		} else if (!short_name_localized.equals(other.short_name_localized))
			return false;
		return true;
	}
	
}
