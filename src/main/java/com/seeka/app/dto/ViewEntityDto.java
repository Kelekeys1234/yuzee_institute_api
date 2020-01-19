package com.seeka.app.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

public class ViewEntityDto implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 3530870980534096720L;

	private String entityType;
	private List<BigInteger> entityIds;

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(final String entityType) {
		this.entityType = entityType;
	}

	public List<BigInteger> getEntityIds() {
		return entityIds;
	}

	public void setEntityIds(final List<BigInteger> entityIds) {
		this.entityIds = entityIds;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (entityIds == null ? 0 : entityIds.hashCode());
		result = prime * result + (entityType == null ? 0 : entityType.hashCode());
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
		ViewEntityDto other = (ViewEntityDto) obj;
		if (entityIds == null) {
			if (other.entityIds != null) {
				return false;
			}
		} else if (!entityIds.equals(other.entityIds)) {
			return false;
		}
		if (entityType == null) {
			if (other.entityType != null) {
				return false;
			}
		} else if (!entityType.equals(other.entityType)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ViewEntityDto [entityType=").append(entityType).append(", entityIds=").append(entityIds).append("]");
		return builder.toString();
	}
}
