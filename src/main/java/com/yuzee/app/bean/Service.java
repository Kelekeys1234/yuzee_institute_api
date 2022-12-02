package com.yuzee.app.bean;




// Generated 7 Jun, 2019 2:45:49 PM by Hibernate Tools 4.3.1

import java.util.Date;
import org.springframework.data.annotation.Id;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Document("service")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Service{

	@Id
	private String id;

	@Indexed(unique = true)
	private String name;

	private String description;

	private Date createdOn;

	private Date updatedOn;

	private String createdBy;

	private String updatedBy;
}