package com.seeka.app.bean;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "accredited_institute_detail")
public class AccreditedInstituteDetail implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 748946079064801464L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private BigInteger id;
	@Column(name = "entity_id", nullable = false)
	private BigInteger entityId;
	@Column(name = "entity_type", nullable = false)
	private String entityType;
	@Column(name = "accredited_institute_id", nullable = false)
	private BigInteger accreditedInstituteId;

	public BigInteger getId() {
		return id;
	}

	public void setId(final BigInteger id) {
		this.id = id;
	}

	public BigInteger getEntityId() {
		return entityId;
	}

	public void setEntityId(final BigInteger entityId) {
		this.entityId = entityId;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(final String entityType) {
		this.entityType = entityType;
	}

	public BigInteger getAccreditedInstituteId() {
		return accreditedInstituteId;
	}

	public void setAccreditedInstituteId(final BigInteger accreditedInstituteId) {
		this.accreditedInstituteId = accreditedInstituteId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (accreditedInstituteId == null ? 0 : accreditedInstituteId.hashCode());
		result = prime * result + (entityId == null ? 0 : entityId.hashCode());
		result = prime * result + (entityType == null ? 0 : entityType.hashCode());
		result = prime * result + (id == null ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		AccreditedInstituteDetail other = (AccreditedInstituteDetail) obj;
		if (accreditedInstituteId == null) {
			if (other.accreditedInstituteId != null) {
				return false;
			}
		} else if (!accreditedInstituteId.equals(other.accreditedInstituteId)) {
			return false;
		}
		if (entityId == null) {
			if (other.entityId != null) {
				return false;
			}
		} else if (!entityId.equals(other.entityId)) {
			return false;
		}
		if (entityType == null) {
			if (other.entityType != null) {
				return false;
			}
		} else if (!entityType.equals(other.entityType)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AccreditedInstituteDetail [id=").append(id).append(", entityId=").append(entityId).append(", entityType=").append(entityType)
				.append(", accreditedInstituteId=").append(accreditedInstituteId).append("]");
		return builder.toString();
	}

}
