package com.seeka.app.bean;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "user_search")
public class UserSearch implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -8927034340284991436L;
	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", columnDefinition = "uniqueidentifier")
	private String id;
	@Column(name = "search_string")
	private String searchString;
	@Column(name = "user_id", length = 20)
	private BigInteger userId;
	@Column(name = "entity_type", length = 50)
	private String entityType;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on", length = 19)
	private Date createdOn;

	public String getId() {
		return id;
	}

	public void setId(final String id) {
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

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(final Date createdOn) {
		this.createdOn = createdOn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((createdOn == null) ? 0 : createdOn.hashCode());
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
		UserSearch other = (UserSearch) obj;
		if (createdOn == null) {
			if (other.createdOn != null) {
				return false;
			}
		} else if (!createdOn.equals(other.createdOn)) {
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserSearch [id=").append(id).append(", searchString=").append(searchString).append(", userId=").append(userId).append(", entityType=")
				.append(entityType).append(", createdOn=").append(createdOn).append("]");
		return builder.toString();
	}

}