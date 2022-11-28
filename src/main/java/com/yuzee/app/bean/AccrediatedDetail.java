package com.yuzee.app.bean;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document("qccrediated_detail")
public class AccrediatedDetail {

	@Id
	private String id;

	private String accrediatedName;

	private String accrediatedWebsite;

	private String entityId;

	private String entityType;
	
	private String description;

	private String createdBy;

	private Date createdOn;

	private String updateddBy;

	private Date updatedOn;
}
