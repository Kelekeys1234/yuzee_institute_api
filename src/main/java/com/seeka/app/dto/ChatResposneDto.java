package com.seeka.app.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ChatResposneDto implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 7590305810325611394L;
	private String id;
	private String entityId;
	private String entityType;
	private String assigneeId;
	private String userId;
	private String userName;
	private Date createdOn;
	private Date updatedOn;
	private List<ChatConversationDto> chatConversationDtos;

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(final String entityId) {
		this.entityId = entityId;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(final String entityType) {
		this.entityType = entityType;
	}

	public String getAssigneeId() {
		return assigneeId;
	}

	public void setAssigneeId(final String assigneeId) {
		this.assigneeId = assigneeId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(final String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(final String userName) {
		this.userName = userName;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(final Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(final Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public List<ChatConversationDto> getChatConversationDtos() {
		return chatConversationDtos;
	}

	public void setChatConversationDtos(final List<ChatConversationDto> chatConversationDtos) {
		this.chatConversationDtos = chatConversationDtos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (assigneeId == null ? 0 : assigneeId.hashCode());
		result = prime * result + (chatConversationDtos == null ? 0 : chatConversationDtos.hashCode());
		result = prime * result + (createdOn == null ? 0 : createdOn.hashCode());
		result = prime * result + (entityId == null ? 0 : entityId.hashCode());
		result = prime * result + (entityType == null ? 0 : entityType.hashCode());
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (updatedOn == null ? 0 : updatedOn.hashCode());
		result = prime * result + (userId == null ? 0 : userId.hashCode());
		result = prime * result + (userName == null ? 0 : userName.hashCode());
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
		ChatResposneDto other = (ChatResposneDto) obj;
		if (assigneeId == null) {
			if (other.assigneeId != null) {
				return false;
			}
		} else if (!assigneeId.equals(other.assigneeId)) {
			return false;
		}
		if (chatConversationDtos == null) {
			if (other.chatConversationDtos != null) {
				return false;
			}
		} else if (!chatConversationDtos.equals(other.chatConversationDtos)) {
			return false;
		}
		if (createdOn == null) {
			if (other.createdOn != null) {
				return false;
			}
		} else if (!createdOn.equals(other.createdOn)) {
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
		if (updatedOn == null) {
			if (other.updatedOn != null) {
				return false;
			}
		} else if (!updatedOn.equals(other.updatedOn)) {
			return false;
		}
		if (userId == null) {
			if (other.userId != null) {
				return false;
			}
		} else if (!userId.equals(other.userId)) {
			return false;
		}
		if (userName == null) {
			if (other.userName != null) {
				return false;
			}
		} else if (!userName.equals(other.userName)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ChatResposneDto [id=").append(id).append(", entityId=").append(entityId).append(", entityType=").append(entityType)
				.append(", assigneeId=").append(assigneeId).append(", userId=").append(userId).append(", userName=").append(userName).append(", createdOn=")
				.append(createdOn).append(", updatedOn=").append(updatedOn).append(", chatConversationDtos=").append(chatConversationDtos).append("]");
		return builder.toString();
	}

}
