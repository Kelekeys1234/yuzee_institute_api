package com.yuzee.app.bean;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data

@ToString(exclude = "course")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor

@Document(collection = "location")
public class Location {
	@Id

	private String id;
	@EqualsAndHashCode.Include
	private GeoJsonPoint location;
	@DBRef
	private Institute institute;

	public Location(String id, GeoJsonPoint location) {
		super();
		this.id = id;
		this.location = location;

	}

}
