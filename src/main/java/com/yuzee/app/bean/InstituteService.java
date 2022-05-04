package com.yuzee.app.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "institute_service")
@JsonIgnoreProperties(ignoreUnknown = true)
public class InstituteService{

	@Id
	private UUID instituteServiceId;

	@DBRef(lazy = true)
	@Indexed(unique = true)
	private Service service;

	private String description;

	private String instituteId;

	public InstituteService(UUID instituteServiceId, Service service, String description) {
		this.instituteServiceId = instituteServiceId;
		this.service = service;
		this.description = description;
	}
}
