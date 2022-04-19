package com.yuzee.app.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "institute_service")
@JsonIgnoreProperties(ignoreUnknown = true)
public class InstituteService{

	@DBRef(lazy = true)
	@Indexed(unique = true)
	private Service service;

	private String description;
}
