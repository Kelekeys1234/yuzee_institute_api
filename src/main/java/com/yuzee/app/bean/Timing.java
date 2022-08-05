package com.yuzee.app.bean;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.mongodb.core.mapping.Document;

import com.yuzee.app.enumeration.TimingType;
import com.yuzee.common.lib.enumeration.EntityTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document("Timing")
public class Timing {

	@Id

	private String id;

	@EqualsAndHashCode.Include

	private String monday = "CLOSED";

	@EqualsAndHashCode.Include

	private String tuesday = "CLOSED";

	@EqualsAndHashCode.Include

	private String wednesday = "CLOSED";

	@EqualsAndHashCode.Include

	private String thursday = "CLOSED";

	@EqualsAndHashCode.Include

	private String friday = "CLOSED";

	@EqualsAndHashCode.Include

	private String saturday = "CLOSED";

	@EqualsAndHashCode.Include

	private String sunday = "CLOSED";

	@EqualsAndHashCode.Include

	private String entityId;

	@EqualsAndHashCode.Include
	@Enumerated(EnumType.STRING)

	private TimingType timingType;

	@EqualsAndHashCode.Include

	private EntityTypeEnum entityType;

	private Date createdOn;

	private Date updatedOn;

	private String createdBy;

	private String updatedBy;

	public void setAuditFields(String userId) {
		this.setUpdatedBy(userId);
		this.setUpdatedOn(new Date());
		if (StringUtils.isEmpty(id)) {
			this.setCreatedBy(userId);
			this.setCreatedOn(new Date());
		}
	}
}
