package com.seeka.app.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class EnrollmentChatResposneDto implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 7590305810325611394L;
	private BigInteger id;
	private BigInteger enrollmentId;
	private BigInteger assigneeId;
	private BigInteger userId;
	private String userName;
	private Date createdOn;
	private Date updatedOn;
	private List<EnrollmentChatConversationDto> chatConversationDtos;

	public BigInteger getId() {
		return id;
	}

	public void setId(final BigInteger id) {
		this.id = id;
	}

	public BigInteger getEnrollmentId() {
		return enrollmentId;
	}

	public void setEnrollmentId(final BigInteger enrollmentId) {
		this.enrollmentId = enrollmentId;
	}

	public BigInteger getAssigneeId() {
		return assigneeId;
	}

	public void setAssigneeId(final BigInteger assigneeId) {
		this.assigneeId = assigneeId;
	}

	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(final BigInteger userId) {
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

	public List<EnrollmentChatConversationDto> getChatConversationDtos() {
		return chatConversationDtos;
	}

	public void setChatConversationDtos(final List<EnrollmentChatConversationDto> chatConversationDtos) {
		this.chatConversationDtos = chatConversationDtos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (assigneeId == null ? 0 : assigneeId.hashCode());
		result = prime * result + (chatConversationDtos == null ? 0 : chatConversationDtos.hashCode());
		result = prime * result + (createdOn == null ? 0 : createdOn.hashCode());
		result = prime * result + (enrollmentId == null ? 0 : enrollmentId.hashCode());
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
		EnrollmentChatResposneDto other = (EnrollmentChatResposneDto) obj;
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
		if (enrollmentId == null) {
			if (other.enrollmentId != null) {
				return false;
			}
		} else if (!enrollmentId.equals(other.enrollmentId)) {
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
		builder.append("EnrollmentChatResposneDto [id=").append(id).append(", enrollmentId=").append(enrollmentId).append(", assigneeId=").append(assigneeId)
				.append(", userId=").append(userId).append(", userName=").append(userName).append(", createdOn=").append(createdOn).append(", updatedOn=")
				.append(updatedOn).append(", chatConversationDtos=").append(chatConversationDtos).append("]");
		return builder.toString();
	}

}
