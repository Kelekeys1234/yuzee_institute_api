package com.seeka.app.dto;

import java.io.Serializable;
import java.math.BigInteger;

import javax.validation.constraints.NotNull;

public class ChatRequestDto implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 7590305810325611394L;
	private String id;
	private BigInteger entityId;
	@NotNull(message = "entityType is required")
	private String entityType;
	private String userId;
	/**
	 * SEEKA/USER
	 */
	@NotNull(message = "initiateFrom is required")
	private String initiateFrom;
	/**
	 * UserId
	 */
	private String initiateFromId;
	private String initiateTo;
	/**
	 * UserId
	 */
	private String initiateToId;
	private String message;

	public String getId() {
		return id;
	}

	public void setId(final String id) {
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(final String userId) {
		this.userId = userId;
	}

	public String getInitiateFrom() {
		return initiateFrom;
	}

	public void setInitiateFrom(final String initiateFrom) {
		this.initiateFrom = initiateFrom;
	}

	public String getInitiateFromId() {
		return initiateFromId;
	}

	public void setInitiateFromId(final String initiateFromId) {
		this.initiateFromId = initiateFromId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (entityId == null ? 0 : entityId.hashCode());
		result = prime * result + (entityType == null ? 0 : entityType.hashCode());
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (initiateFrom == null ? 0 : initiateFrom.hashCode());
		result = prime * result + (initiateFromId == null ? 0 : initiateFromId.hashCode());
		result = prime * result + (initiateTo == null ? 0 : initiateTo.hashCode());
		result = prime * result + (initiateToId == null ? 0 : initiateToId.hashCode());
		result = prime * result + (message == null ? 0 : message.hashCode());
		result = prime * result + (userId == null ? 0 : userId.hashCode());
		return result;
	}

	public String getInitiateTo() {
		return initiateTo;
	}

	public void setInitiateTo(final String initiateTo) {
		this.initiateTo = initiateTo;
	}

	public String getInitiateToId() {
		return initiateToId;
	}

	public void setInitiateToId(final String initiateToId) {
		this.initiateToId = initiateToId;
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
		ChatRequestDto other = (ChatRequestDto) obj;
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
		if (initiateFrom == null) {
			if (other.initiateFrom != null) {
				return false;
			}
		} else if (!initiateFrom.equals(other.initiateFrom)) {
			return false;
		}
		if (initiateFromId == null) {
			if (other.initiateFromId != null) {
				return false;
			}
		} else if (!initiateFromId.equals(other.initiateFromId)) {
			return false;
		}
		if (initiateTo == null) {
			if (other.initiateTo != null) {
				return false;
			}
		} else if (!initiateTo.equals(other.initiateTo)) {
			return false;
		}
		if (initiateToId == null) {
			if (other.initiateToId != null) {
				return false;
			}
		} else if (!initiateToId.equals(other.initiateToId)) {
			return false;
		}
		if (message == null) {
			if (other.message != null) {
				return false;
			}
		} else if (!message.equals(other.message)) {
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
		builder.append("ChatRequestDto [id=").append(id).append(", entityId=").append(entityId).append(", entityType=").append(entityType).append(", userId=")
				.append(userId).append(", initiateFrom=").append(initiateFrom).append(", initiateFromId=").append(initiateFromId).append(", message=")
				.append(message).append("]");
		return builder.toString();
	}

}
