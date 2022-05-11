package com.yuzee.app.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;


@Data
@NoArgsConstructor
@Document(collection = "InstituteCampus")
@JsonIgnoreProperties(ignoreUnknown = true)
public class InstituteCampus{

	@Id
	private UUID id;

	private String instituteId;

	private Institute sourceInstitute;

	private Institute destinationInstitute;

	private Date createdOn;

	private Date updatedOn;

	private String createdBy;

	private String updatedBy;

	public void setAuditFields(String userId) {
		this.setUpdatedBy(userId);
		this.setUpdatedOn(new Date());
		if (StringUtils.isEmpty(id.toString())) {
			this.setCreatedBy(userId);
			this.setCreatedOn(new Date());
		}
	}
}

//@CompoundIndexes({ @CompoundIndex(name = "UK_IC_SI_DI", def = "{'sourceInstitute' : 1}, {'destinationInstitute' : 1}", unique = true) })


