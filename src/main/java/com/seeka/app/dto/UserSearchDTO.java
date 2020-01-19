package com.seeka.app.dto;

import java.io.Serializable;
import java.math.BigInteger;

import javax.validation.constraints.NotNull;

public class UserSearchDTO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -180933182938458432L;
	private BigInteger id;
	@NotNull(message = "searchString is required")
	private String searchString;
	private BigInteger userId;
	@NotNull(message = "entityType is required")
	private String entityType;

	public BigInteger getId() {
		return id;
	}

	public void setId(final BigInteger id) {
		this.id = id;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(final String searchString) {
		this.searchString = searchString;
	}

	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(final BigInteger userId) {
		this.userId = userId;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(final String entityType) {
		this.entityType = entityType;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserSearchDTO [id=").append(id).append(", searchString=").append(searchString).append(", userId=").append(userId)
				.append(", entityType=").append(entityType).append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((entityType == null) ? 0 : entityType.hashCode());
		result = (prime * result) + ((id == null) ? 0 : id.hashCode());
		result = (prime * result) + ((searchString == null) ? 0 : searchString.hashCode());
		result = (prime * result) + ((userId == null) ? 0 : userId.hashCode());
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
		UserSearchDTO other = (UserSearchDTO) obj;
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
		if (searchString == null) {
			if (other.searchString != null) {
				return false;
			}
		} else if (!searchString.equals(other.searchString)) {
			return false;
		}
		if (userId == null) {
			if (other.userId != null) {
				return false;
			}
		} else if (!userId.equals(other.userId)) {
			return false;
		}
		return true;
	}

}
