package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Entity
@Table(name = "faq_category", uniqueConstraints = @UniqueConstraint(columnNames = {
		"name" }, name = "UK_FAQ_CAT"), indexes = { @Index(name = "IDX_CAT_NAME", columnList = "name", unique = true) })
@Data
public class FaqCategory implements Serializable {

	private static final long serialVersionUID = 6922844940897956622L;

	@Id
	@GenericGenerator(name = "GUID", strategy = "org.hibernate.id.GUIDGenerator")
	@GeneratedValue(generator = "GUID")
	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "created_on")
	private Date createdOn;

	@Column(name = "updated_on")
	private Date updatedOn;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_by")
	private String updatedBy;

	@OneToMany(mappedBy = "faqCategory", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<FaqSubCategory> faqSubCategories = new ArrayList<>();
}
