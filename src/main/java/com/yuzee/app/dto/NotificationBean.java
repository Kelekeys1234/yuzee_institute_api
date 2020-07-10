package com.yuzee.app.dto;

import java.io.Serializable;

/**
 *
 * @author SeekADegree
 *
 */
public class NotificationBean implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 2451909674874404916L;
	private PayloadDto payload;
	private String channel;
	private String user;
	private Long userId;
	private String tenantCode;
	private String notificationType;

	/**
	 * @return the payload
	 */
	public PayloadDto getPayload() {
		return payload;
	}

	/**
	 * @param payload the payload to set
	 */
	public void setPayload(final PayloadDto payload) {
		this.payload = payload;
	}

	/**
	 * @return the channel
	 */
	public String getChannel() {
		return channel;
	}

	/**
	 * @param channel the channel to set
	 */
	public void setChannel(final String channel) {
		this.channel = channel;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(final String user) {
		this.user = user;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(final Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the tenantCode
	 */
	public String getTenantCode() {
		return tenantCode;
	}

	/**
	 * @param tenantCode the tenantCode to set
	 */
	public void setTenantCode(final String tenantCode) {

	}

	public String getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(final String notificationType) {
		this.notificationType = notificationType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (channel == null ? 0 : channel.hashCode());
		result = prime * result + (notificationType == null ? 0 : notificationType.hashCode());
		result = prime * result + (payload == null ? 0 : payload.hashCode());
		result = prime * result + (tenantCode == null ? 0 : tenantCode.hashCode());
		result = prime * result + (user == null ? 0 : user.hashCode());
		result = prime * result + (userId == null ? 0 : userId.hashCode());
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
		NotificationBean other = (NotificationBean) obj;
		if (channel == null) {
			if (other.channel != null) {
				return false;
			}
		} else if (!channel.equals(other.channel)) {
			return false;
		}
		if (notificationType == null) {
			if (other.notificationType != null) {
				return false;
			}
		} else if (!notificationType.equals(other.notificationType)) {
			return false;
		}
		if (payload == null) {
			if (other.payload != null) {
				return false;
			}
		} else if (!payload.equals(other.payload)) {
			return false;
		}
		if (tenantCode == null) {
			if (other.tenantCode != null) {
				return false;
			}
		} else if (!tenantCode.equals(other.tenantCode)) {
			return false;
		}
		if (user == null) {
			if (other.user != null) {
				return false;
			}
		} else if (!user.equals(other.user)) {
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
		builder.append("NotificationBean [payload=").append(payload).append(", channel=").append(channel).append(", user=").append(user).append(", userId=")
				.append(userId).append(", tenantCode=").append(tenantCode).append(", notificationType=").append(notificationType).append("]");
		return builder.toString();
	}

}
