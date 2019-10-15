package com.seeka.app.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

public class ChatConversationDto implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 7590305810325611394L;
	private BigInteger id;
	private BigInteger chatId;
	private String message;
	private String status;
	/**
	 * USER/SEEKA
	 */
	private String initiateFrom;
	private BigInteger initiateFromId;
	private String initiateFromName;
	private String initiateTo;
	private BigInteger initiateToId;
	private String initiateToName;
	private Date createdOn;
	private StorageDto storageDto;

	public BigInteger getId() {
		return id;
	}

	public void setId(final BigInteger id) {
		this.id = id;
	}

	public BigInteger getChatId() {
		return chatId;
	}

	public void setChatId(final BigInteger chatId) {
		this.chatId = chatId;
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

	public String getInitiateFromName() {
		return initiateFromName;
	}

	public void setInitiateFromName(final String initiateFromName) {
		this.initiateFromName = initiateFromName;
	}

	public String getInitiateTo() {
		return initiateTo;
	}

	public void setInitiateTo(final String initiateTo) {
		this.initiateTo = initiateTo;
	}

	public BigInteger getInitiateToId() {
		return initiateToId;
	}

	public void setInitiateToId(final BigInteger initiateToId) {
		this.initiateToId = initiateToId;
	}

	public String getInitiateToName() {
		return initiateToName;
	}

	public void setInitiateToName(final String initiateToName) {
		this.initiateToName = initiateToName;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(final Date createdOn) {
		this.createdOn = createdOn;
	}

	public StorageDto getStorageDto() {
		return storageDto;
	}

	public void setStorageDto(final StorageDto storageDto) {
		this.storageDto = storageDto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (chatId == null ? 0 : chatId.hashCode());
		result = prime * result + (createdOn == null ? 0 : createdOn.hashCode());
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (initiateFrom == null ? 0 : initiateFrom.hashCode());
		result = prime * result + (initiateFromId == null ? 0 : initiateFromId.hashCode());
		result = prime * result + (initiateFromName == null ? 0 : initiateFromName.hashCode());
		result = prime * result + (initiateTo == null ? 0 : initiateTo.hashCode());
		result = prime * result + (initiateToId == null ? 0 : initiateToId.hashCode());
		result = prime * result + (initiateToName == null ? 0 : initiateToName.hashCode());
		result = prime * result + (message == null ? 0 : message.hashCode());
		result = prime * result + (status == null ? 0 : status.hashCode());
		result = prime * result + (storageDto == null ? 0 : storageDto.hashCode());
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
		ChatConversationDto other = (ChatConversationDto) obj;
		if (chatId == null) {
			if (other.chatId != null) {
				return false;
			}
		} else if (!chatId.equals(other.chatId)) {
			return false;
		}
		if (createdOn == null) {
			if (other.createdOn != null) {
				return false;
			}
		} else if (!createdOn.equals(other.createdOn)) {
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
		if (initiateFromName == null) {
			if (other.initiateFromName != null) {
				return false;
			}
		} else if (!initiateFromName.equals(other.initiateFromName)) {
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
		if (initiateToName == null) {
			if (other.initiateToName != null) {
				return false;
			}
		} else if (!initiateToName.equals(other.initiateToName)) {
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
		if (storageDto == null) {
			if (other.storageDto != null) {
				return false;
			}
		} else if (!storageDto.equals(other.storageDto)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ChatConversationDto [id=").append(id).append(", chatId=").append(chatId).append(", message=").append(message).append(", status=")
				.append(status).append(", initiateFrom=").append(initiateFrom).append(", initiateFromId=").append(initiateFromId).append(", initiateFromName=")
				.append(initiateFromName).append(", initiateTo=").append(initiateTo).append(", initiateToId=").append(initiateToId).append(", initiateToName=")
				.append(initiateToName).append(", createdOn=").append(createdOn).append(", storageDto=").append(storageDto).append("]");
		return builder.toString();
	}

}
