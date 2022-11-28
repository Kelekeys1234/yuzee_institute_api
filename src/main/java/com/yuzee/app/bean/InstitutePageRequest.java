package com.yuzee.app.bean;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import com.yuzee.app.constant.PageRequestStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document("institute_page_request")
@Data
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class InstitutePageRequest {
	
	@org.springframework.data.annotation.Id
	private String id;

	private String instituteId;
	
	private String userId;
	
	private String firstName;
	
	private String lastName;
	
	private String title;
	
	private String email;

	private String phoneNumber;

	private String managementName;
	
	private String managementEmail;
	
	private String managementPhoneNumber;
	
	private PageRequestStatus pageRequestStatus;
	
	private Date createdOn;

	private Date updatedOn;
	
	private String updatedBy;

	public InstitutePageRequest(String instituteId, String userId, String firstName, String lastName, String title,
			String email, String phoneNumber, String managementName, String managementEmail,
			String managementPhoneNumber, PageRequestStatus pageRequestStatus, Date createdOn, Date updatedOn,
			String createdBy, String updatedBy) {
		super();
		this.instituteId = instituteId;
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.title = title;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.managementName = managementName;
		this.managementEmail = managementEmail;
		this.managementPhoneNumber = managementPhoneNumber;
		this.pageRequestStatus = pageRequestStatus;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.updatedBy = updatedBy;
	}
}
