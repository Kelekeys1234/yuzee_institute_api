package com.yuzee.app.bean;

import java.io.Serializable;


// Generated 7 Jun, 2019 2:45:49 PM by Hibernate Tools 4.3.1

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
//@Entity
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Document("level")
/*
 * @Table(name = "level", uniqueConstraints = @UniqueConstraint(columnNames = {
 * "name", "code", "sequence_no" }, name = "UK_NA_CO_SN"), indexes =
 * { @Index(name = "IDX_NAME", columnList = "name", unique = false) })
 */
public class Level implements Serializable {

	private static final long serialVersionUID = 9149617652748065109L;

	@Id
	private String id;

	private String name;

	private String code;

	private String description;

	private Integer sequenceNo;

	private Boolean isActive;

	private Date createdOn;

	private Date updatedOn;

	private Date deletedOn;

	private String createdBy;

	private String updatedBy;

	private Boolean isDeleted;

}
