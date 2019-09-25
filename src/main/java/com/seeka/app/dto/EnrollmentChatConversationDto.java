package com.seeka.app.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

public class EnrollmentChatConversationDto implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 7590305810325611394L;
	private BigInteger id;
	private BigInteger enrollmentChatId;
	private String message;
	private String status;
	/**
	 * USER/SEEKA
	 */
	private String initiateFrom;
	private BigInteger initiateFromId;
	private Date createdOn;
	private ImageResponseDto imageResponseDto;

	public BigInteger getId() {
		return id;
	}

	public void setId(final BigInteger id) {
		this.id = id;
	}

	public BigInteger getEnrollmentChatId() {
		return enrollmentChatId;
	}

	public void setEnrollmentChatId(final BigInteger enrollmentChatId) {
		this.enrollmentChatId = enrollmentChatId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public String getInitiateFrom() {
		return initiateFrom;
	}

	public void setInitiateFrom(final String initiateFrom) {
		this.initiateFrom = initiateFrom;
	}

	public BigInteger getInitiateFromId() {
		return initiateFromId;
	}

	public void setInitiateFromId(final BigInteger initiateFromId) {
		this.initiateFromId = initiateFromId;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(final Date createdOn) {
		this.createdOn = createdOn;
	}

	public ImageResponseDto getImageResponseDto() {
		return imageResponseDto;
	}

	public void setImageResponseDto(final ImageResponseDto imageResponseDto) {
		this.imageResponseDto = imageResponseDto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (createdOn == null ? 0 : createdOn.hashCode());
		result = prime * result + (enrollmentChatId == null ? 0 : enrollmentChatId.hashCode());
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (imageResponseDto == null ? 0 : imageResponseDto.hashCode());
		result = prime * result + (initiateFrom == null ? 0 : initiateFrom.hashCode());
		result = prime * result + (initiateFromId == null ? 0 : initiateFromId.hashCode());
		result = prime * result + (message == null ? 0 : message.hashCode());
		result = prime * result + (status == null ? 0 : status.hashCode());
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
		EnrollmentChatConversationDto other = (EnrollmentChatConversationDto) obj;
		if (createdOn == null) {
			if (other.createdOn != null) {
				return false;
			}
		} else if (!createdOn.equals(other.createdOn)) {
			return false;
		}
		if (enrollmentChatId == null) {
			if (other.enrollmentChatId != null) {
				return false;
			}
		} else if (!enrollmentChatId.equals(other.enrollmentChatId)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (imageResponseDto == null) {
			if (other.imageResponseDto != null) {
				return false;
			}
		} else if (!imageResponseDto.equals(other.imageResponseDto)) {
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
		if (message == null) {
			if (other.message != null) {
				return false;
			}
		} else if (!message.equals(other.message)) {
			return false;
		}
		if (status == null) {
			if (other.status != null) {
				return false;
			}
		} else if (!status.equals(other.status)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EnrollmentChatConversationDto [id=").append(id).append(", enrollmentChatId=").append(enrollmentChatId).append(", message=")
				.append(message).append(", status=").append(status).append(", initiateFrom=").append(initiateFrom).append(", initiateFromId=")
				.append(initiateFromId).append(", createdOn=").append(createdOn).append(", imageResponseDto=").append(imageResponseDto).append("]");
		return builder.toString();
	}

}
