package com.seeka.app.dto;

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
	private String tenantCode;

	/**
	 * @return the payload
	 */
	public PayloadDto getPayload() {
		return payload;
	}

	/**
	 * @param payload the payload to set
	 */
	public void setPayload(PayloadDto payload) {
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
	public void setChannel(String channel) {
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
	public void setUser(String user) {
		this.user = user;
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
	public void setTenantCode(String tenantCode) {
		this.tenantCode = tenantCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((channel == null) ? 0 : channel.hashCode());
		result = prime * result + ((payload == null) ? 0 : payload.hashCode());
		result = prime * result + ((tenantCode == null) ? 0 : tenantCode.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NotificationBean other = (NotificationBean) obj;
		if (channel == null) {
			if (other.channel != null)
				return false;
		} else if (!channel.equals(other.channel))
			return false;
		if (payload == null) {
			if (other.payload != null)
				return false;
		} else if (!payload.equals(other.payload))
			return false;
		if (tenantCode == null) {
			if (other.tenantCode != null)
				return false;
		} else if (!tenantCode.equals(other.tenantCode))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NotificationBean [payload=").append(payload).append(", channel=").append(channel)
				.append(", user=").append(user).append(", tenantCode=").append(tenantCode).append("]");
		return builder.toString();
	}

}
