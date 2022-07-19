package com.yuzee.app.bean;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@EqualsAndHashCode
/*@Table(name = "job_skill", uniqueConstraints = @UniqueConstraint(columnNames = { "job_id", "skill" }, 
name = "UK_JOB_SKILL_JOB_ID"), indexes = {@Index(name = "IDX_JOB_ID", columnList = "job_id", unique = false)}) */
public class CareerJobSkill implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String skill;

	private String description;
		
}
