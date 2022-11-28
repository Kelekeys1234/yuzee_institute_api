package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.yuzee.app.constant.InstituteAssociationStatus;
import com.yuzee.app.constant.InstituteAssociationType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document("institute_association")
@Data
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class InstituteAssociation implements Serializable {

	private static final long serialVersionUID = 6066399694958334275L;

	@Id
	private String id;

	private String sourceInstituteId;

	private String destinationInstituteId;

	private InstituteAssociationType instituteAssociationType;

	private InstituteAssociationStatus instituteAssociationStatus;

	private Integer rejectionCounter;

	private Date createdOn;

	private Date updatedOn;

	private String createdBy;

	private String updatedBy;

	public InstituteAssociation(String sourceInstituteId, String destinationInstituteId,
			InstituteAssociationType instituteAssociationType, InstituteAssociationStatus instituteAssociationStatus,
			Integer rejectionCounter, Date createdOn, Date updatedOn, String createdBy, String updatedBy) {
		super();
		this.sourceInstituteId = sourceInstituteId;
		this.destinationInstituteId = destinationInstituteId;
		this.instituteAssociationType = instituteAssociationType;
		this.instituteAssociationStatus = instituteAssociationStatus;
		this.rejectionCounter = rejectionCounter;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
	}
}
