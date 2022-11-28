package com.yuzee.app.bean;

import java.io.Serializable;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.yuzee.app.enumeration.CareerTestEntityType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@Document("career_test_result")
public class CareerTestResult implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String userId;

	private String entityId;

	private CareerTestEntityType entityType;

	private Date createdOn;

	private Date updatedOn;

	private String createdBy;

	private String updatedBy;
}
