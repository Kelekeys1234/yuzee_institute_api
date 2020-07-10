package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "faq" ,uniqueConstraints = @UniqueConstraint(columnNames = { "title","entity_id","entity_type","faq_category_id","faq_sub_category_id","is_active" } , name = "UK_FAQ"),
indexes = { @Index (name = "IDX_FAQ_ENTITY_ID", columnList="entity_id", unique = false)
  ,@Index (name = "IDX_FAQ_ENTITY_TYPE", columnList="entity_type", unique = false)})
@Data
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Faq implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 5744814923342867841L;

	@Id
	@GenericGenerator(name = "GUID" , strategy = "org.hibernate.id.GUIDGenerator")
	@GeneratedValue(generator = "GUID")
	@Column(name = "id", unique = true, nullable = false)
	private String id;
	
	@Column(name = "entity_id", nullable = false)
	private String entityId;
	
	@Column(name = "entity_type", nullable = false)
	private String entityType;
	
	@Column(name = "title", nullable = false)
	private String title;
	
	@Column(name = "description", nullable = false)
	private String description;
	
	@Column(name = "votes")
	private Integer votes;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "faq_category_id")
	private FaqCategory faqCategory;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "faq_sub_category_id")
	private FaqSubCategory faqSubCategory;
	
	@Column(name = "created_on")
	private Date createdOn;
	
	@Column(name = "updated_on")
	private Date updatedOn;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "updated_by")
	private String updatedBy;
	
	@Column(name = "is_active")
	private Boolean isActive;

}
