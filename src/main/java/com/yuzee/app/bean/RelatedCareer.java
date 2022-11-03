package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@ToString(exclude = "careers")
@EqualsAndHashCode
@NoArgsConstructor
@Document(collection="relatedCareer")
//@Table(name = "related_career", uniqueConstraints = @UniqueConstraint(columnNames = { "related_career", "career_id" }, 
//	 name = "UK_RC_CAREER_ID"), indexes = {@Index(name = "IDX_CAREER_ID", columnList = "career_id", unique = false) })
public class RelatedCareer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
//	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
//	@GeneratedValue(generator = "generator")
//	@Column(name = "id", unique = true, nullable = false, length=36)
	private String id;

//	@Column(name = "related_career", nullable = false, columnDefinition = "text")
	private String relatedCareer;

	@DBRef(lazy = true)
	private Careers careers;
//	
//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "created_on", length = 19)
	private Date createdOn;

//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "updated_on", length = 19)
	private Date updatedOn;

//	@Column(name = "created_by", length = 50)
	private String createdBy;

//	@Column(name = "updated_by", length = 50)
	private String updatedBy;

public RelatedCareer(String id, String relatedCareer, Careers careers, Date createdOn, String createdBy) {
	super();
	this.id = id;
	this.relatedCareer = relatedCareer;
	this.careers = careers;
	this.createdOn = createdOn;
	this.createdBy = createdBy;
}



}
