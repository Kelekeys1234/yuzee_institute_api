package com.yuzee.app.bean;

import java.util.Date;
import org.springframework.data.mongodb.core.mapping.Document;

import com.yuzee.app.constant.RecommendRequestStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document("institute_recommend_request")
@Data
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class InstituteRecommendRequest {
	
	@org.springframework.data.annotation.Id
	private String id;
	
	private String userId;
	
	private String instituteName;
	
	private Boolean isWorking;
	
	private Boolean knowingSomeoneWorking;
	
	private String name;
	
	private String title;
	
	private String email;
	
	private String phoneNumber;
	
	private String addressInstitute;
	
	private String websiteLink;
	
	private RecommendRequestStatus recommendRequestStatus;
		
	private Date createdOn;
	
	private Date updatedOn;

	private String createdBy;
	
	private String updatedBy;

	public InstituteRecommendRequest(String userId, String instituteName, Boolean isWorking,
			Boolean knowingSomeoneWorking, String name, String title, String email, String phoneNumber,
			String addressInstitute, String websiteLink, RecommendRequestStatus recommendRequestStatus, Date createdOn,
			Date updatedOn, String createdBy, String updatedBy) {
		super();
		this.userId = userId;
		this.instituteName = instituteName;
		this.isWorking = isWorking;
		this.knowingSomeoneWorking = knowingSomeoneWorking;
		this.name = name;
		this.title = title;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.addressInstitute = addressInstitute;
		this.websiteLink = websiteLink;
		this.recommendRequestStatus = recommendRequestStatus;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
	}
}
