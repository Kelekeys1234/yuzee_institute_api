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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
/*@Table(name = "course_provider_code", uniqueConstraints = @UniqueConstraint(columnNames = { "name",
		"course_id" }, name = "UK_IPC_NI"), indexes = {
				@Index(name = "IDX_INST_ID", columnList = "course_id", unique = false) }) */
public class CourseProviderCode implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	
	private String value;
}
