package com.seeka.app.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "northeast", "southwest" })
public class Viewport implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -7092845123879194575L;
	@JsonProperty("northeast")
	private Northeast northeast;
	@JsonProperty("southwest")
	private Southwest southwest;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<>();

	@JsonProperty("northeast")
	public Northeast getNortheast() {
		return northeast;
	}

	@JsonProperty("northeast")
	public void setNortheast(final Northeast northeast) {
		this.northeast = northeast;
	}

	@JsonProperty("southwest")
	public Southwest getSouthwest() {
		return southwest;
	}

	@JsonProperty("southwest")
	public void setSouthwest(final Southwest southwest) {
		this.southwest = southwest;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(final String name, final Object value) {
		this.additionalProperties.put(name, value);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("northeast", northeast).append("southwest", southwest).append("additionalProperties", additionalProperties)
				.toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(additionalProperties).append(southwest).append(northeast).toHashCode();
	}

	@Override
	public boolean equals(final Object other) {
		if (other == this) {
			return true;
		}
		if (other instanceof Viewport == false) {
			return false;
		}
		Viewport rhs = (Viewport) other;
		return new EqualsBuilder().append(additionalProperties, rhs.additionalProperties).append(southwest, rhs.southwest).append(northeast, rhs.northeast)
				.isEquals();
	}

}
